package materials;

public abstract class Material {
    protected String name;
    protected double unitCost;
    protected double quantity;

    public Material(String name, double unitCost, double quantity) {
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
    }

    public abstract double calculateCost();

    public String getName() {
        return name;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public double getQuantity() {
        return quantity;
    }
}