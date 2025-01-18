package cc.cryptek.tappismeetodid.service.steps;

import cc.cryptek.tappismeetodid.repository.ImageRepository;
import cc.cryptek.tappismeetodid.service.ImageDownloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("saveImageStep")
@Slf4j
@RequiredArgsConstructor
public class SaveImageStep implements JavaDelegate {
    private final ImageDownloadService imageDownloadService;
    private final ImageRepository imageRepository;

    @Override
    public void execute(DelegateExecution execution) {
        var id = (UUID) execution.getVariable("image-id");

        log.info("Save image step, image ID: {}", id);

        var image = imageRepository.getReferenceById(id);

        var internalImageUrl = imageDownloadService.downloadAndSaveImage(image.getExternalImageUrl(), image.getId().toString());

        image.setImageUrl(internalImageUrl);

        imageRepository.save(image);
    }
}
