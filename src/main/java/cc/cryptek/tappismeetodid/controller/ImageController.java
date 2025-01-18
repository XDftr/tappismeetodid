package cc.cryptek.tappismeetodid.controller;

import cc.cryptek.tappismeetodid.controller.dto.ImageDto;
import cc.cryptek.tappismeetodid.controller.dto.ImageRequestDto;
import cc.cryptek.tappismeetodid.controller.dto.ImageResponseDto;
import cc.cryptek.tappismeetodid.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ImageResponseDto createImage(@Valid @RequestBody ImageRequestDto dto) {
        return imageService.requestImageGeneration(dto);
    }

    @GetMapping("/{id}")
    public ImageDto getImage(@PathVariable UUID id) {
        return imageService.getImage(id);
    }
}
