/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsimpleai.tspdemo;

import java.io.File;
import java.util.Scanner;

/**
 * Represents the adjacency matrix for the cities..
 * @author Ragha
 */
public class CostMatrix
{
    private double cost[][];

    private CostMatrix()
    {
        
    }

    /**
     * Loads cost matrix for a given tsp file
     * @throws Exception if file 'F' is invalid or doesnt confirm to .tsp format
     */
    public static CostMatrix getCostMatrix(File f) throws Exception
    {
        CostMatrix matrix = new CostMatrix();

        Scanner sc = null;
        try
        {
            sc = new Scanner(f);
            sc.nextLine();
            int numCities = Integer.parseInt(sc.nextLine());
            matrix.cost = new double[numCities][numCities];
            sc.nextLine();

            int row = 0;
            while(sc.hasNext())
            {
                String line = sc.nextLine();
                String arr[] = line.split(" ");
                for(int col = 0; col < arr.length; col++)
                    matrix.cost[row][col] = Double.parseDouble(arr[col]);
                row++;
            }

            return matrix;
        }
        catch(Exception e)
        {
            throw new Exception("Illegal file format");
        }
        finally
        {
            if(sc != null)
                sc.close();
        }
    }

    public double getCost(int row, int col)
    {
        return cost[row][col];
    }

    public double[][] getCostMatrix()
    {
        return cost;
    }

    public int getLength()
    {
        return cost.length;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer("Cost Matrix: \n");
        for(int row = 0; row < cost.length; row++)
        {
            for(int col = 0; col < cost.length; col++)
            {
                sb.append(cost[row][col] + " ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
