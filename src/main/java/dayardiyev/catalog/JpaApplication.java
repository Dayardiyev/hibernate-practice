package dayardiyev.catalog;

import dayardiyev.catalog.crud.CreateProduct;
import dayardiyev.catalog.crud.DeleteProduct;
import dayardiyev.catalog.crud.UpdateProduct;
import dayardiyev.catalog.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class JpaApplication {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("""
                - Создание [1]
                - Редактирование [2]
                - Удаление [3]""");
        System.out.println("Выберите действие: ");

        int input = Integer.parseInt(sc.nextLine());

        switch (input) {
            case 1 -> {
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
                EntityManager manager = factory.createEntityManager();
                TypedQuery<Category> query = manager.createQuery(
                        "select c from Category c", Category.class
                );

                List<Category> categoryList = query.getResultList();

                for (Category category : categoryList) {
                    System.out.printf("- %s [%d]\n", category.getName(), category.getId());
                }
                CreateProduct.main(null);
            }
            case 2 -> UpdateProduct.main(null);
            case 3 -> DeleteProduct.main(null);
        }
    }
}
