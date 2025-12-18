package materials;

public class Steel extends Material {

    public Steel(double unitCost, double quantity) {
        super("Steel", unitCost, quantity);
    }

    @Override
    public double calculateCost() {
        return (unitCost * quantity) * 1.05; // 5% wastage charge
    }
}