package manup;

import java.sql.*;
import java.time.LocalDate;

public class Queries {
    public final String SERVER = "b8kvkjnhmaouphujwxhh-postgresql.services.clever-cloud.com";
    public final String USER = "utzkadhhhw5qgb5v9ksr";
    public final int PORT = 50013;
    public final String DATABASE = "b8kvkjnhmaouphujwxhh";
    public final String PASSWORD = "HEa7y8VRvEkJR8htpfPyjODsRvxxJ2";
    private Connection conn;

    public Queries() {
        try {
            String url = "jdbc:postgresql://%s:%s/%s".formatted(SERVER, PORT, DATABASE);
            conn = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printPersonCount() {
        try {
            String query = "SELECT COUNT(*) FROM person";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long count = resultSet.getLong(1);
            System.out.printf("Current person count in database: %s%n", count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAll() {
        try {
            String query = "SELECT * FROM person";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            System.out.printf("%-5s %-15s %-15s %-25s %-15s %-12s %-10s %-10s%n", "ID", "First Name", "Last Name", "E-Mail", "Country", "Birthday", "Salary", "Bonus");
            while (rs.next()) {
                int id = rs.getInt(1);
                String first_name = rs.getString(2);
                String last_name = rs.getString(3);
                String email = rs.getString(4);
                String country = rs.getString(5);
                Date birthday = rs.getDate(6);
                int salary = rs.getInt(7);
                int bonus = rs.getInt(8);
                System.out.printf("%-5d %-15s %-15s %-25s %-15s %-12s %-10d %-10d%n", id, first_name, last_name, email, country, birthday, salary, bonus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData(String fN, String lN, String email, String country, String birthday, int salary, int bonus) {
        try {
            String query = "INSERT INTO person(first_name, last_name, email, country, birthday, salary, bonus) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fN);
            stmt.setString(2, lN);
            stmt.setString(3, email);
            stmt.setString(4, country);
            stmt.setDate(5, Date.valueOf(birthday));
            stmt.setInt(6, salary);
            stmt.setInt(7, bonus);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateData(int id, String column, String value) {
        try {
            String query = "UPDATE person SET " + column + " = " + value + "WHERE id = " + id;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(int id, String column, int value) {
        try {
            String query = "UPDATE person SET " + column + " = " + value + "WHERE id = " + id;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(int id, String column, Date value) {
        try {
            String query = "UPDATE person SET " + column + " = " + value + "WHERE id = " + id;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData(int id) {
        try {
            String query = "DELETE FROM person WHERE id = " + id;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllData() {
        try {
            String query = "DELETE FROM person";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
