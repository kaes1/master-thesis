package pl.polsl.sobocik.datagenerator.source;

import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class DataSource {

    private List<String> lastNames;
    private List<String> firstNames;
    private List<String> brandNames;
    private List<String> productNameComponents;

    @PostConstruct
    public void loadAllData() throws IOException {
        firstNames = Files.lines(Path.of("data-files/us-firstnames.txt")).collect(Collectors.toList());
        lastNames = Files.lines(Path.of("data-files/us-surnames.txt")).collect(Collectors.toList());
        brandNames = Files.lines(Path.of("data-files/brand-names.txt")).collect(Collectors.toList());
        productNameComponents = Files.lines(Path.of("data-files/product-name-components.txt")).collect(Collectors.toList());
    }
}
