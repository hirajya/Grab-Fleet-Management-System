// Purpose: Model for car.
package model;
import java.sql.Date;

public class car {
    
    String car_Plate;
    String car_CRNum;
    String car_Series;
    String car_Kind;
    int car_YearModel;
    String car_Color;
    String car_ORNum;
    Date car_RegExpiry;
    Date car_Registration;
    String car_RegStatus;
    String car_Availability;

    public car(String car_Plate, String car_CRNum, String car_Series, String car_Kind, int car_YearModel, String car_Color, String car_ORNum, Date car_RegExpiry, Date car_Registration, String car_RegStatus, String car_Availability) {
        this.car_Plate = car_Plate;
        this.car_CRNum = car_CRNum;
        this.car_Series = car_Series;
        this.car_Kind = car_Kind;
        this.car_YearModel = car_YearModel;
        this.car_Color = car_Color;
        this.car_ORNum = car_ORNum;
        this.car_RegExpiry = car_RegExpiry;
        this.car_Registration = car_Registration;
        this.car_RegStatus = car_RegStatus;
        this.car_Availability = car_Availability;
    }

    public String getCar_Plate() {
        return car_Plate;
    }

    public void setCar_Plate(String car_Plate) {
        this.car_Plate = car_Plate;
    }

    public String getCar_CRNum() {
        return car_CRNum;
    }

    public void setCar_CRNum(String car_CRNum) {
        this.car_CRNum = car_CRNum;
    }

    public String getCar_Series() {
        return car_Series;
    }

    public void setCar_Series(String car_Series) {
        this.car_Series = car_Series;
    }

    public String getCar_Kind() {
        return car_Kind;
    }

    public void setCar_Kind(String car_Kind) {
        this.car_Kind = car_Kind;
    }

    public int getCar_YearModel() {
        return car_YearModel;
    }

    public void setCar_YearModel(int car_YearModel) {
        this.car_YearModel = car_YearModel;
    }

    public String getCar_Color() {
        return car_Color;
    }

    public void setCar_Color(String car_Color) {
        this.car_Color = car_Color;
    }

    public String getCar_ORNum() {
        return car_ORNum;
    }

    public void setCar_ORNum(String car_ORNum) {
        this.car_ORNum = car_ORNum;
    }

    public Date getCar_RegExpiry() {
        return car_RegExpiry;
    }

    public void setCar_RegExpiry(Date car_RegExpiry) {
        this.car_RegExpiry = car_RegExpiry;
    }

    public Date getCar_Registration() {
        return car_Registration;
    }

    public void setCar_Registration(Date car_Registration) {
        this.car_Registration = car_Registration;
    }

    public String getCar_RegStatus() {
        return car_RegStatus;
    }

    public void setCar_RegStatus(String car_RegStatus) {
        this.car_RegStatus = car_RegStatus;
    }

    public String getCar_Availability() {
        return car_Availability;
    }

    public void setCar_Availability(String car_Availability) {
        this.car_Availability = car_Availability;
    }

}
