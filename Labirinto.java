import java.io.*;

public class Labirinto {

    int s,e;

    public Labirinto(String fileName) throws IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(fileName));
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        lnr.skip(Long.MAX_VALUE);
        int linhas = lnr.getLineNumber();
        String line;
        line = reader.readLine();
        int colunas = line.length();

        GraphList graphList = new GraphList(linhas * colunas);
        char[][] charMatrix = new char[linhas][colunas];

        int i = 0;
        charMatrix[i] = line.toCharArray();
        i = i + 1;
        while (reader.ready()) {
            line = reader.readLine();
            charMatrix[i] = line.toCharArray();
            i++;
        }

        for (char[] matrix : charMatrix) {
            for (char c : matrix) {
                System.out.printf("%c", c);
            }
            System.out.println();
        }
        reader.close();

        for (int j = 0; j < linhas; j++) {
            for (int k = 0; k < colunas; k++) {
                try {
                    if (charMatrix[j][k] == 'S' || charMatrix[j][k] == 'E' || charMatrix[j][k] == ' ') {
                        if (charMatrix[j][k] == 'S') {
                            s = (j * colunas + k);
                        }
                        if (charMatrix[j][k] == 'E') {
                            e = (j * colunas + k);
                        }
                        try {
                            if (charMatrix[j][k + 1] == 'S' || charMatrix[j][k + 1] == 'E' || charMatrix[j][k + 1] == ' ') {
                                graphList.addEdgeUnoriented((j * colunas + k), (j * colunas + (k + 1)), 1);
                            }
                        } catch (Exception e) {
                            continue;
                        }
                        try {
                            if (charMatrix[j + 1][k] == 'S' || charMatrix[j + 1][k] == 'E' || charMatrix[j + 1][k] == ' ') {
                                graphList.addEdgeUnoriented((j * colunas + k), ((j + 1) * colunas + k), 1);
                            }
                        } catch (Exception ignored) {
                        }

                    }
                } catch (Exception ignored) {
                }
            }
        }
        System.out.println(graphList);
        graphList.bellmanFordMelhorado(s,e);
    }

}
