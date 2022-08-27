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

        System.out.println(g1.buscaEmLargura(4));
        System.out.println(g1.isConex());

        Graph g2 = new Graph(8);
        g2.addEdgeUnoriented(2,1,1);
        g2.addEdgeUnoriented(1,6,1);
        g2.addEdgeUnoriented(6,7,1);
        g2.addEdgeUnoriented(7,0,1);
        g2.addEdgeUnoriented(0,5,1);
        g2.addEdgeUnoriented(6,3,1);
        g2.addEdgeUnoriented(3,4,1);

        System.out.println(g2.buscaEmProfundidade(2));

      
    }
}