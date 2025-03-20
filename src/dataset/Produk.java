package dataset; 

public class Produk {  
    String kodeStok;
    String deskripsi;
    double hargaSatuan;
    int kuantitas;
    String negara;

    public Produk(String kodeStok, String deskripsi, double hargaSatuan, int kuantitas, String negara) {
        this.kodeStok = kodeStok;
        this.deskripsi = deskripsi;
        this.hargaSatuan = hargaSatuan;
        this.kuantitas = kuantitas;
        this.negara = negara;
    }

    public double getTotalPrice() {
        return hargaSatuan * kuantitas;
    }

    @Override
    public String toString() {
        return kodeStok + " - " + deskripsi + " - " + kuantitas + " pcs - $" + hargaSatuan + " - " + negara;
    }
}

