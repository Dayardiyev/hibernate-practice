package dayardiyev.catalog.crud;

import dayardiyev.catalog.entity.Product;
import dayardiyev.catalog.entity.Value;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class DeleteProduct {

    static final Scanner sc = new Scanner(System.in);

    public static void execute(EntityManager manager) {

        try {
            manager.getTransaction().begin();

            System.out.print("Введите идентификатор товара: ");
            long id = Long.parseLong(sc.nextLine());
            Product product = manager.find(Product.class, id);

            List<Value> valueList = product.getValues();
            for (Value value : valueList) {
                manager.remove(value);
            }
            manager.remove(product);

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
