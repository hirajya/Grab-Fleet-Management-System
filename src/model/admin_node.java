package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;


public class admin_node {

    private int admin_Id;
    private String admin_ContactInfo;
    private String admin_Fname;
    private String admin_Mname; 
    private String admin_Lname; 
    private String admin_Username; 
    private String admin_Password; 
    private String admin_LicenseNum; 
    private String car_Plate; 
    
    public admin_node(int admin_id, String admin_ContactInfo, String admin_Fname, String admin_Mname, String admin_Lname, String admin_Username, String Password, String driver_LicenseNum, String car_Plate) {
        this.admin_Id = admin_Id;
        this.admin_ContactInfo = admin_ContactInfo;
        this.admin_Fname = admin_Fname;
        this.admin_Mname = admin_Mname; 
        this.admin_Lname = admin_Lname;
        this.admin_Username = admin_Username;
        this.admin_Password = admin_Password;
        this.admin_LicenseNum = admin_LicenseNum;
        this.car_Plate = car_Plate;
    }

    public int getAdmin_Id() {
        return admin_Id;
    }

    public String getAdmin_ContactInfo() {
        return admin_ContactInfo;
    }

    public String getAdmin_Fname() {
        return admin_Fname;
    }

    public String getAdmin_Mname() {
        return admin_Mname;
    }

    public String getAdmin_Lname() {
        return admin_Lname;
    }

    public String getAdmin_Username() {
        return admin_Username;
    }

    public String getAdmin_Password() {
        return admin_Password;
    }

    public String getAdmin_LicenseNum() {
        return admin_LicenseNum;
    }

    public String getCar_plate() {
        return car_Plate;
    }

}
