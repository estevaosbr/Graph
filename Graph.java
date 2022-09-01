import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graph {

  private int countNodes;
  private int countEdges;
  private int[][] adjMatrix;

  public Graph(int numNodes) {
    this.countNodes = numNodes;
    this.countEdges = 0;
    this.adjMatrix = new int[numNodes][numNodes];
  }

  public void addEdge(int source, int sink, int weight) {
    if (source < 0 || source > this.adjMatrix.length - 1 ||
        sink < 0 || sink > this.adjMatrix.length - 1 ||
        weight <= 0) {
      System.err.println("Invalid edge: " + source + sink + weight);
      return;
    }
    this.countEdges++;
    adjMatrix[source][sink] = weight;
  }

  public void addEdgeUnoriented(int source, int sink, int weight){
    addEdge(source,sink,weight);
    addEdge(sink,source,weight);
  }

  public int degree(int node) {
    int num = 0;
    for (int i = 0; i < adjMatrix.length; i++) {
      if (adjMatrix[node][i] != 0) {
        num++;
      }
    }
    return num;
  }

  public int highestDegree() {
    int x = 0;
    for (int i = 0; i < adjMatrix.length; i++) {
      if (x < this.degree(i)) {
        x = this.degree(i);
      }
    }
    return x;
  }

  public int lowestDegree() {
    int x = this.degree(0);
    for (int i = 0; i < adjMatrix.length; i++) {
      if (x > this.degree(i)) {
        x = this.degree(i);
      }
    }
    return x;
  }

  public Graph complement() {
    Graph complement = new Graph(this.countNodes);
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        if ((this.adjMatrix[i][j] == 0) && (i != j)) {
          complement.addEdge(i, j, 1);
        }
      }
    }
    return complement;
  }

  public boolean subgraph(Graph g2) {
    if (this.countNodes < g2.countNodes || this.countEdges < g2.countEdges)
      return false;
    for (int i = 0; i < g2.adjMatrix.length;i++){
      for (int j = 0; j < g2.adjMatrix.length;j++){
        if (g2.adjMatrix[i][j] != 0 && this.adjMatrix[i][j] == 0)
          return false;
      }
    }
    return true;
  }

  public float density(){
    float d = 0;
    d = (float) (this.countEdges)/(this.countNodes*(this.countNodes-1));
    return d;
  }

  public boolean oriented() {
    for (int i = 0; i < adjMatrix.length; i++) {
      for (int j = 0; j < adjMatrix.length; j++) {
        if (adjMatrix[i][j] != adjMatrix[j][i]) {
          return true;
        }
      }
    }
    return false;
  }

  public ArrayList buscaEmLargura(int s){
    int[] descobertos = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      descobertos[i] = 0;
    }

    descobertos[s] = 1;
    ArrayList q = new ArrayList();
    ArrayList r = new ArrayList();
    q.add(s);
    r.add(s);

    while (q.isEmpty()!=true){
      int u = (int)q.remove(0);
      for (int j = 0 ; j < this.countNodes ; j++){
        if (this.adjMatrix[u][j] == 1){
          if (descobertos[j] == 0) {
            q.add(j);
            r.add(j);
            descobertos[j] = 1;
          }
        }
      }
    }
    return r;
  }

  public int notDescAdj(int u, int[] desc){
    for (int i = 0 ; i < this.adjMatrix[u].length ; i++){
      if (this.adjMatrix[u][i] != 0 && desc[i] ==0){
        return i;
      }
    }
    return -1;
  }

  public ArrayList buscaEmProfundidade(int s){
    int[] descobertos = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      descobertos[i] = 0;
    }
    descobertos[s] = 1;
    ArrayList<Integer> q = new ArrayList<>();
    ArrayList r = new ArrayList();
    q.add(s);
    r.add(s);
    while (q.isEmpty()!=true){
      int u = (int)q.get(q.size()-1);
      int v = notDescAdj(u,descobertos);
      if ( v!= -1){
        q.add(v);
        r.add(v);
        descobertos[v] = 1;
      }
      else {
        q.remove(q.size() - 1);
      }
    }
    return r;
  }

  public ArrayList dfs_rec(int s){
    int[] descobertos = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      descobertos[i] = 0;
    }
    ArrayList r = new ArrayList();
    dfs_rec_aux(s,descobertos,r);
    return r;
  }

  private void dfs_rec_aux(int u, int[] desc, ArrayList r){
    desc[u] = 1;
    r.add(u);
    for (int v = 0; v < this.adjMatrix[u].length; ++v) {
      if (this.adjMatrix[u][v] != 0 && desc[v] == 0) {
        dfs_rec_aux(v, desc, r);
      }
    }
  }

  public boolean isConex(){
    ArrayList array = buscaEmLargura(0);
    if (array.size() == this.countNodes){
      return true;
    }
    else{
      return false;
    }
  }

  public Graph(String fileName) throws IOException {
    File file = new File(fileName);
    FileReader reader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(reader);

    // Read header
    String[] line = bufferedReader.readLine().split(" ");
    this.countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));
    // Create and fill adjMatrix with read edges
    this.adjMatrix = new int[this.countNodes][this.countNodes];
    for (int i = 0; i < fileLines; ++i) {
      String[] edgeInfo = bufferedReader.readLine().split(" ");
      int source = Integer.parseInt(edgeInfo[0]);
      int sink = Integer.parseInt(edgeInfo[1]);
      int weight = Integer.parseInt(edgeInfo[2]);
      addEdge(source, sink, weight);
    }
    bufferedReader.close();
    reader.close();
  }

  public ArrayList ord_top(){
    int[] desc = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      desc[i] = 0;
    }

    ArrayList r = new ArrayList();

    for (int i = 0 ; i < this.countNodes ; i++){
      if (desc[i] == 0) {
        ord_top_aux(i,desc,r);
      }
    }
    return r;
  }

  private void ord_top_aux(int u, int[] desc, ArrayList r){
    desc[u] = 1;

    for (int i = 0 ; i < this.adjMatrix[u].length ; i++){
      if (this.adjMatrix[u][i] == 1){
        if (desc[i] == 0){
          ord_top_aux(i,desc,r);
        }
      }
    }
    r.add(0,u);
  }

  @Override
  public String toString() {
    String str = "";
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix.length; j++) {
        str += this.adjMatrix[i][j] + "\t";
      }
      str += "\n";
    }
    return "Graph" + "\n" +
        "countNodes: " + countNodes + "\n" +
        "countEdges: " + countEdges + "\n\n" +
        str;
  }
}
