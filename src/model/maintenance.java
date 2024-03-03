package model;

public class maintenance {
    int maintenanceId;
    String carSeries;
    String carPlate;
    String licenseNumber;
    String changeOil;
    String changeBelt;
    String status;

    public maintenance(int maintenanceId, String carSeries, String carPlate, String licenseNumber, String changeOil, String changeBelt, String status) {
        this.maintenanceId = maintenanceId;
        this.carSeries = carSeries;
        this.carPlate = carPlate;
        this.licenseNumber = licenseNumber;
        this.changeOil = changeOil;
        this.changeBelt = changeBelt;
        this.status = status;
    }

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void getMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getChangeOil() {
        return changeOil;
    }

    public void setChangeOil(String changeOil) {
        this.changeOil = changeOil;
    }

    public String getChangeBelt() {
        return changeBelt;
    }

    public void setChangeBelt(String changeBelt) {
        this.changeBelt = changeBelt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
