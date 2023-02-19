package dayardiyev.catalog;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Scanner;

public class Task {

    public static void main(String[] args) {

        // Введите id категории: ___
        // Введите процент: ___

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        Scanner sc = new Scanner(System.in);

        System.out.print("Введите id категории: ");
        long id = Long.parseLong(sc.nextLine());

        System.out.print("Введите процент: ");
        int percentage = Integer.parseInt(sc.nextLine());

        try {
            manager.getTransaction().begin();

            Query query = manager.createQuery(
                    "update Product p set p.price = p.price + p.price * ?1 / 100 where p.category.id = ?2"
            );
            query.setParameter(1, percentage);
            query.setParameter(2, id);
            query.executeUpdate();

            manager.getTransaction().commit();
        } catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
