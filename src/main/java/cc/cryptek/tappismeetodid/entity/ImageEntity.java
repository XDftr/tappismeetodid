package cc.cryptek.tappismeetodid.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity extends BaseEntity {
    private String externalImageUrl;

    private String text;

    private UUID author;

    private boolean saved;

    private String imageUrl;
}
