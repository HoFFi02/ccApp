package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.System.exit;

class Menu {
    public static void menu(JFrame frame, JTable table, EntityManagerFactory emf){

JMenu plik,  wyswietl, dodaj, about, usun, edytuj;


JMenuItem wyswietl_przepisy = new JMenuItem("Przepisy");
        wyswietl_przepisy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
        wyswietl_przepisy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setEnabled(false);
                    SelectFrame.recipe();

                } catch (IOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


JMenuItem wyswietl_produkty = new JMenuItem("Produkty");
   wyswietl_produkty.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        wyswietl_produkty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setEnabled(false);
                    SelectFrame.product(emf);

                } catch (IOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


JMenuItem wyswietl_lista_zakupow = new JMenuItem("Lista zakupow");
        wyswietl_lista_zakupow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));
        wyswietl_lista_zakupow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Unimplemented", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });


JMenuItem wyswietl_dni = new JMenuItem("Dni");
        wyswietl_dni.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        wyswietl_dni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Unimplemented", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });


JMenuItem dodaj_przepis = new JMenuItem("Przepis");
        dodaj_przepis.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
        dodaj_przepis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setEnabled(false);
                try {
                    InsertFrame.recipe(table, emf);
                } catch (IOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


JMenuItem edytuj_przepis = new JMenuItem("Przepis");
        edytuj_przepis.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
        edytuj_przepis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setEnabled(false);
                    UpdateFrame.recipeNoValues(table);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        JMenuItem dodaj_produkt = new JMenuItem("Produkt");
    dodaj_produkt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        dodaj_produkt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setEnabled(false);
                try {
                    InsertFrame.product(emf);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


JMenuItem usun_produkt = new JMenuItem("Produkt");
   usun_produkt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        usun_produkt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setEnabled(false);
                    DeleteFrame.product(emf);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


JMenuItem usun_przepis = new JMenuItem("Przepis");
        usun_przepis.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
        usun_przepis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setEnabled(false);
                    DeleteFrame.recipe2(table);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean isRowSelected = !rowSM.isSelectionEmpty();
                edytuj_przepis.setEnabled(isRowSelected);
                usun_przepis.setEnabled(isRowSelected);
            }
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit(0);
            }
        });


        JMenuItem credits = new JMenuItem("Credits");
        credits.setMnemonic(KeyEvent.VK_C);
        credits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutWindow(frame);
            }
        });

        edytuj_przepis.setEnabled(!rowSM.isSelectionEmpty());
        usun_przepis.setEnabled(!rowSM.isSelectionEmpty());

JMenuBar menuBar=new JMenuBar();
plik=new JMenu("Plik");
plik.setMnemonic(KeyEvent.VK_P);
wyswietl=new JMenu("Wyswietl");
wyswietl.setMnemonic(KeyEvent.VK_W);
dodaj=new JMenu("Dodaj");
dodaj.setMnemonic(KeyEvent.VK_D);
usun=new JMenu("Usun");
usun.setMnemonic(KeyEvent.VK_U);
edytuj=new JMenu("Edytuj");
edytuj.setMnemonic(KeyEvent.VK_E);
about=new JMenu("About");
about.setMnemonic(KeyEvent.VK_A);
wyswietl.add(wyswietl_dni); wyswietl.add(wyswietl_przepisy); wyswietl.add(wyswietl_produkty); wyswietl.add(wyswietl_lista_zakupow);
dodaj.add(dodaj_przepis);

dodaj.add(dodaj_produkt);
edytuj.add(edytuj_przepis);
usun.add(usun_przepis);usun.add(usun_produkt);
plik.add(wyswietl);plik.add(dodaj); plik.add(usun); plik.add(edytuj); plik.add(exit);
about.add(credits);
menuBar.add(plik);
menuBar.add(about);
frame.setJMenuBar(menuBar);
}


    private static void showAboutWindow(JFrame frame) {

        JFrame aboutFrame = new JFrame("About Application");

        String aboutMessage = "<html><center>" +
                "<b>Application Version 8.1</b><br>" +
                "<br>" +
                "<b>Creators:</b><br>" +
                "Michal Hoffman<br>" +
                "<br>" +
                "<b>Technology:</b><br>" +
                "Java<br>" +
                "MySQL</center></html>";


        JLabel label = new JLabel(aboutMessage);
        label.setHorizontalAlignment(JLabel.CENTER);


        aboutFrame.add(label);


        aboutFrame.setSize(300, 200);
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutFrame.setLocationRelativeTo(null); // Center the aboutFrame on the screen
        aboutFrame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                MainMenu.close();
            }
        });
    }
}
