package app;


import javax.persistence.*;

@Entity(name = "Produkt")
@Table(name = "produkty")

public class ProductHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_produkty")
    private Integer id;


    private String nazwa;
    private String jednostka;

    public ProductHibernate() {}

    public ProductHibernate(String nazwa, String jednostka) {
        this.nazwa = nazwa;
        this.jednostka = jednostka;
    }

    // Gettery i settery

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return nazwa;
    }

    public void setName(String name) {
        this.nazwa = name;
    }

    public String getUnit() {
        return jednostka;
    }

    public void setUnit(String unit) {
        this.jednostka = unit;
    }

    @Override
    public String toString() {
        return id + " " + nazwa;
    }


}
