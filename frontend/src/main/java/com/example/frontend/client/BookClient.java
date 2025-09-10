package com.example.frontend.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class BookClient {
    private final RestClient http;

    public BookClient(@Value("${api.base:http://localhost:8081}") String base) {
        this.http = RestClient.builder().baseUrl(base).build();
    }

    public List<BookDto> list() {
        ResponseEntity<BookDto[]> resp = http.get().uri("/api/books").retrieve().toEntity(BookDto[].class);
        return Arrays.asList(resp.getBody());
    }

    public void create(BookDto book) {
        http.post()
            .uri("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .body(book)
            .retrieve();
    }

    public boolean delete(String id) {
        try {
            http.delete()
                .uri("/api/books/{id}", id)
                .retrieve()
                .toBodilessEntity();
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    @Data
    public static class BookDto {
        private String id;
        private String title;
        private String author;
        private String isbn;
        private Double price;
    }
}
