DROP TABLE IF EXISTS Transaksi;
DROP TABLE IF EXISTS Barang;
DROP TABLE IF EXISTS Kategori;

SET GLOBAL time_zone = '+07:00';

CREATE TABLE `Kategori` (
  `IdKategori` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `NamaKategori` varchar(50) NOT NULL UNIQUE
);

CREATE TABLE `Barang` (
  `IdBarang` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `IdKategori` int(11) NOT NULL,
  `NamaBarang` varchar(50) NOT NULL,
  `JumlahBarang` int(11) NOT NULL,
  FOREIGN KEY (`IdKategori`) REFERENCES `Kategori` (`IdKategori`) ON DELETE CASCADE
);

CREATE TABLE `Transaksi` (
  `IdTransaksi` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `IdBarang` int(11) NOT NULL,
  `TipeTransaksi` enum('Masuk','Keluar') NOT NULL,
  `JumlahTransaksi` int(11) NOT NULL,
  `Pesan` varchar(50) NOT NULL,
  `Timestamp` timestamp NOT NULL DEFAULT current_timestamp,
  FOREIGN KEY (`IdBarang`) REFERENCES `Barang` (`IdBarang`) ON DELETE CASCADE
);


LOCK TABLES `Transaksi` WRITE;
UNLOCK TABLES;

DELIMITER ;;
CREATE TRIGGER TransaksiMasuk
AFTER INSERT ON Transaksi
FOR EACH ROW
BEGIN
    IF NEW.TipeTransaksi = "Masuk" THEN
        UPDATE Barang
            SET JumlahBarang = (SELECT JumlahBarang FROM Barang WHERE IdBarang=NEW.IdBarang) + NEW.JumlahTransaksi
            WHERE IdBarang=NEW.IdBarang;
    END IF;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE TRIGGER TransaksiKeluar
AFTER INSERT ON Transaksi
FOR EACH ROW
BEGIN
    IF NEW.TipeTransaksi = "Keluar" THEN
        UPDATE Barang
            SET JumlahBarang = (SELECT JumlahBarang FROM Barang WHERE IdBarang=NEW.IdBarang) - NEW.JumlahTransaksi
            WHERE IdBarang=NEW.IdBarang;
    END IF;
END ;;
DELIMITER ;
