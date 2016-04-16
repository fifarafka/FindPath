import java.io.*;
import java.util.Scanner;

public class FindPath {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        Graph graph = new Graph(parser.getNodeList());
        graph.findPath("Fabryczna","Czarnowiejska");
        Scanner console = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter your street source in Cracow");
        String source = console.nextLine();
        System.out.println("Enter your street target in Cracow");
        String target = console.nextLine();
        graph.findPath(source, target);
    }
}