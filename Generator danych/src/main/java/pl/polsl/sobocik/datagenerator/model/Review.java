package pl.polsl.sobocik.datagenerator.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private Integer reviewId;
    private Integer score;
    private String content;
    private String author;
    private LocalDateTime created;
}
