// --- CLASS 1: STACK (LIFO) ---
class Stack {
    private int maxSize;
    private long[] stackArray;
    private int top;

    public Stack(int s) {
        maxSize = s;
        stackArray = new long[maxSize];
        top = -1; // -1 artinya tumpukan kosong (belum ada index 0)
    }

    public void push(long j) {
        if (top == maxSize - 1) {
            System.out.println("Stack Overflow! Gak muat.");
        } else {
            stackArray[++top] = j; // Naikkan top dulu, baru isi
        }
    }

    public long pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow! Kosong.");
            return -1;
        } else {
            return stackArray[top--]; // Ambil data, baru turunkan top
        }
    }

    public long peek() { return stackArray[top]; }
    public boolean isEmpty() { return (top == -1); }
}

// --- CLASS 2: CIRCULAR QUEUE (FIFO) ---
class Queue {
    private int maxSize;
    private long[] queArray;
    private int front;
    private int rear;
    private int nItems; // Counter item biar gak pusing ngitung index

    public Queue(int s) {
        maxSize = s;
        queArray = new long[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }

    // INSERT (Logika Circular)
    public void insert(long j) {
        if (nItems == maxSize) {
            System.out.println("Queue Penuh! Data " + j + " ditolak.");
        } else {
            // WRAPAROUND: Kalau rear udah di ujung, balikin ke sebelum 0
            if (rear == maxSize - 1) {
                rear = -1;
            }
            queArray[++rear] = j;
            nItems++;
        }
    }

    // REMOVE (Logika Circular)
    public long remove() {
        if (nItems == 0) {
            System.out.println("Queue Kosong!");
            return -1;
        } else {
            long temp = queArray[front++];
            // WRAPAROUND: Kalau front bablas lewatin ujung, balikin ke 0
            if (front == maxSize) {
                front = 0;
            }
            nItems--;
            return temp;
        }
    }

    public boolean isEmpty() { return (nItems == 0); }
    
    // Visualisasi isi queue (Tricky karena index-nya muter)
    public void viewQueue() {
        System.out.print("Isi Queue: ");
        if (isEmpty()) {
            System.out.println("[Kosong]");
            return;
        }
        
        int tempFront = front;
        for(int i=0; i<nItems; i++) {
            System.out.print(queArray[tempFront] + " ");
            tempFront++;
            if(tempFront == maxSize) tempFront = 0; // Reset index pas display
        }
        System.out.println();
    }
}

// --- MAIN APP ---
public class StackQueueApp {
    public static void main(String[] args) {
        // 1. DEMO STACK
        System.out.println("=== TEST STACK (LIFO) ===");
        Stack theStack = new Stack(5);
        System.out.println("Push: 10, 20, 30");
        theStack.push(10); 
        theStack.push(20); 
        theStack.push(30);
        
        System.out.print("Pop (Keluar): ");
        while(!theStack.isEmpty()) {
            System.out.print(theStack.pop() + " ");
        }
        System.out.println("\n");

        // 2. DEMO CIRCULAR QUEUE
        System.out.println("=== TEST CIRCULAR QUEUE (FIFO) ===");
        // Pake size kecil (4) biar kelihatan efek muter-nya
        Queue theQueue = new Queue(4);

        System.out.println(">> Insert 10, 20, 30, 40 (Sampe Penuh)");
        theQueue.insert(10); theQueue.insert(20); 
        theQueue.insert(30); theQueue.insert(40);
        theQueue.viewQueue();

        System.out.println(">> Remove 2 items (10 & 20 keluar -> Index 0 & 1 jadi kosong)");
        theQueue.remove(); 
        theQueue.remove();
        theQueue.viewQueue();

        System.out.println(">> Insert 50 & 60 (Harusnya muter balik ngisi index 0 & 1)");
        theQueue.insert(50); 
        theQueue.insert(60);
        theQueue.viewQueue();
        
        System.out.println(">> Coba insert 70 (Harusnya ditolak)");
        theQueue.insert(70);
    }
}