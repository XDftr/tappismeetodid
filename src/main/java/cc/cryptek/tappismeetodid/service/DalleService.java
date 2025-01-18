package cc.cryptek.tappismeetodid.service;

import cc.cryptek.tappismeetodid.config.OpenAiConfig;
import cc.cryptek.tappismeetodid.exception.OpenAiException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DalleService {
    private final RestTemplate restTemplate;
    private final OpenAiConfig openAiConfig;

    private static final String DALLE_API_URL = "https://api.openai.com/v1/images/generations";

    public String generateImage(String prompt) {
        log.info("Generating image at ChatGPT");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiConfig.getKey());

        Map<String, Object> requestBody = Map.of(
                "model", "dall-e-3",
                "prompt", prompt,
                "n", 1,
                "size", "1024x1024"
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        DalleResponse response = restTemplate.postForObject(
                DALLE_API_URL,
                request,
                DalleResponse.class
        );

        if (response != null && !response.getData().isEmpty()) {
            return response.getData().getFirst().getUrl();
        }

        throw new OpenAiException("Failed to generate image");
    }

    @Data
    private static class DalleResponse {
        private List<ImageData> data;
    }

    @Data
    private static class ImageData {
        @JsonProperty("url")
        private String url;
    }
}
