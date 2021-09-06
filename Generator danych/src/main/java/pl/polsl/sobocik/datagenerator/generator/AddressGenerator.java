package pl.polsl.sobocik.datagenerator.generator;


import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.polsl.sobocik.datagenerator.model.Address;

@Service
@RequiredArgsConstructor
public class AddressGenerator extends GeneratorBase {

    Lorem lorem = LoremIpsum.getInstance();

    public Address getAddress() {
        Address address = new Address();
        address.setCountry(randomCountry());
        address.setCity(randomCity());
        address.setZipCode(randomZipCode());
        address.setStreet(randomStreet());
        address.setHouseNumber(randomHouseNumber());
        return address;
    }

    private String randomCountry() {
        return lorem.getCountry().replace("'", "");
    }

    private String randomCity() {
       return lorem.getCity().replace("'", "");
    }

    private String randomZipCode() {
        return lorem.getZipCode();
    }

    private String randomStreet() {
        String firstWord = StringUtils.capitalize(lorem.getWords(1));
        return random.nextBoolean() && random.nextBoolean()
                ? firstWord + " " + StringUtils.capitalize(lorem.getWords(1))
                : firstWord;
    }

    private String randomHouseNumber() {
        String houseNumber = String.valueOf(randomInt(1, 99));
        if (random.nextBoolean()) {
            houseNumber += "/";
            houseNumber += String.valueOf(randomInt(1,99));
        }
        return houseNumber;
    }

}
