package pl.polsl.sobocik.datagenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.sobocik.datagenerator.generator.*;
import pl.polsl.sobocik.datagenerator.model.*;
import pl.polsl.sobocik.datagenerator.writer.*;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SQLScriptCreator {

    private final CustomerGenerator customerGenerator;
    private final ProductGenerator productGenerator;
    private final PurchaseGenerator purchaseGenerator;
    private final ReviewGenerator reviewGenerator;

    int customerCount = 100000;
    int headsetCount = 10000;
    int mouseCount = 10000;
    int monitorCount = 10000;
    int purchaseCount = 300000;
    int reviewCount = 200000;

//    int customerCount = 10000;
//    int headsetCount = 1000;
//    int mouseCount = 1000;
//    int monitorCount = 1000;
//    int purchaseCount = 20000;
//    int reviewCount = 10000;

    @PostConstruct
    public void createSQLScripts() throws IOException {
        File dir = new File("result");
        if (!dir.exists()) {
            dir.mkdir();
        }

        PostgresWriter postgresWriter = new PostgresWriter("result/sql-postgres.sql");
        OracleWriter oracleWriter = new OracleWriter("result/sql-oracle.sql");
        Db2Writer db2Writer = new Db2Writer("result/sql-db2.sql");
        RelationalWriter relationalWriter = new RelationalWriter("result/sql-relational.sql");
        List<SqlFileWriter> writers = Arrays.asList(postgresWriter, oracleWriter, db2Writer, relationalWriter);

        //GENERATE CUSTOMERS
        System.out.println("Generating Customers ...");
        List<Customer> customers = getCustomers(customerCount);

        //GENERATE PRODUCTS
        System.out.println("Generating Products ...");
        List<Headset> headsets = getHeadsets(headsetCount);
        List<Mouse> mice = getMice(mouseCount);
        List<Monitor> monitors = getMonitors(monitorCount);
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(headsets);
        allProducts.addAll(mice);
        allProducts.addAll(monitors);


        // GENERATE PURCHASES
        System.out.println("Generating Purchases ...");
        List<Purchase> purchases = new ArrayList<>();
        for (int i = 0; i < purchaseCount; i++) {
            Product randomProduct = allProducts.get(RandomSource.random.nextInt(allProducts.size()));
            Customer randomCustomer = customers.get(RandomSource.random.nextInt(customers.size()));
            purchases.add(purchaseGenerator.getPurchase(randomCustomer, randomProduct));
        }

        //GENERATE REVIEWS
        System.out.println("Generating Reviews ...");
        for (int i = 0; i < reviewCount; i++) {
            Product randomProduct = allProducts.get(RandomSource.random.nextInt(allProducts.size()));
            randomProduct.getReviews().add(reviewGenerator.getReview());
        }

        System.out.println("Generation finished.");

        //WRITE EVERYTHING TO FILES
        System.out.println("Writing Customers ...");
        for (Customer customer : customers) {
            for (SqlFileWriter writer : writers) {
                writer.insert(customer);
            }
        }

        System.out.println("Writing Products and Reviews ...");
        for (Headset headset : headsets) {
            for (SqlFileWriter writer : writers) {
                writer.insert(headset);
            }
        }

        for (Mouse mouse : mice) {
            for (SqlFileWriter writer : writers) {
                writer.insert(mouse);
            }
        }

        for (Monitor monitor : monitors) {
            for (SqlFileWriter writer : writers) {
                writer.insert(monitor);
            }
        }

        System.out.println("Writing Purchases ...");
        for (Purchase purchase : purchases) {
            for (SqlFileWriter writer : writers) {
                writer.insert(purchase);
            }
        }

        for (SqlFileWriter writer : writers) {
            writer.close();
        }

        System.out.println("Finished writing to files.");
    }

    private List<Mouse> getMice(int n) {
        List<Mouse> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(productGenerator.getMouse());
        }
        return list;
    }

    private List<Monitor> getMonitors(int n) {
        List<Monitor> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(productGenerator.getMonitor());
        }
        return list;
    }

    private List<Headset> getHeadsets(int n) {
        List<Headset> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(productGenerator.getHeadset());
        }
        return list;
    }

    private List<Customer> getCustomers(int n) {
        List<Customer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(customerGenerator.getCustomer());
        }
        return list;
    }

    private List<Review> getReviews(int n) {
        List<Review> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(reviewGenerator.getReview());
        }
        return list;
    }

}
