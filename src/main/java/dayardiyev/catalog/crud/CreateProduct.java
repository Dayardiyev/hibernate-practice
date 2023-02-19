package dayardiyev.catalog.crud;

import dayardiyev.catalog.entity.Category;
import dayardiyev.catalog.entity.Option;
import dayardiyev.catalog.entity.Product;
import dayardiyev.catalog.entity.Value;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class CreateProduct {

    static final Scanner sc = new Scanner(System.in);

    public static void execute(EntityManager manager) {

        try {
            manager.getTransaction().begin();

            System.out.print("Выберите категорию по номеру: ");
            long categoryId = Long.parseLong(sc.nextLine());

            Category category = manager.find(Category.class, categoryId);

            System.out.print("Введите название товара: ");
            String productName = sc.nextLine();

            System.out.print("Введите стоимость товара: ");
            int productPrice = Integer.parseInt(sc.nextLine());


            List<Option> options = category.getOptions();

            Product product = new Product();
            product.setName(productName);
            product.setCategory(category);
            product.setPrice(productPrice);
            manager.persist(product);

            for (Option option : options) {

                System.out.printf("%s: ", option.getName());
                String val = sc.nextLine();

                Value value = new Value();
                value.setProduct(product);
                value.setOption(option);
                value.setValue(val);
                manager.persist(value);

            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
