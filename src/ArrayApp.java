class OrderedArray {
    private Mahasiswa[] mhs;
    private int nElemen;

    public OrderedArray(int max) {
        mhs = new Mahasiswa[max];
        nElemen = 0;
    }

    // 1. INSERT (Menjaga urutan)
    public void insert(long nim, String nama, String asal) {
        int i;
        for (i = 0; i < nElemen; i++) {
            if (mhs[i].getNim() > nim) break;
        }
        for (int k = nElemen; k > i; k--) { // Geser kanan
            mhs[k] = mhs[k - 1];
        }
        mhs[i] = new Mahasiswa(nim, nama, asal);
        nElemen++;
    }

    // 2. FIND (Binary Search - O(log n))
    public int find(long searchNim) {
        int lowerBound = 0;
        int upperBound = nElemen - 1;
        int curIn;

        while (true) {
            curIn = (lowerBound + upperBound) / 2;
            if (mhs[curIn].getNim() == searchNim) {
                return curIn; // Ketemu
            } else if (lowerBound > upperBound) {
                return nElemen; // Tidak ketemu
            } else {
                if (mhs[curIn].getNim() < searchNim) {
                    lowerBound = curIn + 1;
                } else {
                    upperBound = curIn - 1;
                }
            }
        }
    }

    // 3. DELETE (Cari dulu, lalu geser kiri - O(n))
    public boolean delete(long searchNim) {
        int j = find(searchNim);
        if (j == nElemen) {
            return false;
        } else {
            for (int k = j; k < nElemen - 1; k++) {
                mhs[k] = mhs[k + 1];
            }
            nElemen--;
            return true;
        }
    }

    public void display() {
        for (int i = 0; i < nElemen; i++) {
            mhs[i].displayMhs();
        }
    }
}

public class ArrayApp {
    public static void main(String[] args) {
        int maxSize = 100;
        OrderedArray arr = new OrderedArray(maxSize);

        // --- 1. DATA MENTAH (Simulasi input acak) ---
        long[] nimInput = {182, 150, 190, 166}; // Tambah satu data biar rame
        String[] namaInput = {"Baskara", "Budi", "Ani", "Sari"};
        String[] asalInput = {"Kalimantan", "Malang", "Surabaya", "Bandung"};

        System.out.println("--- Data Mentah (Input Acak) ---");
        for(int i=0; i < nimInput.length; i++) {
            System.out.println("Input: " + nimInput[i] + " - " + namaInput[i]);
        }

        // --- 2. INSERT (Otomatis Mengurutkan) ---
        System.out.println("\n>> Memasukkan data ke Ordered Array...");
        for(int i=0; i < nimInput.length; i++) {
            arr.insert(nimInput[i], namaInput[i], asalInput[i]);
        }

        System.out.println("--- Isi Array (Setelah Masuk) ---");
        arr.display();

        // --- 3. BINARY SEARCH (Fitur Utama) ---
        long keyCari = 182;
        System.out.println("\n--- Mencari NIM " + keyCari + " (Binary Search) ---");
        int foundIndex = arr.find(keyCari);
        
        if (foundIndex != maxSize) { // Asumsi maxSize adalah return value kalau not found
            System.out.println("Ketemu! Ada di index ke: " + foundIndex);
        } else {
            System.out.println("Data tidak ditemukan.");
        }

        // --- 4. DELETE ---
        long keyHapus = 150;
        System.out.println("\n--- Menghapus NIM " + keyHapus + " ---");
        boolean isDeleted = arr.delete(keyHapus);
        
        if (isDeleted) System.out.println("Berhasil dihapus!");
        else System.out.println("Gagal, data tidak ditemukan.");

        // --- 5. HASIL AKHIR ---
        System.out.println("\n--- Isi Array (Final) ---");
        arr.display();
    }
}