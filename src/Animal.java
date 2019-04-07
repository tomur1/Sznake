public class Animal {
    private String name;
    private int weight, height;

    Animal(String name, int weight, int height) {
        this.name = name;
        this.weight = weight;
        this.height = height;

    }

    Animal() {
        this("kot", 15, 10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int compareToName(Animal b) {
        return name.compareTo(b.name);
    }


    public int compareToWeight(Animal b) {
        if (weight > b.weight) {
            return 1;
        } else if (weight < b.weight) {
            return -1;
        } else {
            return 0;
        }
    }

    public int compareToHeight(Animal b) {
        if (height > b.height) {
            return 1;
        } else if (height < b.height) {
            return -1;
        } else {
            return 0;
        }
    }

    public String toString() {
        return name + " weight: " + weight + " height: " + height;
    }
}
