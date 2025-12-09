class DataItem {
    private int iData; // Data key

    public DataItem(int ii) { iData = ii; }
    public int getKey() { return iData; }
}

class HashTable {
    private DataItem[] hashArray;
    private int arraySize;
    private DataItem nonItem; // Penanda item yang dihapus

    public HashTable(int size) {
        arraySize = size;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem(-1); // -1 sebagai flag "deleted"
    }

    public void displayTable() {
        System.out.print("Table: ");
        for (int j = 0; j < arraySize; j++) {
            if (hashArray[j] != null) System.out.print(hashArray[j].getKey() + " ");
            else System.out.print("** ");
        }
        System.out.println("");
    }

    // Hash Function sederhana (Modulo)
    public int hashFunc(int key) {
        return key % arraySize;
    }

    // INSERT dengan Linear Probing
    public void insert(int key) {
        DataItem item = new DataItem(key);
        int hashVal = hashFunc(key); // Hitung indeks awal

        // Linear Probing: Cari slot kosong atau bekas hapus (-1)
        // Loop berhenti kalau nemu null atau -1
        while (hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1) {
            hashVal++;                 // Geser ke kanan
            hashVal %= arraySize;      // Wraparound (balik ke 0 jika mentok)
        }
        hashArray[hashVal] = item;     // Masukkan data
    }

    // DELETE dengan Linear Probing
    public DataItem delete(int key) {
        int hashVal = hashFunc(key);

        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getKey() == key) {
                DataItem temp = hashArray[hashVal];
                hashArray[hashVal] = nonItem; // Tandai sebagai "Deleted" (-1)
                return temp;
            }
            hashVal++;
            hashVal %= arraySize;
        }
        return null; // Gak ketemu
    }

    // FIND
    public DataItem find(int key) {
        int hashVal = hashFunc(key);

        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].getKey() == key) return hashArray[hashVal];
            hashVal++;
            hashVal %= arraySize;
        }
        return null;
    }
}

public class HashApp {
    public static void main(String[] args) {
        // Size tabel sebaiknya bilangan prima untuk mengurangi collision
        int size = 10; 
        HashTable theHashTable = new HashTable(size);

        // Skenario: Insert data (beberapa akan collision)
        // HashFunc = key % 10
        System.out.println(">> Insert Data: 10, 20, 35, 45, 88");
        theHashTable.insert(10); // Index 0
        theHashTable.insert(20); // Index 0 (Tabrakan!) -> Geser ke 1
        theHashTable.insert(35); // Index 5
        theHashTable.insert(45); // Index 5 (Tabrakan!) -> Geser ke 6
        theHashTable.insert(88); // Index 8
        
        theHashTable.displayTable();

        // Test Find
        int findKey = 45;
        System.out.println("\n>> Find " + findKey + ": " + 
            (theHashTable.find(findKey) != null ? "Found" : "Not Found"));

        // Test Delete & Re-Insert
        // Hapus 35 (Index 5). Harusnya pencarian 45 (Index 6) tetap jalan
        System.out.println(">> Delete 35 (Index 5)...");
        theHashTable.delete(35);
        theHashTable.displayTable();

        System.out.println(">> Find 45 again (Linear Probe check): " + 
            (theHashTable.find(findKey) != null ? "Found" : "Not Found"));
    }
}