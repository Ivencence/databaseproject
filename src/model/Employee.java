package model; 
public class Employee { 
  private int id; 
  private String name; 
  private int age; 
  private String email; 
  private int deptId; 
  public Employee(int id, String name, int age, String email, int deptId) { 
    this.id = id; 
    this.name = name; 
    this.age = age; 
    this.email = email; 
    this.deptId = deptId; 
  } 
  public Employee() {} 
  public int getId() { return id; } 
  public String getName() { return name; } 
  public int getAge() { return age; } 
  public String getEmail() { return email; } 
  public int getDeptId(){return deptId;} 
  public void setId(int id) { this.id = id; } 
  public void setName(String name) { this.name = name; } 
  public void setAge(int age) { this.age = age; } 
  public void setEmail(String email) { this.email = email; } 
  public void setDeptId(int deptId){this.deptId = deptId;} 
}
