package dev.danvega.multimodal;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class AudioDetection {

    private static final String API_URL = "https://api.openai.com/v1/audio/transcriptions";

    @PostMapping(value = "/speech-to-text1")
    public String speechToText1(@RequestParam("path") String path) throws Exception {
        File audioFile = new File(path);
        Resource audioResource = new FileSystemResource(audioFile);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", audioResource);
        body.add("model", "whisper-1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer OPEN-AI-KEY");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, requestEntity, String.class);

        return response.getBody();
    }

    @PostMapping(value = "/speech-to-text", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String speechToText(@RequestParam("audioFile") MultipartFile audioFile) throws Exception {
        File file = File.createTempFile("temp", null);
        audioFile.transferTo(file);
        Resource audioResource = new FileSystemResource(file);
        //Resource audioResource = new FileSystemResource(audioFile.getResource().getFile());
        //Resource audioResource = new InputStreamResource(audioFile.getInputStream());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", audioResource);
        body.add("model", "whisper-1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer OPEN-AI-KEY");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, requestEntity, String.class);

        return response.getBody();
    }
}
