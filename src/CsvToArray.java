import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * It is a class for conversion of CSV file to 2D integer binary graph
 */
public class CsvToArray {
    /**
     *  Convert the CSV file into a 2D array binary graph
     *
     * @param filePath the target CSV file path
     * @return binary graph with type int[][]
     */
    public static int[][] convert(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error in converting CSV file to 2D array : " + e.getMessage());
        }

        String graphString = content.toString(); // convert the csv file to string

        // Convert the string into 2D array for graph interpretation
        List<String[]> rows = new ArrayList<>();
        for (String line : graphString.split("\n")) {
            rows.add(line.split(", "));
        }

        int[][] graph = new int[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            graph[i] = Arrays.stream(rows.get(i)).mapToInt(Integer::parseInt).toArray();
        }
        return graph;
    }
}