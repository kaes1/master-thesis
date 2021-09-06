package pl.polsl.sobocik.datagenerator.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.sobocik.datagenerator.model.Headset;
import pl.polsl.sobocik.datagenerator.model.Monitor;
import pl.polsl.sobocik.datagenerator.model.Mouse;
import pl.polsl.sobocik.datagenerator.source.DataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductGenerator extends GeneratorBase {

    private final DataSource dataSource;
    private Integer idCounter = 1;

    public Monitor getMonitor() {
        Monitor monitor = new Monitor();
        monitor.setProductId(idCounter++);
        monitor.setBrand(randomBrand());
        monitor.setName(randomName());
        monitor.setPrice(randomMonitorPrice());
        monitor.setRefreshRate(randomRefreshRate());
        monitor.setAudio(random.nextBoolean());
        monitor.setReviews(new ArrayList<>());
        return monitor;
    }

    public Headset getHeadset() {
        Headset headset = new Headset();
        headset.setProductId(idCounter++);
        headset.setBrand(randomBrand());
        headset.setName(randomName());
        headset.setPrice(randomHeadsetPrice());
        headset.setSensitivity(randomInt(30, 200));
        headset.setImpedance(randomInt(20, 500));
        headset.setNoiseCancelling(random.nextBoolean());
        headset.setReviews(new ArrayList<>());
        return headset;
    }

    public Mouse getMouse() {
        Mouse mouse = new Mouse();
        mouse.setProductId(idCounter++);
        mouse.setBrand(randomBrand());
        mouse.setName(randomName());
        mouse.setPrice(randomMousePrice());
        mouse.setDpi(randomInt(800, 4000));
        mouse.setSensor(randomSensor());
        mouse.setReviews(new ArrayList<>());
        return mouse;
    }

    private String randomBrand() {
        return dataSource.getBrandNames().get(random.nextInt(dataSource.getBrandNames().size()));
    }

    // 10 to 150 dollars
    private Double randomMousePrice() {
        return randomPrice(10, 150);
    }

    // 12 to 350 dollars
    private Double randomHeadsetPrice() {
        return randomPrice(12, 350);
    }

    // 50 to 2000 dollars
    private Double randomMonitorPrice() {
        return randomPrice(50, 2000);
    }

    private Double randomPrice(int from, int to) {
        return (double) randomInt(from, to);
    }

    private String randomName() {
        List<String> nameComponents = dataSource.getProductNameComponents();
        Collections.shuffle(nameComponents);
        int howMany = randomInt(1, 3);
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < howMany; i++) {
            name.append(nameComponents.get(i)).append(" ");
        }
        if (random.nextBoolean() && random.nextBoolean()) {
            name.append("Pro").append(" ");
        }
        if (random.nextBoolean() && random.nextBoolean()) {
            name.append(randomInt(2, 6));
        }
        return name.toString().trim();
    }

    private Integer randomRefreshRate() {
        switch(random.nextInt(4)) {
            case 0: return 59;
            case 1: return 60;
            case 2: return 120;
            default: return 144;
        }
    }

    private String randomSensor() {
        return random.nextInt(3) == 0 ? "optical" : "laser";
    }
}
