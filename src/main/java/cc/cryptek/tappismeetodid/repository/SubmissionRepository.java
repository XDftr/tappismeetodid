package cc.cryptek.tappismeetodid.repository;

import cc.cryptek.tappismeetodid.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
}
