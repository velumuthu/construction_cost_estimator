package materials;

public class Bricks extends Material {

    public Bricks(double unitCost, double quantity) {
        super("Bricks", unitCost, quantity);
    }

    @Override
    public double calculateCost() {
        if (quantity > 1000)
            return (unitCost * quantity) * 0.95; // 5% discount
        else
            return unitCost * quantity;
    }
}