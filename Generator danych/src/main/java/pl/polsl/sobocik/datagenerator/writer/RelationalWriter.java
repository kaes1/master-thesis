package pl.polsl.sobocik.datagenerator.writer;

import pl.polsl.sobocik.datagenerator.model.*;

import java.io.IOException;

public class RelationalWriter extends SqlFileWriter {

    private final static String CUSTOMER_INSERT = "INSERT INTO customer VALUES (%d, '%s', '%s', '%s','%s','%s','%s','%s','%s');";
    private final static String PRODUCT_INSERT = "INSERT INTO product VALUES (%d, '%s', '%s', %.2f);";
    private final static String MONITOR_INSERT = "INSERT INTO monitor VALUES (%d, %d, %d);";
    private final static String HEADSET_INSERT = "INSERT INTO headset VALUES (%d, %d, %d, %d);";
    private final static String MOUSE_INSERT = "INSERT INTO mouse VALUES (%d, %d, '%s');";
    private final static String PURCHASE_INSERT = "INSERT INTO purchase VALUES (%d, %d, %d, %d, '%s', '%s');";
    private final static String REVIEW_INSERT = "INSERT INTO review VALUES (%d, %d, '%s', '%s', '%s', %d);";

    public RelationalWriter(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public void insert(Customer customer) throws IOException {
        Address address = customer.getAddress();
        writeLine(String.format(CUSTOMER_INSERT, customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                address.getCountry(), address.getCity(), address.getZipCode(), address.getStreet(), address.getHouseNumber()));
    }

    private void insertProduct(Product product) throws IOException {
        writeLine(String.format(PRODUCT_INSERT, product.getProductId(), product.getBrand(), product.getName(), product.getPrice()));
    }

    @Override
    public void insert(Monitor monitor) throws IOException {
        insertProduct(monitor);
        writeLine(String.format(MONITOR_INSERT, monitor.getProductId(), monitor.getRefreshRate(), monitor.getAudio() ? 1 : 0));
        insert(monitor.getReviews(), monitor);
    }

    @Override
    public void insert(Headset headset) throws IOException {
        insertProduct(headset);
        writeLine(String.format(HEADSET_INSERT, headset.getProductId(), headset.getSensitivity(), headset.getImpedance(), headset.getNoiseCancelling() ? 1 : 0));
        insert(headset.getReviews(), headset);
    }

    @Override
    public void insert(Mouse mouse) throws IOException {
        insertProduct(mouse);
        writeLine(String.format(MOUSE_INSERT, mouse.getProductId(), mouse.getDpi(), mouse.getSensor()));
        insert(mouse.getReviews(), mouse);
    }

    @Override
    public void insert(Purchase purchase) throws IOException {
        writeLine(String.format(PURCHASE_INSERT, purchase.getPurchaseId(), purchase.getCustomer().getCustomerId(), purchase.getProduct().getProductId(),
                purchase.getCount(), purchase.getStatus(), dateTimeFormatter.format(purchase.getOrderedDate())));
    }

    @Override
    public void insert(Review review, Product product) throws IOException {
        writeLine(String.format(REVIEW_INSERT, review.getReviewId(), review.getScore(), review.getContent(), review.getAuthor(), dateTimeFormatter.format(review.getCreated()), product.getProductId()));
    }
}
