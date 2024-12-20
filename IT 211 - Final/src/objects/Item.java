package objects;

public class Item {
    private String name; int id;

    public Item()
    {

    }

    public Item(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public int getID()
    {
        return id;
    }
}
