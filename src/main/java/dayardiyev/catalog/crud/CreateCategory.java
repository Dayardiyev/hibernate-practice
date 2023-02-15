package dayardiyev.catalog.crud;

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

        System.out.print("Введите название категории: ");
        String inputName = sc.nextLine();

        TypedQuery<Category> query = manager.createQuery(
                "select c from Category c where c.name = ?1", Category.class
        );
        query.setParameter(1, inputName);

        List<Category> categories = query.getResultList();
        if (!categories.isEmpty()) {
            System.out.printf("Категория с названием `%s` уже существует", inputName);
            return;
        }

        System.out.println("Введите характеристику категории(через запятую): ");
        String optionsName = sc.nextLine();
        String[] optionsArr = optionsName.split(", ");

        try {
            manager.getTransaction().begin();

            if (inputName != null) {
                Category category = new Category();
                category.setName(inputName);
                manager.persist(category);
                for (String str : optionsArr) {
                    Option option = new Option();
                    option.setCategory(category);
                    option.setName(str);
                    manager.persist(option);
                }
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
