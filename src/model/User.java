package model; 
import java.sql.Date; 
public class User { 
  private int id; 
  private String username; 
  private String password; 
  private String firstName; 
  private String lastName; 
  private Date dateOfBirth; 
  private int age; 
  private String address; 
  private byte[] photo; 
  public User() {} 
  public int getId() { return id; } 
  public void setId(int id) { this.id = id; } 
  public String getUsername() { return username; } 
  public void setUsername(String username) { this.username = username; } 
  public String getPassword() { return password; } 
  public void setPassword(String password) { this.password = password; } 
  public String getFirstName() { return firstName; } 
  public void setFirstName(String firstName) { this.firstName = firstName; } 
  public String getLastName() { return lastName; } 
  public void setLastName(String lastName) { this.lastName = lastName; } 
  public Date getDateOfBirth() { return dateOfBirth; } 
  public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; } 
  public int getAge() { return age; } 
  public void setAge(int age) { this.age = age; } 
  public String getAddress() { return address; } 
  public void setAddress(String address) { this.address = address; } 
  public byte[] getPhoto() { return photo; } 
  public void setPhoto(byte[] photo) { this.photo = photo; } 
}
