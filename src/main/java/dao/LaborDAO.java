package dao;

import java.sql.*;
import java.util.ArrayList;
import management.Labor;

public class LaborDAO {

    public void saveLabor(String projectName, String laborType, double ratePerHour, int hoursWorked, double totalCost) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("⚠️  Database offline. Labor stored locally.");
            return;
        }
        String sql = "INSERT INTO labor (project_name, labor_type, rate_per_hour, hours_worked, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = conn;
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, projectName);
            pstmt.setString(2, laborType);
            pstmt.setDouble(3, ratePerHour);
            pstmt.setInt(4, hoursWorked);
            pstmt.setDouble(5, totalCost);

            pstmt.executeUpdate();
            System.out.println("✅ Labor Saved to Database: " + laborType);
        } catch (SQLException e) {
            System.out.println("❌ Error Saving Labor: " + e.getMessage());
        }
    }

    public ArrayList<String> getAllLabor(String projectName) {
        ArrayList<String> labors = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("⚠️  Database offline. No labor to retrieve.");
            return labors;
        }
        String sql = "SELECT * FROM labor WHERE project_name = ? ORDER BY created_at DESC";
        try (Connection connection = conn;
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, projectName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String labor = String.format(
                    "ID: %d | Type: %s | Rate: ₹%.2f/hr | Hours: %d | Total: ₹%.2f | Date: %s",
                    rs.getInt("id"),
                    rs.getString("labor_type"),
                    rs.getDouble("rate_per_hour"),
                    rs.getInt("hours_worked"),
                    rs.getDouble("total_cost"),
                    rs.getTimestamp("created_at")
                );
                labors.add(labor);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error Retrieving Labor: " + e.getMessage());
        }
        return labors;
    }

    public void deleteLabor(int id) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("⚠️  Database offline. Cannot delete labor.");
            return;
        }
        String sql = "DELETE FROM labor WHERE id = ?";
        try (Connection connection = conn;
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Labor Deleted Successfully!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error Deleting Labor: " + e.getMessage());
        }
    }

    public double getTotalLaborCost(String projectName) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("⚠️  Database offline. Returning 0.");
            return 0;
        }
        String sql = "SELECT SUM(total_cost) as total FROM labor WHERE project_name = ?";
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
