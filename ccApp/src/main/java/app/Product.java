package app;

public class Product {

        Integer id;
        String name;
        String unit;

        public Product(Integer id, String name, String unit) {
            this.id = id;
            this.name = name;
            this.unit = unit;
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

        public String getUnit() {
            return unit;
        }
    }


