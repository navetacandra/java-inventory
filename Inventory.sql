DROP TABLE IF EXISTS Transaksi;
DROP TABLE IF EXISTS Barang;
DROP TABLE IF EXISTS Kategori;

CREATE TABLE `Kategori` (
  `IdKategori` int(11) NOT NULL AUTO_INCREMENT,
  `NamaKategori` varchar(100) NOT NULL,
  PRIMARY KEY (`IdKategori`),
  UNIQUE KEY `NamaKategori` (`NamaKategori`)
);

CREATE TABLE `Barang` (
  `IdBarang` int(11) NOT NULL AUTO_INCREMENT,
  `IdKategori` int(11) NOT NULL,
  `NamaBarang` varchar(100) NOT NULL,
  `JumlahBarang` int(11) NOT NULL,
  PRIMARY KEY (`IdBarang`),
  KEY `IdKategori` (`IdKategori`),
  FOREIGN KEY (`IdKategori`) REFERENCES `Kategori` (`IdKategori`) ON DELETE CASCADE
);

CREATE TABLE `Transaksi` (
  `IdBarang` int(11) NOT NULL,
  `TipeTransaksi` enum('Masuk','Keluar') NOT NULL,
  `JumlahTransaksi` int(11) NOT NULL,
  KEY `IdBarang` (`IdBarang`),
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
