package pl.polsl.sobocik.datagenerator.model;

import lombok.Data;

@Data
public class Customer {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
