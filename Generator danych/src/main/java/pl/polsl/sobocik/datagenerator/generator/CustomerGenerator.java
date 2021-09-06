package pl.polsl.sobocik.datagenerator.generator;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.sobocik.datagenerator.model.Customer;
import pl.polsl.sobocik.datagenerator.source.DataSource;


@Service
@RequiredArgsConstructor
public class CustomerGenerator extends GeneratorBase {

    private final DataSource dataSource;
    private final AddressGenerator addressGenerator;
    private Integer idCounter = 1;

    public Customer getCustomer() {
        String firstName = dataSource.getFirstNames().get(random.nextInt(dataSource.getFirstNames().size()));
        String lastName = dataSource.getLastNames().get(random.nextInt(dataSource.getLastNames().size()));
        String email = firstName.toLowerCase() + lastName.toLowerCase() + "@mail.com";
        Customer customer = new Customer();
        customer.setCustomerId(idCounter++);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setAddress(addressGenerator.getAddress());
        return customer;
    }
}
