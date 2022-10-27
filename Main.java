import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        GraphMatrix graphMatrix = new GraphMatrix("cm\\facebook_combined.txt");
        graphMatrix.bellmanFord(0,2620);

        GraphList graphList = new GraphList("cm\\facebook_combined.txt");
        graphList.bellmanFord(0,2620);

    }
}