package model;

public class maintenance {
    int recordId;
    String driverName;
    String licenseNumber;
    int amount;
    int paidAmount;
    int balance;
    String startDate;
    String dueDate;
    String status;

    public maintenance(int recordId, String driverName, String licenseNumber, int amount, int paidAmount, int balance, String startDate, String dueDate, String status) {
        this.recordId = recordId;
        this.driverName = driverName;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

}
