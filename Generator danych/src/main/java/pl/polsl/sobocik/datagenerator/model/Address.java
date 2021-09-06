package pl.polsl.sobocik.datagenerator.model;

import lombok.Data;

@Data
public class Address {
    private String country;
    private String city;
    private String zipCode;
    private String street;
    private String houseNumber;
}
