class AdvancedSortArray {
    private long[] theArray;
    private int nElems;

    public AdvancedSortArray(int max) {
        theArray = new long[max];
        nElems = 0;
    }

    public void insert(long value) {
        theArray[nElems] = value;
        nElems++;
    }

    public void display() {
        System.out.print("Data: ");
        for (int j = 0; j < nElems; j++) System.out.print(theArray[j] + " ");
        System.out.println("");
    }

    // --- 1. SHELL SORT (Interval-based Sort) ---
    public void shellSort() {
        int inner, outer;
        long temp;
        int h = 1;
        
        // 1. Hitung Interval Awal (Knuth Sequence: 1, 4, 13, 40...)
        // Kita cari 'h' terbesar yang muat di array
        while (h <= nElems / 3) {
            h = h * 3 + 1; 
        }

        // 2. Loop selama interval > 0
        while (h > 0) {
            // Lakukan Insertion Sort tapi dengan lompatan 'h'
            for (outer = h; outer < nElems; outer++) {
                temp = theArray[outer];
                inner = outer;

                // Cek elemen sejauh 'h' di belakangnya
                // Jika elemen belakang lebih besar, geser ke depan
                while (inner > h - 1 && theArray[inner - h] >= temp) {
                    theArray[inner] = theArray[inner - h];
                    inner -= h; // Mundur sejauh 'h'
                }
                theArray[inner] = temp;
            }
            // 3. Kecilkan interval (Inverse Knuth: (h-1)/3)
            h = (h - 1) / 3;
        }
    }

    // --- 2. QUICK SORT (Partition-based Sort) ---
    public void quickSort() {
        recQuickSort(0, nElems - 1);
    }

    // Fungsi Rekursif utama
    private void recQuickSort(int left, int right) {
        // Base Case: Jika size array <= 1, tidak perlu di-sort
        if (right - left <= 0) { 
            return;
        } else {
            long pivot = theArray[right]; // Tentukan Pivot (ambil paling kanan)
            
            // Lakukan Partisi: Kembalikan lokasi final pivot
            int partition = partitionIt(left, right, pivot);
            
            // Rekursif: Sort bagian Kiri (lebih kecil dari pivot)
            recQuickSort(left, partition - 1);
            // Rekursif: Sort bagian Kanan (lebih besar dari pivot)
            recQuickSort(partition + 1, right);
        }
    }

    // Mekanisme Partisi: Memisah data berdasarkan Pivot
    private int partitionIt(int left, int right, long pivot) {
        int leftPtr = left - 1;
        int rightPtr = right; // Pivot ada di index 'right'

        while (true) {
            // Scan dari Kiri: Cari yang lebih besar dari Pivot
            while (theArray[++leftPtr] < pivot) {
                // Nop (Looping terus sampai nemu yang >= pivot)
            }
            // Scan dari Kanan: Cari yang lebih kecil dari Pivot
            while (rightPtr > 0 && theArray[--rightPtr] > pivot) {
                // Nop (Looping terus sampai nemu yang <= pivot)
            }

            // Jika pointer ketemu/berpapasan, partisi selesai
            if (leftPtr >= rightPtr) {
                break;
            } else {
                // Jika belum, tukar posisi elemen kiri dan kanan tersebut
                swap(leftPtr, rightPtr);
            }
        }
        // Terakhir, letakkan Pivot di tengah-tengah (di posisi leftPtr)
        swap(leftPtr, right); 
        return leftPtr; // Return lokasi pivot yang baru
    }

    private void swap(int dex1, int dex2) {
        long temp = theArray[dex1];
        theArray[dex1] = theArray[dex2];
        theArray[dex2] = temp;
    }
}

public class AdvancedSortApp {
    public static void main(String[] args) {
        int maxSize = 100;
        
        System.out.println("=== 1. TEST SHELL SORT ===");
        AdvancedSortArray arr1 = new AdvancedSortArray(maxSize);
        // Data acak
        arr1.insert(70); arr1.insert(80); arr1.insert(10);
        arr1.insert(30); arr1.insert(50); arr1.insert(20);
        arr1.insert(60); arr1.insert(40); arr1.insert(90);
        
        arr1.display();
        System.out.println(">> Eksekusi Shell Sort...");
        arr1.shellSort();
        arr1.display();

        System.out.println("\n=== 2. TEST QUICK SORT ===");
        AdvancedSortArray arr2 = new AdvancedSortArray(maxSize);
        // Data yang sama
        arr2.insert(70); arr2.insert(80); arr2.insert(10);
        arr2.insert(30); arr2.insert(50); arr2.insert(20);
        arr2.insert(60); arr2.insert(40); arr2.insert(90);

        arr2.display();
        System.out.println(">> Eksekusi Quick Sort...");
        arr2.quickSort();
        arr2.display();
    }
}