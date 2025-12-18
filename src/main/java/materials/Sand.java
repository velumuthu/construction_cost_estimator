package materials;

public class Sand extends Material {

    public Sand(double unitCost, double quantity) {
        super("Sand", unitCost, quantity);
    }

    @Override
    public double calculateCost() {
        return (unitCost * quantity) + 500; // handling fee
    }
}