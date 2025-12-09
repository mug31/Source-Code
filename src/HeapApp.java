class HeapNode {
    private int iData; // Data utamanya (misal: nilai prioritas/angka)
    
    public HeapNode(int key) { iData = key; }
    
    public int getKey() { return iData; }
    public void setKey(int id) { iData = id; }
}

class Heap {
    private HeapNode[] heapArray; // Array buat nyimpen tumpukan data heap
    private int maxSize;          // Ukuran maksimum array
    private int currentSize;      // Jumlah item yang ada sekarang

    public Heap(int mx) {
        maxSize = mx;
        currentSize = 0;
        heapArray = new HeapNode[maxSize]; // Bikin array kosong
    }

    public boolean isEmpty() { return currentSize == 0; }

    // --- 1. INSERT (Nambah Data) ---
    // Logika: Taruh di paling belakang, lalu "dorong" ke atas sampai posisinya pas
    public boolean insert(int key) {
        if (currentSize == maxSize) return false; // Cek kalau penuh
        
        HeapNode newNode = new HeapNode(key);
        heapArray[currentSize] = newNode; // Taruh di index terakhir
        trickleUp(currentSize++);         // Lakukan Trickle Up (Naik Jabatan)
        return true;
    }

    // Logika Trickle Up (Naik ke Atas)
    // Dipakai pas INSERT. Kalau anak lebih gede dari bapaknya, tukar!
    public void trickleUp(int index) {
        int parent = (index - 1) / 2; // Rumus nyari index orang tua
        HeapNode bottom = heapArray[index]; // Simpan node baru yang mau dinaikin

        // Selama belum nyampe puncak (index > 0) DAN
        // Nilai parent LEBIH KECIL dari node baru (bottom)
        while (index > 0 && heapArray[parent].getKey() < bottom.getKey()) {
            heapArray[index] = heapArray[parent]; // Turunin bapaknya ke bawah
            index = parent;                       // Kita naik ke posisi bapak
            parent = (parent - 1) / 2;            // Hitung ulang bapaknya yang baru (kakek)
        }
        heapArray[index] = bottom; // Taruh node baru di posisi final yang aman
    }

    // --- 2. REMOVE (Hapus Data Terbesar/Root) ---
    // Logika: Ambil Root, pindahin data terakhir ke Root, lalu "dorong" ke bawah
    public HeapNode remove() { 
        if (currentSize == 0) return null; // Kalau kosong gak bisa dihapus
        
        HeapNode root = heapArray[0]; // Simpan Root (Nilai Max) buat di-return nanti
        
        // Pindahkan elemen paling akhir (buntut) ke posisi Root (kepala)
        // currentSize dikurangin dulu (--currentSize)
        heapArray[0] = heapArray[--currentSize];
        
        trickleDown(0); // Lakukan Trickle Down (Turun Pangkat) buat si pengganti tadi
        return root;
    }

    // Logika Trickle Down (Turun ke Bawah)
    // Dipakai pas REMOVE. Cek anak-anaknya, kalau ada anak yang lebih gede, tukar!
    public void trickleDown(int index) {
        int largerChild;
        HeapNode top = heapArray[index]; // Simpan node yang lagi "salah posisi" di atas

        // Loop selama node ini masih punya setidaknya satu anak (Left Child)
        // Batasnya currentSize / 2 karena mulai dari situ ke belakang adalah daun (gak punya anak)
        while (index < currentSize / 2) {
            int leftChild = 2 * index + 1;  // Rumus index anak kiri
            int rightChild = leftChild + 1; // Rumus index anak kanan

            // Cek mana anak yang lebih gede? Kiri atau Kanan?
            if (rightChild < currentSize && // Pastikan anak kanan beneran ada
                heapArray[leftChild].getKey() < heapArray[rightChild].getKey()) {
                largerChild = rightChild; // Anak kanan lebih gede
            } else {
                largerChild = leftChild;  // Anak kiri lebih gede (atau gak punya anak kanan)
            }

            // Kalau nilai si node (top) udah LEBIH GEDE dari anak terkuatnya, STOP.
            // Posisi dia udah bener (jadi Bos).
            if (top.getKey() >= heapArray[largerChild].getKey()) break;

            // Kalau kalah gede, anak yang kuat naik menggantikan posisi bapak
            heapArray[index] = heapArray[largerChild];
            index = largerChild; // Pointer turun ke posisi anak tadi, lanjut cek cucu
        }
        heapArray[index] = top; // Taruh node di posisi final
    }

    // Method buat nampilin isi array apa adanya
    public void displayArray() {
        for(int j=0; j<currentSize; j++)
            System.out.print(heapArray[j].getKey() + " ");
        System.out.println("");
    }
}

public class HeapApp {
    public static void main(String[] args) {
        Heap theHeap = new Heap(31); // Bikin heap kapasitas 31

        System.out.println(">> Insert Data: 70, 40, 50, 20, 60, 100, 80");
        // Masukin data acak. Perhatikan angka 100 masuk belakangan tapi nanti jadi Root
        theHeap.insert(70);
        theHeap.insert(40);
        theHeap.insert(50);
        theHeap.insert(20);
        theHeap.insert(60);
        theHeap.insert(100);
        theHeap.insert(80);

        System.out.print("Heap Array Structure (Isi Memori): ");
        theHeap.displayArray(); // Bakal kelihatan 100 ada di depan

        System.out.println("\n>> Executing Heap Sort (Repeated Remove Max):");
        // Selama heap belum kosong, cabut Root-nya terus menerus
        // Karena Root selalu nilai MAX, outputnya pasti urut dari Gede -> Kecil
        while(!theHeap.isEmpty()) {
            HeapNode max = theHeap.remove(); 
            System.out.print(max.getKey() + " ");
        }
        System.out.println("");
    }
}