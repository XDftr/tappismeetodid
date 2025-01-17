package cc.cryptek.tappismeetodid.controller;

import cc.cryptek.tappismeetodid.controller.dto.SubmissionRequestDto;
import cc.cryptek.tappismeetodid.entity.Submission;
import cc.cryptek.tappismeetodid.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@CrossOrigin
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<Submission> createSubmission(
            @RequestBody SubmissionRequestDto dto) {
        return ResponseEntity.ok(submissionService.createSubmission(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getImage(@PathVariable UUID id) {
        return ResponseEntity.ok(submissionService.getImage(id));
    }

    @GetMapping
    public ResponseEntity<List<Submission>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.getAllSubmissions());
    }

    @GetMapping("/delay")
    public ResponseEntity<String> getDelayedResponse() {
        try {
            Thread.sleep(5000); // Sleep for 10 seconds
            return ResponseEntity.ok("https://oaidalleapiprodscus.blob.core.windows.net/private/org-N8YVYKA7nD9YS2HqAsYNrvCO/user-PWLOpHDjgLyhFTL8dtGDx0Pv/img-UfaQh7wHsrfdNDHjW6rep20m.png?st=2025-01-14T18%3A35%3A02Z&se=2025-01-14T20%3A35%3A02Z&sp=r&sv=2024-08-04&sr=b&rscd=inline&rsct=image/png&skoid=d505667d-d6c1-4a0a-bac7-5c84a87759f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-01-13T23%3A05%3A52Z&ske=2025-01-14T23%3A05%3A52Z&sks=b&skv=2024-08-04&sig=ew5xmuPsqxm9zyCYk53RuQHgJxyBuavg%2Bm/QrM1mQGA%3D");
        } catch (InterruptedException e) {
            return ResponseEntity.internalServerError().body("Sleep was interrupted");
        }
    }
}
