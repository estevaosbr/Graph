public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
      Graph g1 = new Graph(8);
      g1.addEdgeUnoriented(0,4,1);
      g1.addEdgeUnoriented(0,6,1);
      g1.addEdgeUnoriented(4,3,1);
      g1.addEdgeUnoriented(4,5,1);
      g1.addEdgeUnoriented(3,7,1);
      g1.addEdgeUnoriented(3,2,1);
      g1.addEdgeUnoriented(6,1,1);

      g1.buscaEmLargura(g1,4);

      
    }
}