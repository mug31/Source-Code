// --- 1. CLASS MATH & PUZZLE (Logika Dasar Rekursi) ---
class RecursiveLogic {
    
    // a. Bilangan Segitiga (Penjumlahan beruntun: 5+4+3+2+1)
    public int triangle(int n) {
        if (n == 1) return 1; // Base case
        return n + triangle(n - 1);
    }

    // b. Faktorial (Perkalian beruntun: 5*4*3*2*1)
    public int factorial(int n) {
        if (n == 0) return 1; // Base case
        return n * factorial(n - 1);
    }

    // c. Pangkat (Base^Exp)
    public int power(int base, int exp) {
        if (exp == 0) return 1; // Base case (apapun pangkat 0 = 1)
        return base * power(base, exp - 1);
    }

    // d. Menara Hanoi (The Legend)
    public void doTowers(int topN, char src, char inter, char dest) {
        if (topN == 1) {
            System.out.println("Piringan 1 pindah dari " + src + " ke " + dest);
        } else {
            // 1. Pindahin N-1 piringan dari Sumber ke Sementara
            doTowers(topN - 1, src, dest, inter);
            // 2. Pindahin piringan paling gede (N) dari Sumber ke Tujuan
            System.out.println("Piringan " + topN + " pindah dari " + src + " ke " + dest);
            // 3. Pindahin lagi N-1 piringan dari Sementara ke Tujuan
            doTowers(topN - 1, inter, src, dest);
        }
    }
}

// --- 2. CLASS MERGE SORT (Aplikasi Rekursi di Sorting) ---
class DArray {
    private long[] theArray;
    private int nElems;

    public DArray(int max) {
        theArray = new long[max];
        nElems = 0;
    }

    public void insert(long value) {
        theArray[nElems] = value;
        nElems++;
    }

    public void display() {
        for (int j = 0; j < nElems; j++) System.out.print(theArray[j] + " ");
        System.out.println("");
    }

    public void mergeSort() {
        long[] workSpace = new long[nElems];
        recMergeSort(workSpace, 0, nElems - 1);
    }

    // Fungsi Rekursif buat membelah array
    private void recMergeSort(long[] workSpace, int lowerBound, int upperBound) {
        if (lowerBound == upperBound) {
            return; // Base case: kalau data tinggal 1, gak usah diapa-apain
        } else {
            int mid = (lowerBound + upperBound) / 2;
            // Belah Kiri
            recMergeSort(workSpace, lowerBound, mid);
            // Belah Kanan
            recMergeSort(workSpace, mid + 1, upperBound);
            // Gabungin (Merge)
            merge(workSpace, lowerBound, mid + 1, upperBound);
        }
    }

    // Logika penggabungan dua bagian array yang sudah urut
    private void merge(long[] workSpace, int lowPtr, int highPtr, int upperBound) {
        int j = 0;
        int lowerBound = lowPtr;
        int mid = highPtr - 1;
        int n = upperBound - lowerBound + 1;

        while (lowPtr <= mid && highPtr <= upperBound) {
            if (theArray[lowPtr] < theArray[highPtr])
                workSpace[j++] = theArray[lowPtr++];
            else
                workSpace[j++] = theArray[highPtr++];
        }
        while (lowPtr <= mid) workSpace[j++] = theArray[lowPtr++];
        while (highPtr <= upperBound) workSpace[j++] = theArray[highPtr++];

        for (j = 0; j < n; j++) theArray[lowerBound + j] = workSpace[j];
    }
}

// --- MAIN APP ---
public class RecursionApp {
    public static void main(String[] args) {
        RecursiveLogic rec = new RecursiveLogic();

        System.out.println("=== 1. BASIC MATH RECURSION ===");
        System.out.println("Triangle(5) : " + rec.triangle(5)); // 5+4+3+2+1
        System.out.println("Faktorial(5): " + rec.factorial(5)); // 5*4*3*2*1
        System.out.println("Pangkat(2^5): " + rec.power(2, 5)); // 2*2*2*2*2

        System.out.println("\n=== 2. MENARA HANOI (3 Piringan) ===");
        // A=Sumber, B=Sementara, C=Tujuan
        rec.doTowers(3, 'A', 'B', 'C');

        System.out.println("\n=== 3. MERGE SORT ===");
        DArray arr = new DArray(10);
        arr.insert(64); arr.insert(21); arr.insert(33); arr.insert(70);
        arr.insert(12); arr.insert(85); arr.insert(44); arr.insert(3);
        
        System.out.print("Sebelum Sort: "); arr.display();
        arr.mergeSort();
        System.out.print("Setelah Sort: "); arr.display();
    }
}