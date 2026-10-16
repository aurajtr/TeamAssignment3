/*
 * ==========================================================
 *  TUGAS KELOMPOK 3 - WEEK 8
 *  Algorithmic Analysis, Mathematical Induction, and Recursive Functions
 *  LO2: Analyze algorithm complexity
 * ----------------------------------------------------------
 *  Kelompok : Group-1
 *  Kelas    : LYCA - LEC
 *  Anggota  :
 *    1. AURA JATRA RATU SEMENDAWAI - 2902811884 - Computer Science
 *    2. GAGE - 2902807395 - Computer Science
 *    3. MUHAMMAD SETIADI PRATAMA - 2902807962 - Computer Science
 *    4. AFTA PASMA LOHDRI - 2902827150 - Computer Science
 *    5. MUH FAISAL BIMANTARA - 2902827232 - Computer Science
 * ==========================================================
 */

// Class Lagu didefinisikan pada file Lagu.java di folder yang sama.
import java.io.OutputStream;
import java.io.PrintStream;

public class PlaylistRekursif {

    // Atribut playlist
    static Lagu[] playlist;

    // ------------------------------------------------------
    // 1. totalDurasi
    // Tujuan     : Menghitung total durasi seluruh lagu.
    // Base case  : n <= 0 (tidak ada lagu) -> return 0.
    // Recursive  : durasi lagu ke-(n-1) + totalDurasi(list, n-1).
    // Waktu      : T(n) = T(n-1) + c  =>  O(n)
    // Ruang      : O(n) (call stack sedalam n)
    // ------------------------------------------------------
    static double totalDurasi(Lagu[] list, int n) {
        if (n <= 0) {
            return 0;
        }
        return list[n - 1].getDurasi() + totalDurasi(list, n - 1);
    }

    // ------------------------------------------------------
    // 2. tampilkanMundur
    // Tujuan     : Menampilkan lagu dari urutan terakhir ke pertama.
    // Base case  : index < 0 -> berhenti (tidak melakukan apa-apa).
    // Recursive  : cetak lagu[index], lalu tampilkanMundur(list, index-1).
    //              Pemanggilan awal: tampilkanMundur(list, list.length - 1).
    // Waktu      : T(n) = T(n-1) + c  =>  O(n)
    // Ruang      : O(n) (call stack sedalam n)
    // ------------------------------------------------------
    static void tampilkanMundur(Lagu[] list, int index) {
        if (index < 0) {
            return;
        }
        System.out.println((index + 1) + ". " + list[index].getJudul()
                + " - " + list[index].getArtis()
                + " (" + String.format("%.2f", list[index].getDurasi()) + ")");
        tampilkanMundur(list, index - 1);
    }

    // ------------------------------------------------------
    // 3. cariDurasiTerpanjang
    // Tujuan     : Mencari durasi terpanjang di antara semua lagu.
    // Base case  : index == list.length - 1 (elemen terakhir)
    //              -> return durasi elemen tersebut.
    // Recursive  : max(durasi lagu[index], cariDurasiTerpanjang(list, index+1)).
    //              Pemanggilan awal: cariDurasiTerpanjang(list, 0).
    // Waktu      : T(n) = T(n-1) + c  =>  O(n)
    // Ruang      : O(n) (call stack sedalam n)
    // ------------------------------------------------------
    static double cariDurasiTerpanjang(Lagu[] list, int index) {
        if (index == list.length - 1) {
            return list[index].getDurasi();
        }
        double terpanjangSisa = cariDurasiTerpanjang(list, index + 1);
        return Math.max(list[index].getDurasi(), terpanjangSisa);
    }

    // ------------------------------------------------------
    // Fungsi bantu: mencari objek Lagu dengan durasi terpanjang
    // (supaya judul & artis bisa ditampilkan). Struktur rekursinya
    // sama dengan cariDurasiTerpanjang -> O(n).
    // Base case: index == list.length - 1.
    // ------------------------------------------------------
    static Lagu cariLaguTerpanjang(Lagu[] list, int index) {
        if (index == list.length - 1) {
            return list[index];
        }
        Lagu terpanjangSisa = cariLaguTerpanjang(list, index + 1);
        return (list[index].getDurasi() >= terpanjangSisa.getDurasi()) ? list[index] : terpanjangSisa;
    }

    // ------------------------------------------------------
    // Data contoh
    // ------------------------------------------------------
    static Lagu[] buatPlaylistContoh() {
        return new Lagu[] {
            new Lagu("Perfect",       "Ed Sheeran", 4.23),
            new Lagu("Shivers",       "Ed Sheeran", 3.50),
            new Lagu("Fix You",       "Coldplay",   3.83),
            new Lagu("Yellow",        "Coldplay",   4.27),
            new Lagu("Viva La Vida",  "Coldplay",   4.52)
        };
    }

    // Membuat playlist berukuran n untuk eksperimen
    static Lagu[] buatPlaylist(int n) {
        Lagu[] data = new Lagu[n];
        for (int i = 0; i < n; i++) {
            data[i] = new Lagu("Lagu " + (i + 1), "Artis " + (i % 5 + 1),
                    3.0 + ((i * 37) % 25) / 10.0);
        }
        return data;
    }

    // ------------------------------------------------------
    // Pengukuran waktu (System.nanoTime), hasil dalam milidetik
    // ------------------------------------------------------
    static double ms(long nanoAwal, long nanoAkhir) {
        return (nanoAkhir - nanoAwal) / 1_000_000.0;
    }

    // ------------------------------------------------------
    // Eksperimen: rata-rata waktu dari beberapa kali pengulangan
    // agar hasil lebih stabil (satu kali panggilan n kecil
    // terlalu cepat untuk diukur dalam ms).
    // ------------------------------------------------------
    static void eksperimen(int[] ukuran, int pengulangan) {
        PrintStream asli = System.out;
        PrintStream kosong = new PrintStream(new OutputStream() {
            public void write(int b) { }
        });

        System.out.println("\n=== EKSPERIMEN WAKTU EKSEKUSI (rata-rata " + pengulangan + " kali, ms) ===");
        System.out.printf("%-10s %-16s %-20s %-24s%n",
                "n", "totalDurasi()", "tampilkanMundur()", "cariDurasiTerpanjang()");

        for (int n : ukuran) {
            Lagu[] data = buatPlaylist(n);

            // Pemanasan JIT
            for (int i = 0; i < 200; i++) {
                totalDurasi(data, n);
                cariDurasiTerpanjang(data, 0);
            }

            long t1 = 0, t2 = 0, t3 = 0;
            double sink = 0;
            for (int i = 0; i < pengulangan; i++) {
                long a = System.nanoTime();
                sink += totalDurasi(data, n);
                long b = System.nanoTime();
                t1 += b - a;

                System.setOut(kosong); // sembunyikan output cetak agar yang terukur hanya rekursinya
                a = System.nanoTime();
                tampilkanMundur(data, n - 1);
                b = System.nanoTime();
                System.setOut(asli);
                t2 += b - a;

                a = System.nanoTime();
                sink += cariDurasiTerpanjang(data, 0);
                b = System.nanoTime();
                t3 += b - a;
            }
            System.out.printf("%-10d %-16s %-20s %-24s%n", n,
                    String.format("%.4f ms", t1 / 1e6 / pengulangan),
                    String.format("%.4f ms", t2 / 1e6 / pengulangan),
                    String.format("%.4f ms", t3 / 1e6 / pengulangan));
            if (sink < 0) System.out.println(sink); // cegah optimasi membuang perhitungan
        }
    }

    // ------------------------------------------------------
    // main
    // ------------------------------------------------------
    public static void main(String[] args) {
        playlist = buatPlaylistContoh();
        int n = playlist.length;

        System.out.println("=== ANALISIS REKURSIF PLAYLIST ===");
        System.out.println("Jumlah lagu : " + n);

        // 1. Total durasi
        long a = System.nanoTime();
        double total = totalDurasi(playlist, n);
        double waktuTotal = ms(a, System.nanoTime());
        System.out.printf("Total durasi : %.2f menit%n", total);

        // 3. Durasi terpanjang
        a = System.nanoTime();
        double terpanjang = cariDurasiTerpanjang(playlist, 0);
        double waktuCari = ms(a, System.nanoTime());
        Lagu lagu = cariLaguTerpanjang(playlist, 0);
        System.out.printf("Lagu terpanjang : \"%s\" - %s (%.2f menit)%n",
                lagu.getJudul(), lagu.getArtis(), terpanjang);

        // 2. Tampilkan terbalik
        System.out.println("\nDaftar lagu (ditampilkan terbalik):");
        a = System.nanoTime();
        tampilkanMundur(playlist, n - 1);
        double waktuMundur = ms(a, System.nanoTime());

        System.out.printf("%nExecution Time (totalDurasi)          : %.4f ms%n", waktuTotal);
        System.out.printf("Execution Time (tampilkanMundur)       : %.4f ms%n", waktuMundur);
        System.out.printf("Execution Time (cariDurasiTerpanjang)  : %.4f ms%n", waktuCari);

        // Eksperimen n = 3, 5, 10 (sesuai soal) dan n lebih besar untuk melihat growth rate
        eksperimen(new int[] {3, 5, 10, 100, 500, 1000, 2000}, 2000);
        // Catatan: n dibatasi 2000 karena rekursi sedalam n memakai call stack (ruang O(n));
        // n yang terlalu besar menyebabkan StackOverflowError.
    }
}
