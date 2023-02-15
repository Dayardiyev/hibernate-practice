package dayardiyev.catalog.crud;

import dayardiyev.catalog.entity.enums.Role;
import dayardiyev.catalog.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateUser {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        Scanner sc = new Scanner(System.in);

        try {
            manager.getTransaction().begin();

            System.out.println("\nВведите логин пользователя: ");
            String login = sc.nextLine();

            System.out.print("\nВведите пароль пользователя: ");
            String password = sc.nextLine();

            System.out.print("\nВведите имя пользователя: ");
            String name = sc.nextLine();

            System.out.print("\nВведите фамилию пользователя: ");
            String lastname = sc.nextLine();

            User user = new User(login, password, name, lastname, Role.USER, LocalDateTime.now());
            manager.persist(user);

            manager.getTransaction().commit();
            System.out.printf("User %s had been created\n", login);
        } catch (Exception e) {
            e.printStackTrace();
            manager.getTransaction().rollback();
        }
    }
}
