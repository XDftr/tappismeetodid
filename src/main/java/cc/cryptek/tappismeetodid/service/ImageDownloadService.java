package cc.cryptek.tappismeetodid.service;

import cc.cryptek.tappismeetodid.config.ImageConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageDownloadService {
    private final ImageConfig imageConfig;

    @SneakyThrows
    public String downloadAndSaveImage(String imageUrl, String filename) {
        try {
            // Get the resources folder path
            String resourcePath = new File("src/main/resources").getAbsolutePath();
            Path fullPath = Paths.get(resourcePath, imageConfig.getGenerated());

            // Create directories if they don't exist
            createDirectoryIfNotExists(fullPath);

            String extension = StringUtils.getFilenameExtension(imageUrl);
            if (extension == null) {
                extension = "png"; // Default extension
            }

            filename = filename + "." + extension;
            Path targetPath = fullPath.resolve(filename);

            // Download and save the image
            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("Image downloaded and saved successfully: {}", targetPath);
                return filename;
            }

        } catch (IOException e) {
            log.error("Failed to download image from URL: {}", imageUrl, e);
            throw new RuntimeException("Failed to download and save image", e);
        }
    }

    @SneakyThrows
    private void createDirectoryIfNotExists(Path directory) {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
            log.info("Created directory: {}", directory);
        }
    }
}
