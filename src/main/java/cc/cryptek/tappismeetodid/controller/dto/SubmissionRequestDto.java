package cc.cryptek.tappismeetodid.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class SubmissionRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @NotBlank
    private UUID image;
}
