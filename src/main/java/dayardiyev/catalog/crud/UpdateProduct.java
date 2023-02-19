package dayardiyev.catalog.crud;

import dayardiyev.catalog.entity.Option;
import dayardiyev.catalog.entity.Product;
import dayardiyev.catalog.entity.Value;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class UpdateProduct {

    static final Scanner sc = new Scanner(System.in);

    public static void execute(EntityManager manager){

        try {
            manager.getTransaction().begin();

            System.out.print("Введите идентификатор товара: ");
            long id = Long.parseLong(sc.nextLine());
            Product product = manager.find(Product.class, id);

            System.out.printf("Введите новое название товара [%s]: ", product.getName());
            String updatedName = sc.nextLine();
            if (!updatedName.equals("")) product.setName(updatedName);

            System.out.printf("Введите новую стоимость товара [%d]: ", product.getPrice());
            String updatedPrice = sc.nextLine();
            if (!updatedPrice.equals("")) product.setPrice(Integer.parseInt(updatedPrice));

            List<Value> valueList = product.getValues();
            List<Option> optionList = product.getCategory().getOptions();

            for (int i = 0; i < optionList.size(); i++) {
                if (i >= valueList.size()) {
                    System.out.printf("%s [null]: ", optionList.get(i).getName());
                    String newValue = sc.nextLine();
                    if (!newValue.equals("")) {
                        Value value = new Value();
                        value.setProduct(product);
                        value.setOption(optionList.get(i));
                        value.setValue(newValue);
                        manager.persist(value);
                    }
                } else {
                    if (optionList.get(i) == valueList.get(i).getOption()) {
                        System.out.printf("%s [%s]: ", optionList.get(i).getName(), valueList.get(i).getValue());
                        String updatedValue = sc.nextLine();
                        if (!updatedValue.equals("")) valueList.get(i).setValue(updatedValue);
                    }
                }
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            manager.getTransaction().rollback();
        }
    }
}
