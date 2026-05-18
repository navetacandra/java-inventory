/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package id.team1.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author team1
 */
public class Barang {

    public int idBarang;
    public String namaBarang;

    public static ResultSet get(Connection sqlCon) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "SELECT * FROM Barang LEFT OUTER JOIN Kategori USING (IdKategori) ORDER BY IdBarang ASC"
            );
            ResultSet res = stmt.executeQuery();
            return res;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to get Barang: " + e.toString()
            );
            return null;
        }
    }

    public static ResultSet find(Connection sqlCon, String query) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "SELECT * FROM Barang LEFT OUTER JOIN Kategori USING (IdKategori) WHERE (NamaBarang LIKE ? OR ?='') OR (NamaKategori LIKE ? OR ?='') ORDER BY IdBarang ASC"
            );
            stmt.setString(1, Koneksi.prepareLike(query));
            stmt.setString(2, query);
            stmt.setString(3, Koneksi.prepareLike(query));
            stmt.setString(4, query);
            ResultSet res = stmt.executeQuery();
            return res;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to get Barang: " + e.toString()
            );
            return null;
        }
    }

    public static void create(
        Connection sqlCon,
        int idKategori,
        String namaBarang,
        int jumlahBarang
    ) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "INSERT INTO Barang (IdKategori, NamaBarang, JumlahBarang) VALUES (?, ?, 0)",
                Statement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, idKategori);
            stmt.setString(2, namaBarang);

            stmt.execute();
            ResultSet res = stmt.getGeneratedKeys();
            if (res.next()) {
                if (jumlahBarang > 0) {
                    Transaksi.create(
                        sqlCon,
                        res.getInt(1),
                        "Masuk",
                        jumlahBarang,
                        "Barang baru ditambahkan."
                    );
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to add Barang: " + e.toString()
            );
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {}
        }
    }

    public static void update(
        Connection sqlCon,
        int idBarang,
        int idKategori,
        String namaBarang
    ) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "UPDATE Barang SET IdKategori=?, NamaBarang=? WHERE IdBarang=?"
            );
            stmt.setInt(1, idKategori);
            stmt.setString(2, namaBarang);
            stmt.setInt(3, idBarang);
            stmt.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to update Barang: " + e.toString()
            );
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {}
        }
    }

    public static void delete(Connection sqlCon, int idBarang) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "DELETE FROM Barang WHERE IdBarang=?"
            );
            stmt.setInt(1, idBarang);
            stmt.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to delete Barang: " + e.toString()
            );
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {}
        }
    }
}
