package dayardiyev.catalog.controller;

import dayardiyev.catalog.entity.Option;
import dayardiyev.catalog.entity.Product;
import dayardiyev.catalog.entity.Value;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class UpdateProduct {

    static final Scanner sc = new Scanner(System.in);

    public static void execute(EntityManager manager) {

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

            List<Option> optionList = product.getCategory().getOptions();

            for (Option option : optionList) {

                TypedQuery<Value> query = manager.createQuery(
                        "select v from Value v where v.option = ?1 and v.product = ?2", Value.class
                );
                query.setParameter(1, option);
                query.setParameter(2, product);

                List<Value> valueList = query.getResultList();
                if (!valueList.isEmpty()) {
                    System.out.printf("%s [%s]: ", option.getName(), valueList.get(0).getValue());
                    String updatedValue = sc.nextLine();
                    if (!updatedValue.equals("")) valueList.get(0).setValue(updatedValue);
                } else {
                    System.out.printf("%s [null]: ", option.getName());
                    String newValue = sc.nextLine();
                    if (!newValue.equals("")) {
                        Value value = new Value();
                        value.setProduct(product);
                        value.setOption(option);
                        value.setValue(newValue);
                        manager.persist(value);
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
