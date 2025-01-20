package manup;

import java.sql.*;
import java.util.ArrayList;

public class Queries {

    private Connection conn;

    public Queries() {
        try {
            String url = "jdbc:postgresql://%s:%s/%s".formatted(Creds.SERVER, Creds.PORT, Creds.DATABASE);
            conn = DriverManager.getConnection(url, Creds.USER, Creds.PASSWORD);
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
        String query = "SELECT * FROM person";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Tabellenkopf
            printTableHeader();

            // Tabelleninhalt
            while (rs.next()) {
                printTableRow(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("country"),
                        rs.getDate("birthday"),
                        rs.getInt("salary"),
                        rs.getInt("bonus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hilfsmethode zum Tabellenkopf
    private void printTableHeader() {
        String separator = "+-----+---------------+---------------+-------------------------+---------------+--------------+-----------------+----------------------+";
        System.out.println(separator);
        System.out.printf("| %-3s | %-13s | %-13s | %-23s | %-13s | %-12s | %-15s | %-20s |%n",
                "ID", "Vorname", "Nachname", "E-Mail", "Land", "Geburtstag", "Jahreseinkommen", "jährlicher Bonus");
        System.out.println(separator);
    }

    // Hilfsmethode zur Tabellenausgabe einer Zeile
    private void printTableRow(int id, String firstName, String lastName, String email, String country, Date birthday, int salary, int bonus) {
        System.out.printf("| %-3d | %-13s | %-13s | %-23s | %-13s | %-12s | %-15d | %-20d |%n",
                id, firstName, lastName, email, country, birthday, salary, bonus);
        System.out.println("+-----+---------------+---------------+-------------------------+---------------+--------------+-----------------+----------------------+");
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
            String query = "UPDATE person SET \"" + column + "\" = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, value);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateData(int id, String column, int value) {
        try {
            String query = "UPDATE person SET \"" + column + "\" = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, value);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(int id, Date value) {
        try {
            String query = "UPDATE person SET birthday = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, value);
            stmt.setInt(2, id);
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
            e.printStackTrace();
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

    public void selectById(int id) {
        String query = "SELECT * FROM person WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                // Tabellenkopf
                printTableHeader();

                // Eintrag ausgeben, wenn vorhanden
                if (rs.next()) {
                    printTableRow(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("country"),
                            rs.getDate("birthday"),
                            rs.getInt("salary"),
                            rs.getInt("bonus")
                    );
                } else {
                    System.out.println("Keine Einträge mit dieser ID gefunden.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getIDs() {
        ArrayList<Integer> ids = new ArrayList<>();
        String query = "SELECT id FROM person";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
