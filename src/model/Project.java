package model; 
public class Project { 
  private int id; 
  private String title; 
  private int clientId; 
  public Project(int id, String title, int clientId) { 
    this.id = id; this.title = title; this.clientId = clientId; } 
  public int getId() { return id; } 
  public String getTitle() { return title; } 
  public int getClientId() { return clientId; } 
  public void setId(int id) { this.id = id; } 
  public void setTitle(String title) { this.title = title; } 
  public void setClientId(int clientId) { this.clientId = clientId; } 
}
