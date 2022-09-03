import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

//        aula 26/08
//        Graph g1 = new Graph(8);
//        g1.addEdgeUnoriented(0,4,1);
//        g1.addEdgeUnoriented(0,6,1);
//        g1.addEdgeUnoriented(4,3,1);
//        g1.addEdgeUnoriented(4,5,1);
//        g1.addEdgeUnoriented(3,7,1);
//        g1.addEdgeUnoriented(3,2,1);
//        g1.addEdgeUnoriented(6,1,1);
//
//        System.out.println(g1.buscaEmLargura(4));
//        System.out.println(g1.isConex());
//
//        Graph g2 = new Graph(8);
//        g2.addEdgeUnoriented(2,1,1);
//        g2.addEdgeUnoriented(1,6,1);
//        g2.addEdgeUnoriented(6,7,1);
//        g2.addEdgeUnoriented(7,0,1);
//        g2.addEdgeUnoriented(0,5,1);
//        g2.addEdgeUnoriented(6,3,1);
//        g2.addEdgeUnoriented(3,4,1);
//
//        System.out.println(g2.buscaEmProfundidade(2));
//        System.out.println(g2.dfs_rec(2));


//        AULA 31/08
//        Graph app = new Graph("graphteste.txt");
//        System.out.println(app);
//        System.out.println(app.ord_top());
//
//        Graph graph = new Graph("graph.txt");
//        System.out.println(graph);
//        System.out.println(graph.ord_top());
//        System.out.println(graph.isConex());

//        AULA 02/09
        Graph graph = new Graph(6);
        graph.addEdgeUnoriented(0,1,1);
        graph.addEdgeUnoriented(1,4,1);
        graph.addEdgeUnoriented(0,4,1);
        graph.addEdgeUnoriented(3,5,1);
        System.out.println(graph);
        int[] desc = graph.connected_comp();
        for (int i : desc) {
            System.out.print(desc[i]+" ");
        }

        Graph a = new Graph("a.txt");
        System.out.println(a);
        System.out.println(a.has_cycle_oriented());


    }
}