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
public class Transaksi {

    public int idTransaksi;
    public String namaTransaksi;

    public static ResultSet get(Connection sqlCon) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "SELECT * FROM Transaksi LEFT OUTER JOIN Barang USING (IdBarang) LEFT OUTER JOIN Kategori USING (IdKategori) ORDER BY Timestamp DESC"
            );
            ResultSet res = stmt.executeQuery();
            return res;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to get Transaksi: " + e.toString()
            );
            return null;
        }
    }

    public static ResultSet find(Connection sqlCon, int idTransaksi) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "SELECT * FROM Transaksi LEFT OUTER JOIN Barang USING (IdBarang) LEFT OUTER JOIN Kategori USING (IdKategori) WHERE IdTransaksi=? ORDER BY Timestamp DESC"
            );
            stmt.setInt(1, idTransaksi);
            ResultSet res = stmt.executeQuery();
            return res;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to get Transaksi: " + e.toString()
            );
            return null;
        }
    }

    public static void create(
        Connection sqlCon,
        int idBarang,
        String tipeTransaksi,
        int jumlahTransaksi,
        String pesan
    ) {
        PreparedStatement stmt = null;
        try {
            stmt = sqlCon.prepareStatement(
                "INSERT INTO Transaksi (IdBarang, TipeTransaksi, JumlahTransaksi, Pesan) VALUES (?, ?, ?, ?)"
            );
            stmt.setInt(1, idBarang);
            stmt.setString(2, tipeTransaksi);
            stmt.setInt(3, jumlahTransaksi);
            stmt.setString(4, pesan);
            stmt.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Failed to add Transaksi: " + e.toString()
            );
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {}
        }
    }
}
