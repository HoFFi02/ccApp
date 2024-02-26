package app;

public class Recipe {
    Integer id;
    String name;
    String preparation;

    public Recipe(Integer id, String name, String preparation) {
        this.id = id;
        this.name = name;
        this.preparation = preparation;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreparation() {
        return preparation;
    }
}
