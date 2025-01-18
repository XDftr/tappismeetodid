package cc.cryptek.tappismeetodid.service;

import cc.cryptek.tappismeetodid.controller.dto.SubmissionRequestDto;
import cc.cryptek.tappismeetodid.entity.SubmissionEntity;
import cc.cryptek.tappismeetodid.repository.ImageRepository;
import cc.cryptek.tappismeetodid.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final ImageRepository imageRepository;
    private static final String UPLOAD_DIR = "static/images/";

    public SubmissionEntity createSubmission(SubmissionRequestDto dto) {
        var image = imageRepository.getReferenceById(dto.getImage());
        SubmissionEntity submissionEntity = new SubmissionEntity(dto.getTitle(), dto.getName(), dto.getCountry(), image.getImageUrl());
        return submissionRepository.save(submissionEntity);
    }

    public SubmissionEntity getImage(UUID id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
    }

    public List<SubmissionEntity> getAllSubmissions() {
        return submissionRepository.findAll();
    }
}
