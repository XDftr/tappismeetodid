package cc.cryptek.tappismeetodid.service;

import cc.cryptek.tappismeetodid.controller.dto.SubmissionRequestDto;
import cc.cryptek.tappismeetodid.entity.Submission;
import cc.cryptek.tappismeetodid.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private static final String UPLOAD_DIR = "static/images/";

    public Submission createSubmission(SubmissionRequestDto dto) {
        UUID id = UUID.randomUUID();
        Submission submission = new Submission(id, dto.getTitle(), dto.getName(), dto.getCountry(), dto.getImage());
        return submissionRepository.save(submission);
    }

    public Submission getImage(UUID id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
    }

    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }
}
