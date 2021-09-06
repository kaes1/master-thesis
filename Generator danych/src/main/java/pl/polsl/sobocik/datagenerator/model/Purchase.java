package pl.polsl.sobocik.datagenerator.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Purchase {
    private Integer purchaseId;
    private Product product;
    private Customer customer;
    private Integer count;
    private String status;
    private LocalDateTime orderedDate;
}
