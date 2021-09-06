package pl.polsl.sobocik.datagenerator.writer;

import pl.polsl.sobocik.datagenerator.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class SqlFileWriter {

    protected BufferedWriter fileWriter;

    protected final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SqlFileWriter(String fileName) throws IOException {
        fileWriter = new BufferedWriter(new FileWriter(createFile(fileName)));
    }

    private File createFile(String fileName) throws IOException {
        File targetFile = new File(fileName);
        if (targetFile.createNewFile()){
            System.out.println("File " + fileName + " created");
        }
        return targetFile;
    }

    protected void writeLine(String line) throws IOException {
        fileWriter.write(line);
        fileWriter.newLine();
    }

    public void close() throws IOException {
        fileWriter.close();
    }

    public void insert(List<Review> reviews, Product product) throws IOException {
        for (Review review : reviews) {
            insert(review, product);
        }
    }

    public abstract void insert(Customer customer) throws IOException;
    public abstract void insert(Monitor monitor) throws IOException;
    public abstract void insert(Headset headset) throws IOException;
    public abstract void insert(Mouse mouse) throws IOException;
    public abstract void insert(Purchase purchase) throws IOException;
    public abstract void insert(Review review, Product product) throws IOException;
}
