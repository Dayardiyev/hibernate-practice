package dayardiyev.catalog.controller;

import dayardiyev.catalog.entity.Category;
import dayardiyev.catalog.entity.Option;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class CreateCategory {

    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
    static EntityManager manager = factory.createEntityManager();

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String inputCategoryName;

        while (true) {
            System.out.print("Введите название категории: ");
            inputCategoryName = sc.nextLine();
            if (inputCategoryName.equals("")) {
                System.out.println("Название категории не должно быть пустым значением!");
                continue;
            }

            TypedQuery<Category> query = manager.createQuery(
                    "select c from Category c where c.name = ?1", Category.class
            );
            query.setParameter(1, inputCategoryName);

            List<Category> categories = query.getResultList();
            if (!categories.isEmpty()) {
                System.out.printf("Категория с названием `%s` уже существует\n", inputCategoryName);
            } else {
                break;
            }
        }

        System.out.println("Введите характеристику категории(через запятую): ");
        String inputOptionsName = sc.nextLine();
        String[] optionsArr = inputOptionsName.split(", ");

        try {
            manager.getTransaction().begin();

            Category category = new Category();
            category.setName(inputCategoryName);
            manager.persist(category);

            for (String str : optionsArr) {
                Option option = new Option();
                option.setCategory(category);
                option.setName(str);
                manager.persist(option);
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
