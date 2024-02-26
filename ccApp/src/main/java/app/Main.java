package app;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws IOException, SQLException {
        System.out.println("Start programu");

        System.out.println("Konfiguracja warstwy persistance");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Produkty");
        MainMenu.menu(emf);
    }
}
