package pl.polsl.sobocik.datagenerator.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.sobocik.datagenerator.model.*;

@Service
@RequiredArgsConstructor
public class PurchaseGenerator extends GeneratorBase {

    private Integer idCounter = 1;

    public Purchase getPurchase(Customer customer, Product product) {
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(idCounter++);
        purchase.setCustomer(customer);
        purchase.setProduct(product);
        purchase.setCount(randomInt(1, 30));
        purchase.setStatus(randomStatus());
        purchase.setOrderedDate(randomDateTime());
        return purchase;
    }

    private String randomStatus() {
        switch(randomInt(0, 3)) {
            case 0: return "awaiting-payment";
            case 1: return "delivery";
            case 2: return "complete";
            default: return "canceled";
        }
    }
}
