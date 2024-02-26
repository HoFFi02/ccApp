package app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionClass {

    private static Connection conn;
    static Connection getConnection() {
  //  public static void getConnection() throws IOException {

        //System.out.println("Start");
      //  Connection conn = null;

        Properties p = new Properties();
        try( FileInputStream input = new FileInputStream("config.properties")){
            p.load(input);
           // System.out.println("Wczytano istniejacy plik konfiguracyjny.");
        }catch(IOException e){
           // System.out.println("Nie znaleziono istniejacego pliku konfiguracyjnego. Tworzenie nowego...");
            p.setProperty("host", "localhost");
            p.setProperty("port", "55555");
            p.setProperty("database", "mydb");
            p.setProperty("user", "root");
            p.setProperty("password", "null");
        }

        try(FileOutputStream output = new FileOutputStream("config.properties")){
            p.store(output, "Plik konfiguracyjny");
          //  System.out.println("Nowy plik konfiguracyjny utworzony i zapisany.");
        }catch(IOException e){
            e.printStackTrace();
        }




        String connection = String.format("jdbc:mysql://%s:%s/%s?user=%s&serverTimezone=UTC&characterEncoding=utf8", p.getProperty("host"), p.getProperty("port"), p.getProperty("database"), p.getProperty("user"));
        System.out.println(connection);

        try {
            conn = DriverManager.getConnection(connection);
           // System.out.println("Jest połaczenie :)");
        } catch (SQLException ex) {

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }
}
