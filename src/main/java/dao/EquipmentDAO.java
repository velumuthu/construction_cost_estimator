package dao;

import java.sql.*;
import java.util.ArrayList;

public class EquipmentDAO {

    public void saveEquipment(String projectName, String equipmentName, double rentalRate, int daysUsed, double totalCost) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("⚠️  Database offline. Equipment stored locally.");
            return;
        }
        String sql = "INSERT INTO equipment (project_name, equipment_name, rate_per_day, days_used, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = conn;
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

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
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("⚠️  Database offline. No equipment to retrieve.");
            return equipments;
        }
        String sql = "SELECT * FROM equipment WHERE project_name = ? ORDER BY created_at DESC";
        try (Connection connection = conn;
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

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
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("⚠️  Database offline. Cannot delete equipment.");
            return;
        }
        String sql = "DELETE FROM equipment WHERE id = ?";
        try (Connection connection = conn;
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

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
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("⚠️  Database offline. Returning 0.");
            return 0;
        }
        String sql = "SELECT SUM(total_cost) as total FROM equipment WHERE project_name = ?";
        try (Connection connection = conn;
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

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
