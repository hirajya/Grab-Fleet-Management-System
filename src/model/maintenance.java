// Purpose: Maintenance class to store maintenance information.
package model;
import java.sql.Date;

public class maintenance {
    int maintenance_RecordID;
    String car_Plate;
    Date maintenance_SDate;
    Date maintenance_DDate;
    Date maintenance_EDate;
    int maintenance_Amount;
    int maintenance_Payment;
    String maintenance_Status;
    

    public maintenance(int maintenance_RecordID, String car_Plate, Date maintenance_SDate, Date maintenance_DDate, Date maintenance_EDate, int maintenance_Payment, String maintenance_Status) {
        this.maintenance_RecordID = maintenance_RecordID;
        this.car_Plate = car_Plate;
        this.maintenance_SDate = maintenance_SDate;
        this.maintenance_DDate = maintenance_DDate;
        this.maintenance_EDate = maintenance_EDate;
        this.maintenance_Payment = maintenance_Payment;
        this.maintenance_Status = maintenance_Status;
     
    }

    public void setMaintenance_RecordID(int maintenance_RecordID) {
        this.maintenance_RecordID = maintenance_RecordID;
    }

    public Date getMaintenance_SDate() {
        return maintenance_SDate;
    }

    public void setMaintenance_SDate(Date maintenance_SDate) {
        this.maintenance_SDate = maintenance_SDate;
    }

    public Date getMaintenance_DDate() {
        return maintenance_DDate;
    }

    public void setMaintenance_DDate(Date maintenance_DDate) {
        this.maintenance_DDate = maintenance_DDate;
    }
    
    public Date getMaintenance_EDate() {
        return maintenance_EDate;
    }

    public void setMaintenance_EDate(Date maintenance_EDate) {
        this.maintenance_EDate = maintenance_EDate;
    }

    public int getMaintenance_Amount() {
        return maintenance_Amount;
    }

    public void setMaintenance_Amount(int maintenance_Amount) {
        this.maintenance_Amount = maintenance_Amount;
    }

    public int getMaintenance_Payment() {
        return maintenance_Payment;
    }

    public void setMaintenance_Payment(int maintenance_Payment) {
        this.maintenance_Payment = maintenance_Payment;
    }

    public String getCar_Plate() {
        return car_Plate;
    }

    public void setCar_Plate(String car_Plate) {
        this.car_Plate = car_Plate;
    }

    public String getMaintenance_Status() {
        return maintenance_Status;
    }

    public void setMaintenance_Status(String maintenance_Status) {
        this.maintenance_Status = maintenance_Status;
    }

    public int getMaintenance_RecordID() {
        return maintenance_RecordID;
    }

    
}
