package model.object_model;

public class Driver_Quota_obj {
    int recordId;
    String licenseNumber;
    double amount;
    double paidAmount;
    double balance;
    String startDate;
    String dueDate;
    String status;

    public Driver_Quota_obj(int recordId, String licenseNumber, double amount, double paidAmount, double balance, String startDate, String dueDate, String status) {
        this.recordId = recordId;
        this.licenseNumber = licenseNumber;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.balance = balance;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
