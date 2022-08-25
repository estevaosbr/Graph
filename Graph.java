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

  public ArrayList buscaEmLargura(Graph graph, int s){
    int[] descobertos = new int[graph.countNodes];
    for (int i = 0; i < graph.countNodes; i++){
      descobertos[i] = 0;
    }

    descobertos[s] = 1;
    ArrayList q = new ArrayList();
    ArrayList r = new ArrayList();
    q.add(s);
    r.add(s);

    while (q.isEmpty()!=true){
      int u = (int)q.remove(0);
      for (int j = 0 ; j < graph.countNodes ; j++){
        if (adjMatrix[u][j] == 1){
          if (descobertos[j] == 0) {
            q.add(j);
            r.add(j);
            descobertos[j] = 1;
          }
        }
      }
    }
    System.out.println(r);
    return r;
  }

    public boolean isConex(Graph graph){
        ArrayList array = buscaEmLargura(graph,0);
        if (array.size() == graph.size()){
            return true;
        }
        else{
            return false;
        }
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
