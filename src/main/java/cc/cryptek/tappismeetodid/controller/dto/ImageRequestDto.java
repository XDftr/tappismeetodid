package cc.cryptek.tappismeetodid.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ImageRequestDto {
    @NotBlank
    private final String text;

    @NotNull
    private final UUID author;
}
