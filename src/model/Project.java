package model;

public class Project {

    private int id;
    private String title;
    private int clientId;
    private String location;
    private double budget;

    public Project() {}

    public Project(int id, String title, int clientId) {
        this.id = id;
        this.title = title;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
}