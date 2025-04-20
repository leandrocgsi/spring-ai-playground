package br.com.erudio.service;

import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    public ImageService() {
        // Construtor vazio
    }

    public ImageResponse generateImage(
            String prompt,
            String quality,
            Integer n,
            Integer height,
            Integer width) {

        ImagePrompt imagePrompt = new ImagePrompt(prompt); // Mantendo padrão parecido

        List<ImageGeneration> imageGenerations = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String url = "https://placehold.co/" + width + "x" + height + "?text=Mocked+Image";
            String b64Json = null;

            Image image = new Image(url, b64Json);
            ImageGeneration imageGeneration = new ImageGeneration(image);
            imageGenerations.add(imageGeneration);
        }

        ImageResponse imageResponse = new ImageResponse(imageGenerations);
        return imageResponse;
    }
}