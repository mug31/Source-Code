// --- CLASS NODE ---
class Node {
    public int id;          // Key data (misal: ID Barang)
    public String data;     // Value data (misal: Nama Barang)
    public Node leftChild;
    public Node rightChild;

    public Node(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public void displayNode() {
        System.out.print("{ " + id + ", " + data + " } ");
    }
}

// --- CLASS TREE (BST Logic) ---
class Tree {
    private Node root;

    public Tree() { root = null; }

    // 1. FIND: O(log N)
    public Node find(int key) {
        Node current = root;
        while (current.id != key) {
            if (key < current.id) current = current.leftChild;  // Ke Kiri
            else current = current.rightChild;                  // Ke Kanan
            
            if (current == null) return null; // Tidak ketemu
        }
        return current;
    }

    // 2. INSERT: Menjaga properti BST (Kiri < Parent < Kanan)
    public void insert(int id, String data) {
        Node newNode = new Node(id, data);
        if (root == null) {
            root = newNode;
        } else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                // Cek Duplicate Key (Optional, sesuai Learning Outcome #5)
                if (id == current.id) {
                    System.out.println("Gagal Insert: ID " + id + " sudah ada (Duplicate).");
                    return;
                }
                
                if (id < current.id) { // Belok Kiri
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = newNode;
                        return;
                    }
                } else {               // Belok Kanan
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }

    // 3. DELETE (Logic Paling Rumit)
    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        // Step 1: Cari Node-nya
        while (current.id != key) {
            parent = current;
            if (key < current.id) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) return false; // Gak ketemu
        }

        // Step 2: Hapus Node berdasarkan kondisi children
        // Kondisi 1: Tidak punya anak (Leaf)
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) root = null;
            else if (isLeftChild) parent.leftChild = null;
            else parent.rightChild = null;
        }
        // Kondisi 2: Punya 1 anak (Kanan saja)
        else if (current.leftChild == null) {
            if (current == root) root = current.rightChild;
            else if (isLeftChild) parent.leftChild = current.rightChild;
            else parent.rightChild = current.rightChild;
        }
        // Kondisi 3: Punya 1 anak (Kiri saja)
        else if (current.rightChild == null) {
            if (current == root) root = current.leftChild;
            else if (isLeftChild) parent.leftChild = current.leftChild;
            else parent.rightChild = current.leftChild;
        }
        // Kondisi 4: Punya 2 anak (Cari Successor)
        else {
            Node successor = getSuccessor(current);
            if (current == root) root = successor;
            else if (isLeftChild) parent.leftChild = successor;
            else parent.rightChild = successor;
            successor.leftChild = current.leftChild; // Sambungin anak kiri lama ke successor
        }
        return true;
    }

    // Helper untuk Kondisi 4: Cari pengganti (nilai terkecil di subtree kanan)
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild; // Mulai dari kanan
        while (current != null) {          // Lalu kiri terus sampai mentok
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    // 4. TRAVERSAL
    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1: 
                System.out.print("PreOrder: "); 
                preOrder(root); 
                break;
            case 2: 
                System.out.print("InOrder (Sorted): "); 
                inOrder(root); 
                break;
            case 3: 
                System.out.print("PostOrder: "); 
                postOrder(root); 
                break;
        }
        System.out.println();
    }

    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.id + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }
    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.id + " ");
            inOrder(localRoot.rightChild);
        }
    }
    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.id + " ");
        }
    }
    
    // 5. MIN & MAX VALUE
    public void minMax() {
        Node current = root;
        Node last = null;
        while(current != null) { last = current; current = current.leftChild; }
        System.out.println("Min Value: " + last.id);
        
        current = root;
        while(current != null) { last = current; current = current.rightChild; }
        System.out.println("Max Value: " + last.id);
    }
}

public class TreeApp {
    public static void main(String[] args) {
        Tree theTree = new Tree();
        
        // Insert Data (Acak)
        System.out.println(">> Inserting: 50, 25, 75, 12, 37, 43, 30");
        theTree.insert(50, "Ahmad");
        theTree.insert(25, "Rosa");
        theTree.insert(75, "Raisa");
        theTree.insert(12, "Naya");
        theTree.insert(37, "Gagas");
        theTree.insert(43, "Ainun");
        theTree.insert(30, "Beri");
        
        // Test Duplicate
        System.out.print(">> Test Insert Duplicate (50): ");
        theTree.insert(50, "Double");

        // Traversals
        System.out.println("\n--- Traversal ---");
        theTree.traverse(1); // PreOrder
        theTree.traverse(2); // InOrder (Harus Urut)
        theTree.traverse(3); // PostOrder
        
        // Min Max
        System.out.println("\n--- Statistics ---");
        theTree.minMax();

        // Find & Delete
        int key = 25;
        System.out.println("\n--- Operation ---");
        System.out.println("Find " + key + ": " + (theTree.find(key) != null ? "Found" : "Not Found"));
        System.out.println("Delete " + key + " (Node with 2 children)...");
        theTree.delete(key);
        
        theTree.traverse(2); // Cek InOrder lagi untuk validasi struktur
    }
}