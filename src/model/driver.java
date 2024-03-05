package model;

import java.sql.Date;

public class driver {
    private String driver_LicenseNum;
    private Date driver_LicenseExpiry;
    private String driver_CNumber;
    private String driver_CPersonNum;
    private String driver_Sex;
    private String driver_FName;
    private String driver_MName;
    private String driver_LName;
    private Date driver_Birthdate;
    private int driver_HouseNum; 
    private String driver_Street;
    private String driver_Block; 
    private String driver_Brgy; 
    private String driver_City;
    private String car_Plate;

    public driver(String driver_LicenseNum, String driver_CNumber, String driver_CPersonNum, String driver_Sex, String driver_FName, String driver_MName, String driver_LName, Date driver_Birthdate, int driver_HouseNum, String driver_City, String driver_Street, String driver_Block, String driver_Brgy, String car_Plate, Date driver_LicenseExpiry) {
        this.driver_LicenseNum = driver_LicenseNum;
        this.driver_LicenseExpiry = driver_LicenseExpiry;
        this.driver_CNumber = driver_CNumber;
        this.driver_CPersonNum = driver_CPersonNum;
        this.driver_CNumber = driver_CNumber;
        this.driver_HouseNum = driver_HouseNum;
        this.driver_Street = driver_Street;
        this.driver_Block = driver_Block;
        this.driver_Brgy = driver_Brgy;
        this.driver_City = driver_City;
        this.driver_Sex = driver_Sex;
        this.driver_Birthdate = driver_Birthdate;
        this.driver_FName = driver_FName;
        this.driver_MName = driver_MName;
        this.driver_LName = driver_LName;
        this.car_Plate = car_Plate;
        

    }


    public String getDriver_LicenseNum() {
        return driver_LicenseNum;
    }

    public void setDriver_LicenseNum(String driver_LicenseNum) {
        this.driver_LicenseNum = driver_LicenseNum;
    }

    public Date getDriver_LicenseExpiry() {
        return driver_LicenseExpiry;
    }

    public void setDriver_LicenseExpiry(Date driver_LicenseExpiry) {
        this.driver_LicenseExpiry = driver_LicenseExpiry;
    }

    public String getDriver_FName() {
        return driver_FName;
    }

    public void setDriver_FName(String driver_FName) {
        this.driver_FName = driver_FName;
    }

    public String getDriver_MName() {
        return driver_MName;
    }

    public void setDriver_MName(String driver_MName) {
        this.driver_MName = driver_MName;
    }

    public String getDriver_LName() {
        return driver_LName; 
    }

    public void setDriver_LName(String driver_LName) {
        this.driver_LName = driver_LName;
    }

    public String getDriver_CNumber() {
        return driver_CNumber;
    }

    public void setDriver_CNumber(String driver_CNumber) {
        this.driver_CNumber = driver_CNumber;
    }

    public String getDriver_CPersonNum() {
        return driver_CPersonNum; 
    }

    public void setDriver_CPersonNum(String driver_CPersonNum) {
        this.driver_CPersonNum = driver_CPersonNum;
    }

    public String getDriver_Sex() {
        return driver_Sex;
    }
    
    public void setDriver_Sex(String driver_Sex) {
        this.driver_Sex = driver_Sex;
    }

    public Date getDriver_Birthdate() {
        return driver_Birthdate;
    }

    public void setDriver_Birthdate(Date driver_Birthdate) {
        this.driver_Birthdate = driver_Birthdate;
    }

    public int getDriver_HouseNum() {
        return driver_HouseNum;
    }

    public void setDriver_HouseNum(int driver_HouseNum) {
        this.driver_HouseNum = driver_HouseNum; 
    }

    public String getDriver_Street() {
        return driver_Street;
    }

    public void setDriver_Street(String driver_Street) {
        this.driver_Street = driver_Street;
    }

    public String getDriver_Block() {
        return driver_Block;
    }

    public void setDriver_Block(String driver_Block) {
        this.driver_Block = driver_Block; 
    }

    public String getDriver_Brgy() {
        return driver_Brgy;
    }

    public void setDriver_Brgy(String driver_Brgy) {
        this.driver_Brgy = driver_Brgy; 
    }

    public String getDriver_City() {
        return driver_City;
    }

    public void setDriver_City(String driver_City) {
        this.driver_City = driver_City;
    }

    public String getCar_Plate() {
        return car_Plate;
    }

    public void setCar_Plate(String car_Plate) {
        this.car_Plate = car_Plate;
    }

    
}