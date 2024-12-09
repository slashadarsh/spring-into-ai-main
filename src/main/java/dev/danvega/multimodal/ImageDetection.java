package dev.danvega.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageDetection {

    private final ChatClient chatClient;
    @Value("classpath:/images/sooryvansham_1590047977.jpg")
    Resource sampleImage;

    public ImageDetection(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/image-to-text")
    public String image() throws IOException {
        return chatClient.prompt()
                .user(u -> u
                        .text("Can you please this scene is from which Movie and describe the movie scene as well appearing in the image, also specify on which OTT platform it is available ?")
                        .media(MimeTypeUtils.IMAGE_JPEG,sampleImage)
                ).system(s -> s
                        .text("You are a movies expert having details of all the movies in the world. Please see the image and answer the questions around it. ")
                )
                .call()
                .content();
    }

}

