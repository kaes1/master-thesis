package pl.polsl.sobocik.datagenerator.writer;

import pl.polsl.sobocik.datagenerator.model.*;

import java.io.IOException;

public class Db2Writer extends SqlFileWriter {

    private final static String CUSTOMER_INSERT = "INSERT INTO Customer VALUES (Customer_Type(%d), '%s', '%s', '%s', Address_Type()..country('%s')..city('%s')..zip_code('%s')..street('%s')..house_number('%s'));";
    private final static String MONITOR_INSERT = "INSERT INTO Monitor VALUES (Monitor_Type(%d), '%s', '%s', %.2f, %d, %d);";
    private final static String HEADSET_INSERT = "INSERT INTO Headset VALUES (Headset_Type(%d), '%s', '%s', %.2f, %d, %d, %d);";
    private final static String MOUSE_INSERT = "INSERT INTO Mouse VALUES (Mouse_Type(%d), '%s', '%s', %.2f, %d, '%s');";
    private final static String PURCHASE_INSERT = "INSERT INTO Purchase VALUES (Purchase_Type(%d), Customer_Type(%d), Product_Type(%d), %d, '%s', '%s');";
    private final static String REVIEW_INSERT = "INSERT INTO Review VALUES (Review_Type(%d), %d, '%s', '%s', '%s', Product_Type(%d));";


    public Db2Writer(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public void insert(Customer customer) throws IOException {
        Address address = customer.getAddress();
        writeLine(String.format(CUSTOMER_INSERT, customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                address.getCountry(), address.getCity(), address.getZipCode(), address.getStreet(), address.getHouseNumber()));
    }

    @Override
    public void insert(Monitor monitor) throws IOException {
        writeLine(String.format(MONITOR_INSERT, monitor.getProductId(), monitor.getBrand(), monitor.getName(), monitor.getPrice(),
                monitor.getRefreshRate(), monitor.getAudio() ? 1 : 0));
        insert(monitor.getReviews(), monitor);
    }

    @Override
    public void insert(Headset headset) throws IOException {
        writeLine(String.format(HEADSET_INSERT, headset.getProductId(), headset.getBrand(), headset.getName(), headset.getPrice(),
                headset.getSensitivity(), headset.getImpedance(), headset.getNoiseCancelling() ? 1 : 0));
        insert(headset.getReviews(), headset);
    }

    @Override
    public void insert(Mouse mouse) throws IOException {
        writeLine(String.format(MOUSE_INSERT, mouse.getProductId(), mouse.getBrand(), mouse.getName(), mouse.getPrice(),
                mouse.getDpi(), mouse.getSensor()));
        insert(mouse.getReviews(), mouse);
    }

    @Override
    public void insert(Purchase purchase) throws IOException {
        writeLine(String.format(PURCHASE_INSERT, purchase.getPurchaseId(),  purchase.getCustomer().getCustomerId(), purchase.getProduct().getProductId(),
                purchase.getCount(), purchase.getStatus(), dateTimeFormatter.format(purchase.getOrderedDate())));
    }

    @Override
    public void insert(Review review, Product product) throws IOException {
        writeLine(String.format(REVIEW_INSERT, review.getReviewId(), review.getScore(), review.getContent(), review.getAuthor(), dateTimeFormatter.format(review.getCreated()),
                product.getProductId()));
    }
}
