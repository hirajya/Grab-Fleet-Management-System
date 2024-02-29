// Purpose: Amortization class to store amortization information.
package model;
import java.sql.Date;

public class amortization {
    int amortization_RecordID;
    String car_Plate;
    Date amortization_SDate;
    Date amortization_DDate;
    Date amortization_EDate;
    int amortization_Amount;
    int amortization_Payment;
    String amortization_Status;
    

    public amortization(int amortization_RecordID, String car_Plate, Date amortization_SDate, Date amortization_DDate, Date amortization_EDate, int amortization_Payment, String amortization_Status) {
        this.amortization_RecordID = amortization_RecordID;
        this.car_Plate = car_Plate;
        this.amortization_SDate = amortization_SDate;
        this.amortization_DDate = amortization_DDate;
        this.amortization_EDate = amortization_EDate;
        this.amortization_Payment = amortization_Payment;
        this.amortization_Status = amortization_Status;
     
    }

    public void setAmortization_RecordID(int amortization_RecordID) {
        this.amortization_RecordID = amortization_RecordID;
    }

    public Date getAmortization_SDate() {
        return amortization_SDate;
    }

    public void setAmortization_SDate(Date amortization_SDate) {
        this.amortization_SDate = amortization_SDate;
    }

    public Date getAmortization_DDate() {
        return amortization_DDate;
    }

    public void setAmortization_DDate(Date amortization_DDate) {
        this.amortization_DDate = amortization_DDate;
    }
    
    public Date getAmortization_EDate() {
        return amortization_EDate;
    }

    public void setAmortization_EDate(Date amortization_EDate) {
        this.amortization_EDate = amortization_EDate;
    }

    public int getAmortization_Amount() {
        return amortization_Amount;
    }

    public void setAmortization_Amount(int amortization_Amount) {
        this.amortization_Amount = amortization_Amount;
    }

    public int getAmortization_Payment() {
        return amortization_Payment;
    }

    public void setAmortization_Payment(int amortization_Payment) {
        this.amortization_Payment = amortization_Payment;
    }

    public String getCar_Plate() {
        return car_Plate;
    }

    public void setCar_Plate(String car_Plate) {
        this.car_Plate = car_Plate;
    }

    public String getAmortization_Status() {
        return amortization_Status;
    }

    public void setAmortization_Status(String amortization_Status) {
        this.amortization_Status = amortization_Status;
    }

    public int getAmortization_RecordID() {
        return amortization_RecordID;
    }

    
}


