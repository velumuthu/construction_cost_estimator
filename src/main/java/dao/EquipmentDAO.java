package dao;

import java.sql.*;
import java.util.ArrayList;

public class EquipmentDAO {

    public void saveEquipment(String projectName, String equipmentName, double rentalRate, int daysUsed, double totalCost) {
        String sql = "INSERT INTO equipment (project_name, equipment_name, rate_per_day, days_used, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projectName);
            pstmt.setString(2, equipmentName);
            pstmt.setDouble(3, rentalRate);
            pstmt.setInt(4, daysUsed);
            pstmt.setDouble(5, totalCost);

            pstmt.executeUpdate();
            System.out.println("✅ Equipment Saved to Database: " + equipmentName);
        } catch (SQLException e) {
            System.out.println("❌ Error Saving Equipment: " + e.getMessage());
        }
    }

    public ArrayList<String> getAllEquipment(String projectName) {
        ArrayList<String> equipments = new ArrayList<>();
        String sql = "SELECT * FROM equipment WHERE project_name = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projectName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String equipment = String.format(
                    "ID: %d | Name: %s | Rate: ₹%.2f/day | Days: %d | Total: ₹%.2f | Date: %s",
                    rs.getInt("id"),
                    rs.getString("equipment_name"),
                    rs.getDouble("rate_per_day"),
                    rs.getInt("days_used"),
                    rs.getDouble("total_cost"),
                    rs.getTimestamp("created_at")
                );
                equipments.add(equipment);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error Retrieving Equipment: " + e.getMessage());
        }
        return equipments;
    }

    public void deleteEquipment(int id) {
        String sql = "DELETE FROM equipment WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Equipment Deleted Successfully!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error Deleting Equipment: " + e.getMessage());
        }
    }

    public double getTotalEquipmentCost(String projectName) {
        String sql = "SELECT SUM(total_cost) as total FROM equipment WHERE project_name = ?";
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
