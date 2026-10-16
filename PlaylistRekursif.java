/*
 * ==========================================================
 *  TUGAS KELOMPOK 3 - WEEK 8
 *  Data Structures and Algorithm Analysis
 *  Topik : Algorithmic Analysis, Mathematical Induction, and Recursive Functions
 * ----------------------------------------------------------
 *  Kelompok : Group-1
 *  Kelas    : LYCA - LEC
 *  Anggota  :
 *    1. AURA JATRA RATU SEMENDAWAI - 2902811884
 *    2. GAGE                       - 2902807395
 *    3. MUHAMMAD SETIADI PRATAMA   - 2902807962
 *    4. AFTA PASMA LOHDRI          - 2902827150
 *    5. MUH FAISAL BIMANTARA       - 2902827232
 * ==========================================================
 */


import java.io.OutputStream;
import java.util.Locale;
import java.io.PrintStream;

public class PlaylistRekursif {

    // Atribut playlist (array objek Lagu)
    private Lagu[] playlist;

    public PlaylistRekursif(Lagu[] playlist) {
        this.playlist = playlist;
    }

    public Lagu[] getPlaylist() {
        return playlist;
    }

    // ==========================================================
    // FUNGSI REKURSIF
    // ==========================================================

    /**
     * totalDurasi
     * Tujuan          : Menghitung total durasi n lagu pertama di playlist.
     * Base case       : n <= 0  -> tidak ada lagu, kembalikan 0.
     * Recursive case  : durasi lagu ke-(n-1) + totalDurasi(list, n - 1).
     * Kompleksitas    : T(n) = T(n-1) + c  ->  O(n) (linear).
     *                   Ruang: O(n) karena kedalaman call stack = n.
     */
    static double totalDurasi(Lagu[] list, int n) {
        if (n <= 0) {                       // base case
            return 0;
        }
        return list[n - 1].getDurasi() + totalDurasi(list, n - 1); // recursive case
    }

    /**
     * tampilkanMundur
     * Tujuan          : Menampilkan lagu dari akhir ke awal (reverse traversal).
     *                   Dipanggil pertama kali dengan index = list.length - 1.
     * Base case       : index < 0  -> semua lagu sudah ditampilkan, berhenti.
     * Recursive case  : cetak lagu pada index dengan format
     *                   "No. Judul - Artis (durasi)", lalu panggil tampilkanMundur(list, index - 1).
     * Kompleksitas    : T(n) = T(n-1) + c  ->  O(n) (linear).
     *                   Ruang: O(n) untuk call stack.
     */
    static void tampilkanMundur(Lagu[] list, int index) {
        if (index < 0) {                    // base case
            return;
        }
        System.out.println((index + 1) + ". " + list[index].getJudul()
                + " - " + list[index].getArtis()
                + " (" + String.format(Locale.US, "%.2f", list[index].getDurasi()) + ")");
        tampilkanMundur(list, index - 1);   // recursive case
    }

    /**
     * cariDurasiTerpanjang
     * Tujuan          : Mencari durasi terpanjang mulai dari posisi index sampai akhir.
     *                   Dipanggil pertama kali dengan index = 0.
     * Base case       : index == list.length - 1 -> hanya tersisa 1 lagu,
     *                   durasinya pasti yang terpanjang.
     * Recursive case  : bandingkan durasi lagu[index] dengan
     *                   cariDurasiTerpanjang(list, index + 1), ambil yang lebih besar.
     * Kompleksitas    : T(n) = T(n-1) + c  ->  O(n) (linear).
     *                   Ruang: O(n) untuk call stack.
     */
    static double cariDurasiTerpanjang(Lagu[] list, int index) {
        if (index == list.length - 1) {     // base case
            return list[index].getDurasi();
        }
        double maxSisa = cariDurasiTerpanjang(list, index + 1); // recursive case
        return Math.max(list[index].getDurasi(), maxSisa);
    }

    /**
     * cariLaguTerpanjang (fungsi bantu)
     * Tujuan          : Mencari OBJEK Lagu dengan durasi terpanjang, supaya judul
     *                   dan artis lagu terpanjang bisa ditampilkan.
     *                   Dipanggil pertama kali dengan index = 0.
     * Base case       : index == list.length - 1 -> kembalikan lagu terakhir.
     * Recursive case  : bandingkan lagu[index] dengan lagu terpanjang dari sisa array,
     *                   kembalikan yang durasinya lebih besar.
     * Kompleksitas    : T(n) = T(n-1) + c  ->  O(n).
     */
    static Lagu cariLaguTerpanjang(Lagu[] list, int index) {
        if (index == list.length - 1) {     // base case
            return list[index];
        }
        Lagu terpanjangSisa = cariLaguTerpanjang(list, index + 1); // recursive case
        return (list[index].getDurasi() >= terpanjangSisa.getDurasi()) ? list[index] : terpanjangSisa;
    }

    // ==========================================================
    // DATA & UTILITAS
    // ==========================================================

    // Bank lagu untuk membentuk playlist dengan berbagai ukuran
    private static final Lagu[] BANK_LAGU = {
        new Lagu("Perfect", "Ed Sheeran", 4.23),
        new Lagu("Shivers", "Ed Sheeran", 3.50),
        new Lagu("Fix You", "Coldplay", 3.83),
        new Lagu("Yellow", "Coldplay", 4.27),
        new Lagu("Viva La Vida", "Coldplay", 4.52),
        new Lagu("Photograph", "Ed Sheeran", 4.18),
        new Lagu("The Scientist", "Coldplay", 5.09),
        new Lagu("Bad Habits", "Ed Sheeran", 3.51),
        new Lagu("Paradise", "Coldplay", 4.38),
        new Lagu("Thinking Out Loud", "Ed Sheeran", 4.41)
    };

    /** Membuat playlist berisi n lagu (diambil berulang dari BANK_LAGU). */
    static Lagu[] buatPlaylist(int n) {
        Lagu[] hasil = new Lagu[n];
        for (int i = 0; i < n; i++) {
            hasil[i] = BANK_LAGU[i % BANK_LAGU.length];
        }
        return hasil;
    }

    /** Konversi nanodetik ke milidetik. */
    static double keMs(long nano) {
        return nano / 1_000_000.0;
    }

    // PrintStream kosong agar output tampilkanMundur tidak membanjiri layar saat eksperimen
    private static final PrintStream NULL_OUT = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) { }
    });

    /**
     * Mengukur rata-rata waktu eksekusi (ms) sebuah fungsi.
     * Fungsi dijalankan berulang kali (repeat) lalu dirata-rata, karena untuk
     * n kecil satu kali eksekusi hanya beberapa mikrodetik (di bawah 1 ms).
     * Sebelum diukur dilakukan warm-up agar JIT compiler Java sudah aktif.
     */
    static double ukur(int fungsi, Lagu[] list, int repeat) {
        // warm-up (tidak diukur)
        for (int i = 0; i < 2000; i++) {
            jalankan(fungsi, list);
        }
        // 7 kali percobaan, diambil nilai tengah (median) agar tahan terhadap
        // gangguan sesaat seperti garbage collection
        double[] percobaan = new double[7];
        for (int p = 0; p < percobaan.length; p++) {
            long mulai = System.nanoTime();
            for (int i = 0; i < repeat; i++) {
                jalankan(fungsi, list);
            }
            long selesai = System.nanoTime();
            percobaan[p] = keMs(selesai - mulai) / repeat;
        }
        java.util.Arrays.sort(percobaan);
        return percobaan[percobaan.length / 2];
    }

    static volatile double sink; // mencegah JIT menghapus hasil perhitungan

    static void jalankan(int fungsi, Lagu[] list) {
        switch (fungsi) {
            case 0: sink = totalDurasi(list, list.length); break;
            case 1: tampilkanMundur(list, list.length - 1); break;
            default: sink = cariDurasiTerpanjang(list, 0); break;
        }
    }

    // ==========================================================
    // MAIN
    // ==========================================================
    public static void main(String[] args) {

        // ---------- BAGIAN 1: Analisis playlist 5 lagu ----------
        PlaylistRekursif app = new PlaylistRekursif(buatPlaylist(5));
        Lagu[] list = app.getPlaylist();

        System.out.println("=== ANALISIS REKURSIF PLAYLIST ===");
        System.out.println("Jumlah lagu      : " + list.length);

        long t0 = System.nanoTime();
        double total = totalDurasi(list, list.length);
        long t1 = System.nanoTime();
        System.out.printf(Locale.US, "Total durasi     : %.2f menit%n", total);

        long t2 = System.nanoTime();
        double terpanjang = cariDurasiTerpanjang(list, 0);
        long t3 = System.nanoTime();
        Lagu laguMax = cariLaguTerpanjang(list, 0);
        System.out.printf(Locale.US, "Lagu terpanjang  : \"%s\" - %s (%.2f menit)%n",
                laguMax.getJudul(), laguMax.getArtis(), terpanjang);

        System.out.println();
        System.out.println("Daftar lagu (ditampilkan terbalik):");
        long t4 = System.nanoTime();
        tampilkanMundur(list, list.length - 1);
        long t5 = System.nanoTime();

        System.out.println();
        System.out.printf(Locale.US, "Execution Time (totalDurasi)          : %.4f ms%n", keMs(t1 - t0));
        System.out.printf(Locale.US, "Execution Time (tampilkanMundur)      : %.4f ms%n", keMs(t5 - t4));
        System.out.printf(Locale.US, "Execution Time (cariDurasiTerpanjang) : %.4f ms%n", keMs(t3 - t2));
        System.out.println("(satu kali eksekusi, diukur dengan System.nanoTime())");

        // ---------- BAGIAN 2: Eksperimen waktu eksekusi ----------
        System.out.println();
        System.out.println("=== EKSPERIMEN WAKTU EKSEKUSI (rata-rata per panggilan) ===");
        System.out.println("Setiap fungsi diulang ribuan kali setelah warm-up, dirata-rata, lalu diambil median dari 7 percobaan.");
        System.out.println("Output tampilkanMundur dialihkan selama eksperimen agar layar tidak penuh.");
        System.out.println();

        int[] ukuran = {2, 3, 4, 5, 6, 8, 10, 100, 500, 1000};

        // warm-up global: jalankan semua ukuran satu kali dulu (hasil dibuang)
        PrintStream asli = System.out;
        System.setOut(NULL_OUT);
        for (int n : ukuran) {
            Lagu[] data = buatPlaylist(n);
            for (int f = 0; f < 3; f++) {
                ukur(f, data, 1000);
            }
        }
        System.setOut(asli);

        System.out.println("+-----------+-----------------+-------------------+------------------------+");
        System.out.println("| Jumlah(n) | totalDurasi()   | tampilkanMundur() | cariDurasiTerpanjang() |");
        System.out.println("+-----------+-----------------+-------------------+------------------------+");
        for (int n : ukuran) {
            Lagu[] data = buatPlaylist(n);
            int repeat = Math.max(1_000, 200_000 / n); // n kecil diulang lebih banyak
            double[] hasil = new double[3];
            System.setOut(NULL_OUT);
            for (int f = 0; f < 3; f++) {
                hasil[f] = ukur(f, data, repeat);
            }
            System.setOut(asli);
            System.out.printf(Locale.US, "| %-9d | %12.6f ms | %14.6f ms | %19.6f ms |%n",
                    n, hasil[0], hasil[1], hasil[2]);
        }
        System.out.println("+-----------+-----------------+-------------------+------------------------+");
    }
}