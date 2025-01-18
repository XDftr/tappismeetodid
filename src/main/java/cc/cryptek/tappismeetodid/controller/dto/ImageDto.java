package cc.cryptek.tappismeetodid.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageDto {
    private UUID id;
    private String imageUrl;
}
