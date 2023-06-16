package org.lessons.java;

import java.sql.*;
import java.util.Scanner;

public class Mysql {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/db-nations";
        String user = "root";
        String password = "root";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println(con.getCatalog());

            Scanner scan = new Scanner(System.in);
            System.out.print("Ricerca: ");
            String search = scan.nextLine();

            String sql = """
                    SELECT c.name AS country_name, c.country_id, r.name AS region_name, con.name AS continent_name
                    FROM countries c
                    JOIN regions r ON c.region_id = r.region_id
                    JOIN continents con ON r.continent_id = con.continent_id
                    WHERE c.name LIKE ?
                    ORDER BY c.name ASC""";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, "%" + search + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String countryName = rs.getString("country_name");
                        int countryId = rs.getInt("country_id");
                        String regionName = rs.getString("region_name");
                        String continentName = rs.getString("continent_name");

                        System.out.println(countryName + "-" + countryId + "-" + regionName + "-" + continentName);
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect");
            e.printStackTrace();
        }
    }
}