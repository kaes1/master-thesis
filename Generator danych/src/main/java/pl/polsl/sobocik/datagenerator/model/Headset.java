package pl.polsl.sobocik.datagenerator.model;

import lombok.Data;

@Data
public class Headset extends Product {
    private Integer sensitivity;
    private Integer impedance;
    private Boolean noiseCancelling;
}
