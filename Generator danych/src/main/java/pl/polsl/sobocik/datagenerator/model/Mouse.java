package pl.polsl.sobocik.datagenerator.model;

import lombok.Data;

@Data
public class Mouse extends Product {
    private Integer dpi;
    private String sensor;
}
