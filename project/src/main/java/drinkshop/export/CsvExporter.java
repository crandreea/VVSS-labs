package drinkshop.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import drinkshop.domain.Order;
import drinkshop.domain.OrderItem;
import drinkshop.domain.Product;

public class CsvExporter {
    public static void exportOrders(List<Product> products, List<Order> orders, String path) {
        try (FileWriter w = new FileWriter(path)) {
            w.write("OrderId,Product,Quantity,Price\n");
            double sum=0.0;
            for (Order o : orders){
                for (OrderItem i : o.getItems()) {
                    Product p = products.stream().filter((p1)->i.getProduct().getId()==p1.getId()).toList().get(0);
                    w.write(o.getId() + "," + p.getNume() + "," + i.getQuantity() + "," + p.getPret() + "\n");
                }
                w.write("total order: "+o.getTotalPrice()+" RON\n");
                w.write("-------------------------------\n");
                sum+=o.getTotalPrice();
            }
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            w.write("TOTAL OF "+date+" is: "+sum+" RON\n");
        } catch (IOException e) {
            throw new UncheckedIOException("Eroare la exportul comenzilor in CSV", e);
        }
    }
}