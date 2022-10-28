package src;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String fileName;
        int option;

        do {

            System.out.println("\n______ MENU ______\n");
            System.out.println("[1] - Caminho Mínimo");
            System.out.println("[2] - Labirinto");
            System.out.println("[0] - Sair");
            System.out.println("____________");
            System.out.print("Digite a opção desejada = ");

            option = scan.nextInt();

            switch (option) {
                case 1:
                    fileName = Selec_arq_cm();
                    if (fileName != null) {
                        Scanner scanner = new Scanner(System.in);
                        int origem, destino;
                        System.out.println("Digite a origem: ");
                        origem = scanner.nextInt();
                        System.out.println("Digite o destino: ");
                        destino = scanner.nextInt();
                        new GraphList(fileName).bellmanFordMelhorado(origem,destino);
                    }
                    break;
                case 2:
                    fileName = Selec_arq_lab();
                    if (fileName != null) {
                        new Labirinto(fileName);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("!!! Opção Inválida !!! - Tente novamente");
                    break;
            }

        } while (option != 0);
        scan.close();

    }

    public static String Selec_arq_lab() {

        String fileName;
        int option;
        Scanner scan = new Scanner(System.in);

        System.out.println("\nArquivos disponíveis :");
        System.out.println("[1] - toy.txt");
        System.out.println("[2] - maze3.txt");
        System.out.println("[3] - maze10.txt");
        System.out.println("[4] - maze20.txt");
        System.out.println("[5] - maze30.txt");
        System.out.println("[6] - maze40.txt");
        System.out.println("[7] - maze50.txt");
        System.out.println("[0] - Voltar ao menu");

        System.out.print("\nDigite a opção: ");
        option = scan.nextInt();

        if (option == 1) {
            fileName = "maze\\toy.txt";
            return fileName;
        } else if (option == 2) {
            fileName = "maze\\maze3.txt";
            return fileName;
        } else if (option == 3) {
            fileName = "maze\\maze10.txt";
            return fileName;
        } else if (option == 4) {
            fileName = "maze\\maze20.txt";
            return fileName;
        } else if (option == 5) {
            fileName = "maze\\maze30.txt";
            return fileName;
        } else if (option == 6) {
            fileName = "maze\\maze40.txt";
            return fileName;
        } else if (option == 7) {
            fileName = "maze\\maze50.txt";
            return fileName;
        } else if (option == 0) {
            return null;
        }
        return null;
    }

    public static String Selec_arq_cm(){
        String fileName;
        int option;
        Scanner scan = new Scanner(System.in);

        System.out.println("\nArquivos disponíveis :");
        System.out.println("[1] - toy.txt");
        System.out.println("[2] - facebook_combined.txt");
        System.out.println("[3] - rg300_4730.txt");
        System.out.println("[4] - rome99c.txt");
        System.out.println("[5] - USA-road-dt.DC.txt");
        System.out.println("[6] - USA-road-dt.NY.txt");
        System.out.println("[0] - Voltar ao menu");

        System.out.print("\nDigite a opção: ");
        option = scan.nextInt();

        if (option == 1) {
            fileName = "cm/toy.txt";
            return fileName;
        } else if (option == 2) {
            fileName = "cm/facebook_combined.txt";
            return fileName;
        } else if (option == 3) {
            fileName = "cm/rg300_4730.txt";
            return fileName;
        } else if (option == 4) {
            fileName = "cm/rome99c.txt";
            return fileName;
        } else if (option == 5) {
            fileName = "cm/USA-road-dt.DC.txt";
            return fileName;
        } else if (option == 6) {
            fileName = "cm/USA-road-dt.NY.txt";
            return fileName;
        } else if (option == 0) {
            return null;
        }
        return null;

    }

}