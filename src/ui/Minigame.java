package ui;

public class Minigame {

    private int id;
    private String name;
    private int category;

    public Minigame(int id, String name, int category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCategory() {
        return category;
    }
}
