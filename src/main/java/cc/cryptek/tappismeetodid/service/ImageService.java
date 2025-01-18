package cc.cryptek.tappismeetodid.service;

import cc.cryptek.tappismeetodid.controller.dto.ImageDto;
import cc.cryptek.tappismeetodid.controller.dto.ImageRequestDto;
import cc.cryptek.tappismeetodid.controller.dto.ImageResponseDto;
import cc.cryptek.tappismeetodid.entity.ImageEntity;
import cc.cryptek.tappismeetodid.exception.TimeoutException;
import cc.cryptek.tappismeetodid.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final SmartBpmnService service;
    private static final int TIMEOUT_SECONDS = 30;

    public ImageResponseDto requestImageGeneration(ImageRequestDto dto) {
        log.info("Started image generation");

        handleTimeout(dto.getAuthor());

        var imageEntity = ImageEntity.builder().text(dto.getText()).author(dto.getAuthor()).build();
        var image = imageRepository.save(imageEntity);

        Map<String, Object> params = Map.of("image-id", image.getId());
        service.startProcess("create-and-save-image-process", params);

        return new ImageResponseDto(image.getId().toString());
    }

    public ImageDto getImage(UUID id) {
        var image = imageRepository.getReferenceById(id);
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setImageUrl(image.getImageUrl());
        return imageDto;
    }

    private void handleTimeout(UUID authorId) {
        LocalDateTime now = LocalDateTime.now();

        imageRepository.findTopByAuthorOrderByCreatedDateDesc(authorId)
                .ifPresent(lastImage -> {
                    long secondsDiff = ChronoUnit.SECONDS.between(lastImage.getCreatedDate(), now);
                    if (secondsDiff < TIMEOUT_SECONDS) {
                        throw new TimeoutException("Please wait " + (TIMEOUT_SECONDS - secondsDiff) +
                                " seconds before generating a new image");
                    }
                });
    }
}
