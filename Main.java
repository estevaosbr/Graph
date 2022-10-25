import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        GraphList graphList = new GraphList("cm\\toy.txt");
        //System.out.println(graphList);
        //graphList.dijkstra(0,3);
        graphList.bellmanFord(0,4);

    }
}