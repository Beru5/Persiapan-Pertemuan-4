package dataset;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Dataset {    
       
    public static void main(String[] argo){
        String url = "https://drive.google.com/uc?export=download&id=14DWF2kG0hGD3hYJjAcsexOCGedVfrv3r";
        
        List<Produk> listProduk = new ArrayList<>();
        Set<String> negaraMana = new HashSet<>();
        Map<String, Integer> totalPenjualanProduk = new HashMap<>();
        Map<String, Double> penghasilanPerNegara = new HashMap<>();
        Map<String, Produk> produkSesuaiKode = new HashMap<>();

        try (BufferedReader br = new BufferedReader (new InputStreamReader(new URL(url).openStream()))){
            
            String line;
            boolean firstLine = true;
                        
            while((line = br.readLine())!= null){
                if (firstLine) 
                {
                    firstLine = false;
                    continue;
                }
                              
            String[] values = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
            
            String invoice = values[0].trim();
            String kodeStok = values[1].trim();
            String deskripsi = values[2].trim();
            int kuantitas = Integer.parseInt(values[3].trim());
            String tanggal = values[4].trim();
            double hargaSatuan = Double.parseDouble(values[5].trim());
            String idPelanggan = values[6].trim();
            String negara = values[7].trim();

            Produk produk = new Produk(invoice, kodeStok, deskripsi, tanggal, hargaSatuan, kuantitas, idPelanggan, negara);
            listProduk.add(produk);
            negaraMana.add(negara);
                
            totalPenjualanProduk.put(kodeStok, totalPenjualanProduk.getOrDefault(kodeStok, 0) + kuantitas);
            penghasilanPerNegara.put(negara, penghasilanPerNegara.getOrDefault(negara, 0.0) + produk.getTotalPrice());
            produkSesuaiKode.put(kodeStok, produk);
            }
            
            System.out.println("Dataset berhasil dibaca! Total Produk: " + listProduk.size());
            
            System.out.println("\n=== Contoh 5 Produk ===");
            listProduk.stream().limit(5).forEach(System.out::println);

            System.out.println("\n=== Daftar Negara ===");
            System.out.println(negaraMana);

            System.out.println("\n=== Total Produk Terjual berdasarkan Kode Stok ===");
            //batasi hingga 5 saja, karena 541909 data produk bakal kewalahan untuk scroll ke bagian sebelumnya
            totalPenjualanProduk.entrySet().stream().limit(5).forEach(e -> System.out.println("Kode Stok: " + e.getKey() + " -> " + e.getValue() + " pcs"));            
            //jika ingin tidak dibatasi, gunakan kode berikut alih-alih kode diatas
            //totalPenjualanProduk.forEach((k, v) -> System.out.println("Kode Stok: " + k + " -> " + v + " pcs"));
            
            System.out.println("\n=== Total Pendapatan per Negara ===");
            penghasilanPerNegara.forEach((negara, duit) -> System.out.println("Negara: " + negara + " -> $" + duit));

            System.out.println("\n=== Cari Produk berdasarkan Kode Stok '85123A' /ada/ dan 'DJAlokFF' /tidak ada/ ===");
            if (produkSesuaiKode.containsKey("85123A")) {
                System.out.println(produkSesuaiKode.get("85123A"));
            } else {
                System.out.println("Produk tidak ditemukan.");
            }
            
            if (produkSesuaiKode.containsKey("DJAlokFF")) {
                System.out.println(produkSesuaiKode.get("DJAlokFF"));
            } else {
                System.out.println("Produk tidak ditemukan.");
            }
            
        }
        catch (IOException e){
                    e.printStackTrace();
                    }
        
    }              
}