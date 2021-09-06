package pl.polsl.sobocik.datagenerator.model;

import lombok.Data;

import java.util.List;

@Data
public abstract class Product {
    private Integer productId;
    private String brand;
    private String name;
    private Double price;
    private List<Review> reviews;
}
