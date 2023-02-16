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
        String inputCategoryName = sc.nextLine();

        TypedQuery<Category> query = manager.createQuery(
                "select c from Category c where c.name = ?1", Category.class
        );
        query.setParameter(1, inputCategoryName);

        List<Category> categories = query.getResultList();
        if (!categories.isEmpty()) {
            System.out.printf("Категория с названием `%s` уже существует", inputCategoryName);
            return;
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
