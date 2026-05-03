package model;

public class ProjectEmployee {

    private int prId;
    private int empId;

    public ProjectEmployee() {}

    public ProjectEmployee(int prId, int empId) {
        this.prId  = prId;
        this.empId = empId;
    }

    public int getPrId()  { return prId; }
    public int getEmpId() { return empId; }

    public void setPrId(int prId)   { this.prId  = prId; }
    public void setEmpId(int empId) { this.empId = empId; }
}