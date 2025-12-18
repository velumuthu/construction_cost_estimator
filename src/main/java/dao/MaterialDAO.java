package dao;

import java.sql.*;
import java.util.ArrayList;
import materials.Material;
import materials.Cement;
import materials.Steel;
import materials.Sand;
import materials.Bricks;

public class MaterialDAO {

    public void saveMaterial(String projectName, String materialType, double unitCost, double quantity, double totalCost) {
        String sql = "INSERT INTO materials (project_name, material_type, unit_cost, quantity, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projectName);
            pstmt.setString(2, materialType);
            pstmt.setDouble(3, unitCost);
            pstmt.setDouble(4, quantity);
            pstmt.setDouble(5, totalCost);

            pstmt.executeUpdate();
            System.out.println("✅ Material Saved to Database: " + materialType);
        } catch (SQLException e) {
            System.out.println("❌ Error Saving Material: " + e.getMessage());
        }
    }

    public ArrayList<String> getAllMaterials(String projectName) {
        ArrayList<String> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials WHERE project_name = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projectName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String material = String.format(
                    "ID: %d | Type: %s | Unit Cost: ₹%.2f | Qty: %.2f | Total: ₹%.2f | Date: %s",
                    rs.getInt("id"),
                    rs.getString("material_type"),
                    rs.getDouble("unit_cost"),
                    rs.getDouble("quantity"),
                    rs.getDouble("total_cost"),
                    rs.getTimestamp("created_at")
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error Retrieving Materials: " + e.getMessage());
        }
        return materials;
    }

    public void deleteMaterial(int id) {
        String sql = "DELETE FROM materials WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Material Deleted Successfully!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error Deleting Material: " + e.getMessage());
        }
    }

    public double getTotalMaterialCost(String projectName) {
        String sql = "SELECT SUM(total_cost) as total FROM materials WHERE project_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projectName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error Calculating Total: " + e.getMessage());
        }
        return 0;
    }
}
