package cc.cryptek.tappismeetodid.service.steps;

import cc.cryptek.tappismeetodid.repository.ImageRepository;
import cc.cryptek.tappismeetodid.service.DalleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component("processImageStep")
@RequiredArgsConstructor
public class ProcessImageStep implements JavaDelegate {
    private final DalleService dalleService;
    private final ImageRepository imageRepository;

    @Override
    public void execute(DelegateExecution execution) {
        var id = (UUID) execution.getVariable("image-id");
        log.info("Process image step, image ID: {}", id);

        var image = imageRepository.getReferenceById(id);

        var imageUrl = dalleService.generateImage(image.getText());

        log.info("Image generated at provider, image url: {}", imageUrl);

        image.setExternalImageUrl(imageUrl);

        imageRepository.save(image);
    }
}
