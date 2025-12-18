package management;

public class Equipment {
    private String equipmentName;
    private double rentalRate;
    private int daysUsed;

    public Equipment(String equipmentName, double rentalRate, int daysUsed) {
        this.equipmentName = equipmentName;
        this.rentalRate = rentalRate;
        this.daysUsed = daysUsed;
    }

    public double calculateEquipmentCost() {
        return rentalRate * daysUsed;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public int getDaysUsed() {
        return daysUsed;
    }
}