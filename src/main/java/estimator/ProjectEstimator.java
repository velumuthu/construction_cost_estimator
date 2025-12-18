package estimator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import materials.Material;
import management.Labor;
import management.Equipment;
import dao.*;

public class ProjectEstimator {
    private ArrayList<Material> materials = new ArrayList<>();
    private ArrayList<Labor> labors = new ArrayList<>();
    private ArrayList<Equipment> equipments = new ArrayList<>();
    
    private MaterialDAO materialDAO = new MaterialDAO();
    private LaborDAO laborDAO = new LaborDAO();
    private EquipmentDAO equipmentDAO = new EquipmentDAO();
    private String projectName;

    public ProjectEstimator(String projectName) {
        this.projectName = projectName;
        System.out.println("Project set to: " + this.projectName);
    }

    public void addMaterial(Material m) {
        materials.add(m);
        // Save to database
        materialDAO.saveMaterial(projectName, m.getName(), m.getUnitCost(), m.getQuantity(), m.calculateCost());
    }

    public void addLabor(Labor l) {
        labors.add(l);
        // Save to database
        laborDAO.saveLabor(projectName, l.getLaborType(), l.getRatePerHour(), l.getHoursWorked(), l.calculateLaborCost());
    }

    public void addEquipment(Equipment e) {
        equipments.add(e);
        // Save to database
        equipmentDAO.saveEquipment(projectName, e.getEquipmentName(), e.getRentalRate(), e.getDaysUsed(), e.calculateEquipmentCost());
    }

    public void viewAllMaterials() {
        System.out.println("\n===== All Materials from Database =====");
        ArrayList<String> materials = materialDAO.getAllMaterials(projectName);
        if (materials.isEmpty()) {
            System.out.println("No materials found!");
        } else {
            for (String material : materials) {
                System.out.println(material);
            }
        }
    }

    public void viewAllLabor() {
        System.out.println("\n===== All Labor Records from Database =====");
        ArrayList<String> labors = laborDAO.getAllLabor(projectName);
        if (labors.isEmpty()) {
            System.out.println("No labor records found!");
        } else {
            for (String labor : labors) {
                System.out.println(labor);
            }
        }
    }

    public void viewAllEquipment() {
        System.out.println("\n===== All Equipment from Database =====");
        ArrayList<String> equipments = equipmentDAO.getAllEquipment(projectName);
        if (equipments.isEmpty()) {
            System.out.println("No equipment found!");
        } else {
            for (String equipment : equipments) {
                System.out.println(equipment);
            }
        }
    }

    public void generateReport() {
        // Get totals from database
        double totalMaterialCost = materialDAO.getTotalMaterialCost(projectName);
        double totalLaborCost = laborDAO.getTotalLaborCost(projectName);
        double totalEquipmentCost = equipmentDAO.getTotalEquipmentCost(projectName);

        double grandTotal = totalMaterialCost + totalLaborCost + totalEquipmentCost;
        ArrayList<String> materialList = materialDAO.getAllMaterials(projectName);
        ArrayList<String> laborList = laborDAO.getAllLabor(projectName);
        ArrayList<String> equipmentList = equipmentDAO.getAllEquipment(projectName);

        try (FileWriter writer = new FileWriter("CostReport.txt")) {
            writer.write("----- Construction Cost Report -----\n");
            writer.write(String.format("Project: %s\n", projectName));
            writer.write("\n--- Materials ---\n");
            if (materialList.isEmpty()) {
                writer.write("No materials recorded for this project.\n");
            } else {
                for (String mat : materialList) {
                    writer.write(mat + "\n");
                }
            }
            writer.write(String.format("Total Material Cost: ₹%.2f\n", totalMaterialCost));

            writer.write("\n--- Labor ---\n");
            if (laborList.isEmpty()) {
                writer.write("No labor recorded for this project.\n");
            } else {
                for (String lab : laborList) {
                    writer.write(lab + "\n");
                }
            }
            writer.write(String.format("Total Labor Cost: ₹%.2f\n", totalLaborCost));

            writer.write("\n--- Equipment ---\n");
            if (equipmentList.isEmpty()) {
                writer.write("No equipment recorded for this project.\n");
            } else {
                for (String eq : equipmentList) {
                    writer.write(eq + "\n");
                }
            }
            writer.write(String.format("Total Equipment Cost: ₹%.2f\n", totalEquipmentCost));

            writer.write("\n-------------------------------------\n");
            writer.write(String.format("Grand Total: ₹%.2f\n", grandTotal));
            System.out.println("\n✅ Report generated successfully! Saved as CostReport.txt");
        } catch (IOException ex) {
            System.out.println("Error writing report: " + ex.getMessage());
        }
    }
}