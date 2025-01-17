package cc.cryptek.tappismeetodid.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    private UUID id;
    @Column(length = Integer.MAX_VALUE)
    private String title;
    @Column(length = Integer.MAX_VALUE)
    private String name;
    @Column(length = Integer.MAX_VALUE)
    private String country;
    @Column(length = Integer.MAX_VALUE)
    private String url;
}
