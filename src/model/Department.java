package model;

public class Department {

    private int    id;
    private String name;
    private String location;

    public Department() {}

    public Department(int id, String name, String location) {
        this.id       = id;
        this.name     = name;
        this.location = location;
    }

    public int    getId()       { return id; }
    public String getName()     { return name; }
    public String getLocation() { return location; }

    public void setId(int id)             { this.id       = id; }
    public void setName(String name)      { this.name     = name; }
    public void setLocation(String loc)   { this.location = loc; }
}