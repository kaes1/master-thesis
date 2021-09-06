package pl.polsl.sobocik.datagenerator.model;

import lombok.Data;

@Data
public class Monitor extends Product {
    private Integer refreshRate;
    private Boolean audio;
}
