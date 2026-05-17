/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.team1.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author team1
 */
public class Koneksi {

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Inventory",
                "root",
                "root"
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to open database connection: " + e.toString()
            );
            return null;
        }
    }
}
