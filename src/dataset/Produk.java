package dataset;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Produk {  
    String invoiceNo;
    String kodeStok;
    String deskripsi;   
    LocalDateTime tanggal;
    double hargaSatuan;
    int kuantitas;
    String idPelanggan;
    String negara;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Produk(String invoiceNo, String kodeStok, String deskripsi, String tanggal, double hargaSatuan, int kuantitas, String idPelanggan, String negara) {
        this.invoiceNo = invoiceNo;
        this.kodeStok = kodeStok;
        this.deskripsi = deskripsi;
        this.tanggal = LocalDateTime.parse(tanggal, FORMATTER);
        this.hargaSatuan = hargaSatuan;
        this.kuantitas = kuantitas;
        this.idPelanggan = idPelanggan;
        this.negara = negara;
    }
    

    public double getTotalPrice() {
        return hargaSatuan * kuantitas;
    }

    @Override
    public String toString() {
        return invoiceNo + " - " + kodeStok + " - " + deskripsi + " - " + tanggal + " - " + kuantitas + " pcs - $" + hargaSatuan + " - " + idPelanggan + " - "+ negara;
    }
}

