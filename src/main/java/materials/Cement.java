package materials;

public class Cement extends Material {

    public Cement(double unitCost, double quantity) {
        super("Cement", unitCost, quantity);
    }

    @Override
    public double calculateCost() {
        return unitCost * quantity;
    }
}