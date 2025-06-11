package org.example;
import java.sql.*;
import java.util.Scanner;

public class SafeQueryManager {
    public void queryDatabase(String username, String password, String url) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter EmployeeID:");
            String employeeIdInput = scanner.nextLine(); // Allow any string input

            String query = "SELECT FirstName, LastName, Notes FROM Employees WHERE EmployeeID = ?";
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = conn.prepareStatement(query)) {

                // Even if user enters SQL code, it's treated as a literal value
                ps.setString(1, employeeIdInput);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Employee: " + rs.getString("FirstName") + " " +
                                rs.getString("LastName") + " " +
                                rs.getString("Notes"));
                    } else {
                        System.out.println("No employee found with that ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

