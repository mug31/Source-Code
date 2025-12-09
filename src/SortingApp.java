class DataArraySort {
    private Mahasiswa[] mhs;
    private int nElemen;

    public DataArraySort(int max) {
        mhs = new Mahasiswa[max];
        nElemen = 0;
    }

    public void insert(long nim, String nama, String asal) {
        mhs[nElemen] = new Mahasiswa(nim, nama, asal);
        nElemen++;
    }

    // Helper method buat nuker posisi (dipake Bubble & Selection)
    private void swap(int one, int two) {
        Mahasiswa temp = mhs[one];
        mhs[one] = mhs[two];
        mhs[two] = temp;
    }

    // 1. BUBBLE SORT: Tukar terus sampe pegel
    public void bubbleSortByName() {
        int batas, i;
        for (batas = nElemen - 1; batas > 0; batas--) {
            for (i = 0; i < batas; i++) {
                // Kalau nama kiri > nama kanan, TUKAR!
                if (mhs[i].getNama().compareToIgnoreCase(mhs[i + 1].getNama()) > 0) {
                    swap(i, i + 1);
                }
            }
        }
    }

    // 2. SELECTION SORT: Cari yang paling kecil, baru tukar
    public void selectionSortByName() {
        int awal, i, min;
        for (awal = 0; awal < nElemen - 1; awal++) {
            min = awal; // Anggap awal adalah yang terkecil sementara
            for (i = awal + 1; i < nElemen; i++) {
                // Kalau nemu yang lebih kecil lagi, tandain indexnya
                if (mhs[i].getNama().compareToIgnoreCase(mhs[min].getNama()) < 0) {
                    min = i;
                }
            }
            swap(awal, min); // Tukar si kecil ke posisi awal
        }
    }

    // 3. INSERTION SORT: Geser dan Sisipkan (Paling Efisien)
    public void insertionSortByName() {
        int in, out;
        for (out = 1; out < nElemen; out++) {
            Mahasiswa temp = mhs[out];
            in = out;
            // Geser kanan selama nama kiri > nama temp
            while (in > 0 && mhs[in - 1].getNama().compareToIgnoreCase(temp.getNama()) > 0) {
                mhs[in] = mhs[in - 1];
                in--;
            }
            mhs[in] = temp; // Sisipkan
        }
    }

    public void display() {
        for (int i = 0; i < nElemen; i++) {
            mhs[i].displayMhs();
        }
    }
}

public class SortingApp {
    public static void main(String[] args) {
        int maxSize = 100;
        DataArraySort arr = new DataArraySort(maxSize);

        // Masukin data acak
        arr.insert(182, "Baskara", "Kalimantan");
        arr.insert(150, "Zaki", "Jakarta");
        arr.insert(190, "Andi", "Surabaya");
        arr.insert(166, "Budi", "Malang");

        System.out.println("--- Data Mentah ---");
        arr.display();
        // Ganti method ini kalau mau coba algoritma lain:
        // arr.bubbleSortByName();
        // arr.selectionSortByName();
        System.out.println("\n>> Mengurutkan dengan Selection Sort...");
        arr.selectionSortByName();
        arr.display();
    }
}