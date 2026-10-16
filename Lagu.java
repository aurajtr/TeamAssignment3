import java.util.Locale;

/* =====================================================================
 * CLASS LAGU
 * ===================================================================== */
public class Lagu {
    // Atribut private -> inti dari enkapsulasi (information hiding)
    private String judul;
    private String artis;
    private double durasi; // satuan: menit

    /*
     * Constructor.
     * Logika: menerima nilai awal untuk setiap atribut saat objek dibuat.
     * Pengisian durasi sengaja dilewatkan ke setDurasi() agar aturan
     * validasi hanya ditulis satu kali (tidak duplikat).
     */
    public Lagu(String judul, String artis, double durasi) {
        this.judul = judul;
        this.artis = artis;
        this.setDurasi(durasi);
    }

    // ---------------------- GETTER ----------------------
    // Menyediakan akses BACA yang terkontrol ke atribut private.
    public String getJudul() {
        return judul;
    }

    public String getArtis() {
        return artis;
    }

    public double getDurasi() {
        return durasi;
    }

    // ---------------------- SETTER ----------------------
    // Menyediakan akses UBAH yang terkontrol, lengkap dengan validasi.
    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setArtis(String artis) {
        this.artis = artis;
    }

    /*
     * Setter durasi dengan validasi.
     * Logika: durasi sebuah lagu tidak mungkin bernilai negatif, sehingga
     * nilai yang tidak masuk akal ditolak dan diganti 0. Inilah keuntungan
     * enkapsulasi: data objek tidak bisa dirusak dari luar class.
     */
    public void setDurasi(double durasi) {
        if (durasi < 0) {
            System.out.println("[!] Durasi tidak valid untuk lagu \"" + judul + "\". Nilai diset ke 0.");
            this.durasi = 0;
        } else {
            this.durasi = durasi;
        }
    }

    /*
     * Method tampilkanInfo().
     * Logika: mencetak seluruh informasi lagu dalam format multi-baris
     * yang mudah dibaca. Dipakai saat pengguna meminta DETAIL satu lagu.
     */
    public void tampilkanInfo() {
        System.out.println("  Judul  : " + judul);
        System.out.println("  Artis  : " + artis);
        System.out.println("  Durasi : " + String.format(Locale.US, "%.2f", durasi) + " menit");
    }

    /*
     * Method tampilkanRingkas().
     * Logika: mencetak informasi lagu dalam SATU baris bernomor, dipakai
     * ketika menampilkan seluruh isi playlist agar output tetap rapi.
     */
    public void tampilkanRingkas(int nomor) {
        System.out.println(String.format(Locale.US, "  %-3d %-22s %-15s %5.2f menit",
                nomor, judul, artis, durasi));
    }
}
