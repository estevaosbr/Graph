package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphMatrix {

  private int countNodes;
  private int countEdges;
  private int[][] adjMatrix;
  private static final int INF = 999999;

  public GraphMatrix(int numNodes) {
    this.countNodes = numNodes;
    this.countEdges = 0;
    this.adjMatrix = new int[numNodes][numNodes];
  }

  public GraphMatrix(String fileName) throws IOException {
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

  public GraphMatrix complement() {
    GraphMatrix complement = new GraphMatrix(this.countNodes);
    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        if ((this.adjMatrix[i][j] == 0) && (i != j)) {
          complement.addEdge(i, j, 1);
        }
      }
    }
    return complement;
  }

  public boolean subgraph(GraphMatrix g2) {
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
    return (float) (this.countEdges)/(this.countNodes*(this.countNodes-1));
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

  public ArrayList<Integer> buscaEmLargura(int s){  //BFS
    int[] descobertos = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      descobertos[i] = 0;
    }

    descobertos[s] = 1;
    ArrayList<Integer> q = new ArrayList<>();
    ArrayList<Integer> r = new ArrayList<>();
    q.add(s);
    r.add(s);

    while (!q.isEmpty()){
      int u = q.remove(0);
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

  public ArrayList<Integer> buscaEmProfundidade(int s){ //DFS
    int[] descobertos = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      descobertos[i] = 0;
    }
    descobertos[s] = 1;
    ArrayList<Integer> q = new ArrayList<>();
    ArrayList<Integer> r = new ArrayList<>();
    q.add(s);
    r.add(s);
    while (!q.isEmpty()){
      int u = q.get(q.size()-1);
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

  public ArrayList<Integer> dfs_rec(int s){
    int[] descobertos = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      descobertos[i] = 0;
    }
    ArrayList<Integer> r = new ArrayList<>();
    dfs_rec_aux(s,descobertos,r);
    return r;
  }

  private void dfs_rec_aux(int u, int[] desc, ArrayList<Integer> r){
    desc[u] = 1;
    r.add(u);
    for (int v = 0; v < this.adjMatrix[u].length; ++v) {
      if (this.adjMatrix[u][v] != 0 && desc[v] == 0) {
        dfs_rec_aux(v, desc, r);
      }
    }
  }

  public boolean isConex(){
    ArrayList<Integer> array = buscaEmLargura(0);
    return array.size() == this.countNodes;
  }

  public ArrayList<Integer> ord_top(){
    int[] desc = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      desc[i] = 0;
    }

    ArrayList<Integer> r = new ArrayList<>();

    for (int i = 0 ; i < this.countNodes ; i++){
      if (desc[i] == 0) {
        ord_top_aux(i,desc,r);
      }
    }
    return r;
  }

  private void ord_top_aux(int u, int[] desc, ArrayList<Integer> r){
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

  public int[] connected_comp(){
    int[] desc = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      desc[i] = 0;
    }

    int comp = 0;

    for (int i = 0 ; i < this.countNodes ; i++){
      if (desc[i] == 0){
        comp = comp + 1;
        connected_comp_aux(i,desc,comp);
      }
    }
    return desc;
  }

  private void connected_comp_aux(int u, int[] desc, int comp){
    desc[u] = comp;
    for (int i = 0; i < this.adjMatrix[u].length ; i++){
      if (desc[i] == 0 && this.adjMatrix[u][i]!=0){
        connected_comp_aux(i,desc,comp);
      }
    }
  }

  public boolean has_cycle_oriented(){
    for (int i = 0; i < this.countNodes; i++) {
      if (has_cycle_oriented_aux(i))
        return true;
    }
    return false;
  }

  private boolean has_cycle_oriented_aux(int s){
    int[] descobertos = new int[this.countNodes];
    for (int i = 0; i < this.countNodes; i++){
      descobertos[i] = 0;
    }

    descobertos[s] = 1;
    ArrayList<Integer> q = new ArrayList<>();
    q.add(s);


    while (!q.isEmpty()) {
      int u = q.remove(0);
      for (int j = 0; j < this.countNodes; j++) {
        if (this.adjMatrix[u][j] != 0) {
          if (descobertos[j] == 0) {
            q.add(j);
            descobertos[j] = 1;
          } else {
            return true;
          }
        }
      }
    }
    return false;
  }

  public void floydWarshall(int s, int t) {
    int[][] dist = new int[this.countNodes][this.countNodes];
    int[][] pred = new int[this.countNodes][this.countNodes];
    for (int i = 0; i < this.adjMatrix.length; ++i) {
      for (int j = 0; j < this.adjMatrix[i].length; ++j) {
        if (i == j) {
          dist[i][j] = 0;
          pred[i][j] = -1;
        } else if (this.adjMatrix[i][j] != 0) { // src.Edge (i, j) exists
          dist[i][j] = this.adjMatrix[i][j];
          pred[i][j] = i;
        } else {
          dist[i][j] = INF;
          pred[i][j] = -1;
        }
      }
    }
    for (int k = 0; k < this.countNodes; ++k) {
      for (int i = 0; i < this.countNodes; ++i) {
        for (int j = 0; j < this.countNodes; ++j) {
          if (dist[i][j] > dist[i][k] + dist[k][j]) {
            dist[i][j] = dist[i][k] + dist[k][j];
            pred[i][j] = pred[k][j];
          }
        }
      }
    }
    // Recovering paths
    System.out.printf("Distance from %d to %d is: %d ", s, t, dist[s][t]);
    ArrayList<Integer> C = new ArrayList<Integer>();
    C.add(t);
    int aux = t;
    while (aux != s) {
      aux = pred[s][aux];
      C.add(0, aux);
    }
    System.out.println("Path: " + C);
  }

  public int menorDist(int[] dist, ArrayList<Integer> q){
    int menor = dist[q.get(0)];
    int u = q.get(0);

    for (int i = 0; i < this.countNodes; i++) {
      if(q.contains(i)){
        if(dist[i] < menor){
          menor = dist[i];
          u = i;
        }
      }
    }
    return u;
  }

  public void dijkstra(int s, int t) {
    long start = System.currentTimeMillis();
    int[] pred = new int[this.countNodes];
    int[] dist = new int[this.countNodes];

    for (int i = 0; i < this.countNodes; i++) {
      pred[i] = -1;
      dist[i] = INF;
    }

    dist[s] = 0;
    ArrayList<Integer> q = new ArrayList<Integer>();
    for (int i = 0; i < this.countNodes; i++) {
      q.add(i);
    }

    while (q.size() != 0) {
      Integer u = menorDist(dist, q);
      q.remove(u);

      for (int i = 0; i < this.adjMatrix[u].length; i++) {
        if (this.adjMatrix[u][i] != 0) {
          if (dist[i] > dist[u] + this.adjMatrix[u][i]) {
            dist[i] = dist[u] + this.adjMatrix[u][i];
            pred[i] = u;
          }
        }
      }
    }

    //An??lise do caminho m??nimo entre s e t
    System.out.printf("Dist??ncia entre %d e %d: %d   ", s,t,dist[t]);
    ArrayList<Integer> caminho = new ArrayList<Integer>();
    int aux = t;
    caminho.add(t);
    while(aux != s){
      aux = pred[aux];
      caminho.add(0,aux);
    }
    System.out.println("Caminho: " + caminho);

    long elapsed = System.currentTimeMillis() - start;
    System.out.println("\nTempo de execu????o: " + elapsed + " ms");
  }

  public void bellmanFord(int s, int t){
    long start = System.currentTimeMillis();
    int[] pred = new int[this.countNodes];
    int[] dist = new int[this.countNodes];

    for (int i = 0; i < this.countNodes; i++) {
      dist[i] = INF;
      pred[i] = -1;
    }
    dist[s] = 0;

    for (int i = 0; i < this.countNodes; i++) {
      for (int j = 0; j < this.countNodes; j++) {
        if(i!=j && this.adjMatrix[i][j]!=0) {
          if (dist[j] > dist[i]+this.adjMatrix[i][j]){
            dist[j] = dist[i] + this.adjMatrix[i][j];
            pred[j] = i;
          }
        }
      }
    }

    //An??lise do caminho m??nimo entre s e t
    System.out.printf("Dist??ncia entre %d e %d: %d   ", s,t,dist[t]);
    ArrayList<Integer> caminho = new ArrayList<Integer>();
    int aux = t;
    caminho.add(t);
    while(aux != s){
      aux = pred[aux];
      caminho.add(0,aux);
    }
    System.out.println("Caminho: " + caminho);

    long elapsed = System.currentTimeMillis() - start;
    System.out.println("\nTempo de execu????o: " + elapsed + " ms");
  }

//  public void bellmanFordMelhorado(int s, int t){
//    long start = System.currentTimeMillis();
//    int[] pred = new int[this.countNodes];
//    int[] dist = new int[this.countNodes];
//    boolean trocou;
//
//    for (int i = 0; i < this.countNodes; i++) {
//      dist[i] = INF;
//      pred[i] = -1;
//    }
//    dist[s] = 0;
//
//    for (int k = 0; k < this.countNodes; k++) {
//      trocou = false;
//      for (int i = 0; i < this.countNodes; i++) {
//        for (int j = 0; j < this.countNodes; j++) {
//          if (i != j && this.adjMatrix[i][j] != 0) {
//            if (dist[j] > dist[i] + this.adjMatrix[i][j]) {
//              dist[j] = dist[i] + this.adjMatrix[i][j];
//              pred[j] = i;
//              trocou = true;
//            }
//          }
//        }
//      }
//      if(!trocou){
//        break;
//      }
//    }
//
//
//    //An??lise do caminho m??nimo entre s e t
//    System.out.printf("Dist??ncia entre %d e %d: %d   ", s,t,dist[t]);
//    ArrayList<Integer> caminho = new ArrayList<Integer>();
//    int aux = t;
//    caminho.add(t);
//    while(aux != s){
//      aux = pred[aux];
//      caminho.add(0,aux);
//    }
//    System.out.println("Caminho: " + caminho);
//
//    long elapsed = System.currentTimeMillis() - start;
//    System.out.println("\nTempo de execu????o: " + elapsed + " ms");
//  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int[] matrix : this.adjMatrix) {
      for (int j = 0; j < this.adjMatrix.length; j++) {
        str.append(matrix[j]).append("\t");
      }
      str.append("\n");
    }
    return "Graph" + "\n" +
        "countNodes: " + countNodes + "\n" +
        "countEdges: " + countEdges + "\n\n" +
        str;
  }
}
