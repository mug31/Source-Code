// Helper Class: Stack untuk DFS
class StackX {
    private int[] st; private int top;
    public StackX() { st = new int[20]; top = -1; }
    public void push(int j) { st[++top] = j; }
    public int pop() { return st[top--]; }
    public int peek() { return st[top]; }
    public boolean isEmpty() { return (top == -1); }
}

// Helper Class: Queue untuk BFS
class QueueX {
    private int[] que; private int front, rear, size;
    public QueueX() { que = new int[20]; front = 0; rear = -1; size = 0; }
    public void insert(int j) { if(rear==19) rear=-1; que[++rear] = j; size++; }
    public int remove() { int temp=que[front++]; if(front==20) front=0; size--; return temp; }
    public boolean isEmpty() { return (size==0); }
}

class Vertex {
    public char label;
    public boolean wasVisited;
    public Vertex(char lab) { label = lab; wasVisited = false; }
}

class Graph {
    private final int MAX_VERTS = 20;
    private Vertex vertexList[]; 
    private int adjMat[][];      
    private int nVerts;
    private StackX theStack;     
    private QueueX theQueue;     

    public Graph() {
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        // Inisialisasi Matrix dengan 0 (tidak ada koneksi)
        for(int j=0; j<MAX_VERTS; j++)
            for(int k=0; k<MAX_VERTS; k++) adjMat[j][k] = 0;
        theStack = new StackX();
        theQueue = new QueueX();
    }

    public void addVertex(char lab) { vertexList[nVerts++] = new Vertex(lab); }
    
    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1; // Undirected Graph (Simetris)
    }
    
    public void displayVertex(int v) { System.out.print(vertexList[v].label); }

    // --- 1. DEPTH FIRST SEARCH (Stack-based) ---
    public void dfs() {
        System.out.print("DFS Traversal: ");
        vertexList[0].wasVisited = true; 
        displayVertex(0);
        theStack.push(0);

        while(!theStack.isEmpty()) {
            // Cari tetangga unvisited dari top stack
            int v = getAdjUnvisitedVertex(theStack.peek());
            if(v == -1) {
                theStack.pop(); // Backtrack
            } else {
                vertexList[v].wasVisited = true;
                displayVertex(v);
                theStack.push(v); // Push ke stack untuk dive lebih dalam
            }
        }
        System.out.println("");
        resetFlags();
    }

    // --- 2. BREADTH FIRST SEARCH (Queue-based) ---
    public void bfs() {
        System.out.print("BFS Traversal: ");
        vertexList[0].wasVisited = true; 
        displayVertex(0);
        theQueue.insert(0);

        while(!theQueue.isEmpty()) {
            int v1 = theQueue.remove(); // Ambil vertex saat ini
            int v2;
            // Kunjungi semua tetangga unvisited
            while((v2 = getAdjUnvisitedVertex(v1)) != -1) {
                vertexList[v2].wasVisited = true;
                displayVertex(v2);
                theQueue.insert(v2); // Masukkan queue untuk diproses nanti
            }
        }
        System.out.println("");
        resetFlags();
    }

    // --- 3. MINIMUM SPANNING TREE (DFS Variant) ---
    public void mst() {
        System.out.print("MST Edges: ");
        vertexList[0].wasVisited = true;
        theStack.push(0);

        while(!theStack.isEmpty()) {
            int currentVertex = theStack.peek();
            int v = getAdjUnvisitedVertex(currentVertex);
            if(v == -1) {
                theStack.pop();
            } else {
                vertexList[v].wasVisited = true;
                theStack.push(v);
                
                // Tampilkan Edge yang valid
                displayVertex(currentVertex);
                System.out.print("-");
                displayVertex(v);
                System.out.print(" ");
            }
        }
        System.out.println("");
        resetFlags();
    }

    // Mengembalikan index tetangga unvisited pertama (-1 jika tidak ada)
    public int getAdjUnvisitedVertex(int v) {
        for(int j=0; j<nVerts; j++)
            if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
                return j;
        return -1;
    }
    
    public void resetFlags() {
        for(int j=0; j<nVerts; j++) vertexList[j].wasVisited = false;
    }
}

public class GraphApp {
    public static void main(String[] args) {
        Graph theGraph = new Graph();
        
        // Membangun Topologi Graph
        // A terhubung ke B, C, D, E. B terhubung ke F.
        
        theGraph.addVertex('A'); // 0
        theGraph.addVertex('B'); // 1
        theGraph.addVertex('C'); // 2
        theGraph.addVertex('D'); // 3
        theGraph.addVertex('F'); // 4

        theGraph.addEdge(0, 1); // AB
        theGraph.addEdge(0, 2); // AC
        theGraph.addEdge(1, 4); // BF
        theGraph.addEdge(2, 3); // CD (Koreksi dari diagram sebelumnya: C ke D)

        System.out.println("Topology: A connected to B,C. B-F. C-D.");
        
        // Eksekusi Algoritma
        theGraph.dfs(); 
        theGraph.bfs(); 
        theGraph.mst();
    }
}