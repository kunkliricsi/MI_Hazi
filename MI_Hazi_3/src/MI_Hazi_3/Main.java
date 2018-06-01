/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mi_hazi_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 *
 * @author Kunkli Richárd
 */
public class Main {

    static Random r = new Random();
    
    public static void main(String[] args) throws IOException {
        
        // Initializing Readers and Constants
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = 4;
        int steps = 1800;
        Double alpha = 0.0012;
        Double beta = 0.0;
        
        // Reading and formatting first row
        String[] firstRow = br.readLine().split("\t");
        int numberOfReviews = Integer.parseInt(firstRow[0]);
        int numberOfUsers = Integer.parseInt(firstRow[1]);
        int numberOfFilms = Integer.parseInt(firstRow[2]);
        
        // Creating E, P and Q matrixes
        Double[][] E = new Double[numberOfUsers][numberOfFilms];
        Double[][] P = new Double[numberOfUsers][K];
        Double[][] Qt = new Double[numberOfFilms][K];
        randomize(P);
        randomize(Qt);
        
        // Reading reviews
        int[][] R = new int[numberOfUsers][numberOfFilms];
        for (int i = 0; i < numberOfReviews; i++) {
            String[] splitter = br.readLine().split("\t");
            R[Integer.parseInt(splitter[0])][Integer.parseInt(splitter[1])] = Integer.parseInt(splitter[2]);
        }
        
        // Calculating predictions
        for (int step = 0; step < steps; step++)
            for (int i = 0; i < R.length; i++)
                for (int j = 0; j < R[i].length; j++){
                    if (R[i][j] <= 0)
                        continue;
                    
                    Double e = R[i][j] - multiply(P[i], Qt[j]);
                    
                    for (int k = 0; k < K; k++){
                        P[i][k] = P[i][k] + alpha * (2 * e * Qt[j][k] - beta * P[i][k]);
                        Qt[j][k] = Qt[j][k] + alpha * (2 * e * P[i][k] - beta * Qt[j][k]);
                    }
                }
        
        // Calculating output
        double[][] output = new double[P.length][Qt.length];
        for (int i = 0; i < P.length; i++)
            for (int j = 0; j < Qt.length; j++){
                if (R[i][j] != 0)
                    output[i][j] = Double.NaN;
                else{
                    for (int k = 0; k < K; k++)
                        output[i][j] += P[i][k] * Qt[j][k]; 
                }
            }
            
        // Getting top 10 per user
        ArrayList<Double> top;
        ArrayList<Integer> topID;
        for (int i = 0; i < numberOfUsers; i++){
            top = new ArrayList<>();
            topID = new ArrayList<>();
            
            for (int m = 0; m < 10; m++){
                Double max = 0.0;
                int id = 0;
                
                for (int j = 0; j < numberOfFilms; j++)
                    if (output[i][j] > max && !top.contains(output[i][j])){
                        id = j;
                        max = output[i][j];
                    }
                
                topID.add(id);
                top.add(max);
            }
            
            for (int m = 0; m < 10; m++){
                System.out.print(topID.get(m));
                if (m != 9)
                    System.out.print("\t");
            }
            
            System.out.print("\n");
        }       
    }
    
    public static Double multiply(Double[] a, Double[] b){
        Double sum = 0.0;
        for (int i = 0; i < a.length; i++)
            sum += a[i] * b[i];
        
        return sum;
    }
 
    public static void randomize(Double[][] matrix){
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = r.nextDouble();
    }   
}
