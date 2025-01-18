package cc.cryptek.tappismeetodid.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "submissions")
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionEntity extends BaseEntity {
    private String title;

    private String name;

    private String country;

    private String url;
}
