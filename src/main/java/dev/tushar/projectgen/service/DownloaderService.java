package dev.tushar.projectgen.service;

import dev.tushar.projectgen.exception.ProjectGenerationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class DownloaderService {

    private static final String API_URL = "https://start.spring.io/starter.zip";
    private final HttpClient httpClient;

    public DownloaderService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public byte[] downloadProjectZip(String requestBody) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() == 200) {
                return response.body();
            }
            throw new ProjectGenerationException("Failed to download project. Status: " + response.statusCode() + ", Body: " + new String(response.body()));
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProjectGenerationException("An error occurred during project download.", e);
        }
    }
}