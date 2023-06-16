package org.lessons.java;

import java.sql.*;

public class Mysql {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/db-nations";
        String user = "root";
        String password = "root";


        // Definizione della query SQL


        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println(con.getCatalog());

            String sql = """
                    SELECT c.name AS country_name, c.country_id, r.name AS region_name, con.name AS continent_name
                    FROM countries c
                    JOIN regions r ON c.region_id = r.region_id
                    JOIN continents con ON r.continent_id = con.continent_id
                    ORDER BY c.name ASC""";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String CountryName = rs.getString("country_name");
                        int CountryId = rs.getInt("country_id");
                        String RegionName = rs.getString("region_name");
                        String ContinentName = rs.getString("continent_name");

                        System.out.println(CountryName + "-" + CountryId + "-" + RegionName + "-" + ContinentName);
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