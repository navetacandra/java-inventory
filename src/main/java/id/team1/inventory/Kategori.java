/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.team1.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author team1
 */
public class Kategori {

    public int idKategori;
    public String namaKategori;

    public static ResultSet get(Connection sqlCon) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "SELECT * FROM Kategori ORDER BY IdKategori ASC"
            );
            ResultSet res = stmt.executeQuery();
            return res;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to get kategori: " + e.toString()
            );
            return null;
        }
    }

    public static ResultSet find(Connection sqlCon, String namaKategori) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "SELECT * FROM Kategori WHERE NamaKategori LIKE ? ORDER BY IdKategori ASC"
            );
            stmt.setString(1, Koneksi.prepareLike(namaKategori));
            ResultSet res = stmt.executeQuery();
            return res;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to get kategori: " + e.toString()
            );
            return null;
        }
    }

    public static void create(Connection sqlCon, String namaKategori) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "INSERT INTO Kategori (NamaKategori) VALUES (?)"
            );
            stmt.setString(1, namaKategori);
            stmt.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to add kategori: " + e.toString()
            );
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {}
        }
    }

    public static void update(
        Connection sqlCon,
        int idKategori,
        String namaKategori
    ) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "UPDATE Kategori SET NamaKategori=? WHERE IdKategori=?"
            );
            stmt.setString(1, namaKategori);
            stmt.setInt(2, idKategori);
            stmt.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to update kategori: " + e.toString()
            );
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {}
        }
    }

    public static void delete(Connection sqlCon, int idKategori) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "DELETE FROM Kategori SET WHERE IdKategori=?"
            );
            stmt.setInt(1, idKategori);
            stmt.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to delete kategori: " + e.toString()
            );
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {}
        }
    }
}
