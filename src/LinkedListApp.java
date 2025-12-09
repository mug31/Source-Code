// --- 1. CLASS LINK (Gerbong Kereta) ---
class Link {
    public long data;      // Datanya (misal: NIM/ID Barang)
    public Link next;      // Penunjuk ke gerbong depan
    public Link previous;  // Penunjuk ke gerbong belakang

    public Link(long d) { data = d; }
    
    public void displayLink() { System.out.print(data + " "); }
}

// --- 2. CLASS DOUBLY LINKED LIST (Lokomotif Pengatur) ---
class DoublyLinkedList {
    private Link first; // Kepala Kereta
    private Link last;  // Ekor Kereta

    public DoublyLinkedList() {
        first = null;
        last = null;
    }

    public boolean isEmpty() { return first == null; }

    // Insert di DEPAN (Kepala)
    public void insertFirst(long dd) {
        Link newLink = new Link(dd);
        if(isEmpty()) last = newLink; // Kalau kosong, dia jadi first & last
        else first.previous = newLink; // Link lama nunjuk ke yg baru
        newLink.next = first;          // Yang baru nunjuk ke yg lama
        first = newLink;               // Resmi jadi Kepala baru
    }

    // Insert di BELAKANG (Ekor)
    public void insertLast(long dd) {
        Link newLink = new Link(dd);
        if(isEmpty()) first = newLink;
        else {
            last.next = newLink;       // Ekor lama nunjuk yg baru
            newLink.previous = last;   // Yg baru nunjuk ekor lama
        }
        last = newLink;                // Resmi jadi Ekor baru
    }

    // Hapus DEPAN (Kepala)
    public Link deleteFirst() {
        if(isEmpty()) return null;
        Link temp = first;
        if(first.next == null) last = null; // Kalau cuma 1 item
        else first.next.previous = null;    // Putus hubungan balik
        first = first.next;                 // Kepala pindah ke gerbong ke-2
        return temp;
    }

    // Hapus BELAKANG (Ekor)
    public Link deleteLast() {
        if(isEmpty()) return null;
        Link temp = last;
        if(first.next == null) first = null;
        else last.previous.next = null;     // Putus hubungan depan
        last = last.previous;               // Ekor pindah ke gerbong ke-2 dari belakang
        return temp;
    }

    public void displayForward() {
        System.out.print("List (First-->Last): ");
        Link current = first;
        while(current != null) {
            current.displayLink();
            current = current.next;
        }
        System.out.println();
    }
}

// --- 3. IMPLEMENTASI STACK (Pake Linked List) ---
class LinkStack {
    private DoublyLinkedList theList;
    public LinkStack() { theList = new DoublyLinkedList(); }
    public void push(long j) { theList.insertFirst(j); }
    public long pop() { return theList.deleteFirst().data; }
    public boolean isEmpty() { return theList.isEmpty(); }
    public void display() { 
        System.out.print("Stack (Top-->Bottom): ");
        theList.displayForward(); 
    }
}

// --- 4. IMPLEMENTASI QUEUE (Pake Linked List) ---
class LinkQueue {
    private DoublyLinkedList theList;
    public LinkQueue() { theList = new DoublyLinkedList(); }
    public void insert(long j) { theList.insertLast(j); } // Masuk dari belakang
    public long remove() { return theList.deleteFirst().data; } // Keluar dari depan
    public boolean isEmpty() { return theList.isEmpty(); }
    public void display() { 
        System.out.print("Queue (Front-->Rear): ");
        theList.displayForward(); 
    }
}

// --- MAIN APP ---
public class LinkedListApp {
    public static void main(String[] args) {
        // A. TEST LINKED LIST MANUAL
        System.out.println("=== TEST DOUBLY LINKED LIST ===");
        DoublyLinkedList theList = new DoublyLinkedList();
        theList.insertFirst(22); // Masuk 22
        theList.insertFirst(44); // 44 jadi di depan 22
        theList.insertFirst(66); // 66 jadi di depan 44
        theList.insertLast(11);  // 11 jadi di paling belakang
        theList.insertLast(33);  // 33 jadi di belakang 11
        theList.displayForward();
        
        System.out.println(">> Hapus First (66) & Last (33)");
        theList.deleteFirst();
        theList.deleteLast();
        theList.displayForward();

        // B. TEST STACK (LIFO)
        System.out.println("\n=== TEST STACK via LINKED LIST ===");
        LinkStack stack = new LinkStack();
        stack.push(10); stack.push(20); stack.push(30);
        stack.display(); // Harusnya 30 20 10
        System.out.println("Pop: " + stack.pop());
        stack.display();

        // C. TEST QUEUE (FIFO)
        System.out.println("\n=== TEST QUEUE via LINKED LIST ===");
        LinkQueue queue = new LinkQueue();
        queue.insert(100); queue.insert(200); queue.insert(300);
        queue.display(); // Harusnya 100 200 300
        System.out.println("Remove: " + queue.remove()); // Harusnya 100 keluar
        queue.display();
    }
}