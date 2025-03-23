package dataset;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Dataset {    
       
    public static void main(String[] args){
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
                
                String invoiceNo = values[0].trim();
                String kodeStok = values[1].trim();
                String deskripsi = values[2].trim();
                int kuantitas = Integer.parseInt(values[3].trim());
                String tanggal = values[4].trim();
                double hargaSatuan = Double.parseDouble(values[5].trim());
                String idPelanggan = values[6].trim();
                String negara = values[7].trim();

                Produk produk = new Produk(invoiceNo, kodeStok, deskripsi, tanggal, hargaSatuan, kuantitas, idPelanggan, negara);
                listProduk.add(produk);
                negaraMana.add(negara);
                    
                totalPenjualanProduk.put(kodeStok, totalPenjualanProduk.getOrDefault(kodeStok, 0) + kuantitas);
                penghasilanPerNegara.put(negara, penghasilanPerNegara.getOrDefault(negara, 0.0) + produk.getTotalPrice());
                produkSesuaiKode.put(kodeStok, produk);
            }

            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Dataset berhasil dibaca! Total Produk: " + listProduk.size());
            
            System.out.println("\n=== Contoh 5 Produk ===");
            listProduk.stream().limit(5).forEach(System.out::println);

            System.out.println("\n=== Daftar Negara ===");
            System.out.println(negaraMana);

            System.out.println("\n=== Total Produk Terjual berdasarkan Kode Stok ===");
            
            //Dibatasi hingga 5 saja, produk disortir berdasarkan kode stok.
            totalPenjualanProduk.entrySet().stream().limit(5).forEach(e -> System.out.println("Kode Stok: " + e.getKey() + " -> " + e.getValue() + " pcs"));

            //Jika ingin tidak dibatasi, gunakan kode berikut alih-alih kode diatas
            //totalPenjualanProduk.forEach((k, v) -> System.out.println("Kode Stok: " + k + " -> " + v + " pcs"));

            //Jika ingin di sortir berdasarkan jumlah penjualan produk (dibatasi hingga 5)
            //totalPenjualanProduk.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).limit(5).forEach(e -> System.out.println("Kode Stok: " + e.getKey() + " -> " + e.getValue() + " pcs"));            
                        
            //Jika ingin di sortir berdasarkan jumlah penjualan produk dan tidak ingin dibatasi jumlah output datanya
            //totalPenjualanProduk.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).forEach(e -> System.out.println("Kode Stok: " + e.getKey() + " -> " + e.getValue() + " pcs"));


            System.out.println("\n=== Total Penghasilan Penjualan per Negara ===");
            penghasilanPerNegara.forEach((negara, duit) -> System.out.println("Negara: " + negara + " -> $" + duit));

            while (true) {
                System.out.print("\nCari kode stok produk: ");
                String kodeStok = scanner.nextLine();
                if (produkSesuaiKode.containsKey(kodeStok)) {
                    System.out.println("\nProduk yang dicari: " + produkSesuaiKode.get(kodeStok));
                } else {
                    System.out.println("\nProduk yang ingin dicari tidak berhasil ditemukan.");
                }
                System.out.print("\nKetik y untuk mencari lagi: ");
                String ulangi = scanner.nextLine().trim().toLowerCase();
                if (!ulangi.equals("y")) {
                    break;
                }
        }
            scanner.close();
        }
        catch (IOException e){
                    e.printStackTrace();
                    }
        
    }              
}