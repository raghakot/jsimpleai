package jsimpleai.demo.tsp;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the adjacency matrix for the cities..
 */
public class CostMatrix {
    private double cost[][];

    private CostMatrix() {
    }

    /**
     * Loads cost matrix for a given tsp file
     *
     * @throws Exception if file 'F' is invalid or doesnt confirm to .tsp format
     */
    public static CostMatrix getCostMatrix(final String problemFilePath) throws IOException {
        final CostMatrix matrix = new CostMatrix();

        try (final Scanner scanner = new Scanner(new File(problemFilePath))) {
            scanner.nextLine();
            int numCities = Integer.parseInt(scanner.nextLine());
            matrix.cost = new double[numCities][numCities];
            scanner.nextLine();

            int row = 0;
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                final String arr[] = line.split(" ");
                for (int col = 0; col < arr.length; col++)
                    matrix.cost[row][col] = Double.parseDouble(arr[col]);
                row++;
            }

            return matrix;
        }
    }

    public double getCost(int row, int col) {
        return cost[row][col];
    }

    public double[][] getCostMatrix() {
        return cost;
    }

    public int getLength() {
        return cost.length;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cost Matrix: \n");
        for (int row = 0; row < cost.length; row++) {
            for (int col = 0; col < cost.length; col++) {
                sb.append(cost[row][col]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
