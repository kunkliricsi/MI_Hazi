package mlp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class NNSolutionOne {
    
    public static void main(String[] args) throws IOException {
        
        // Initializing Initializers and non-Initializers
        NeuralNetworkHandler nnh = new NeuralNetworkHandler();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Reading and formatting input
        String s = br.readLine();
        String[] inpSt = s.split(",");
        ArrayList<Integer> inpList = new ArrayList<>();
        for (String a : inpSt) {
            inpList.add(Integer.parseInt(a));
        }
        
        // Creating random weights and biases
        ArrayList<ArrayList<Double>> wb = new ArrayList<>();
        for (int i = 1; i < inpList.size(); i++){
            int neuronCounter = 0;
            for (int t = 1; t < i; t++)
                neuronCounter += inpList.get(t);
            
            for (int j = neuronCounter; j < inpList.get(i) + neuronCounter; j++){
                wb.add(new ArrayList<>());
                
                for (int k = 0; k < inpList.get(i - 1); k++)
                    wb.get(j).add(new Random().nextGaussian() * 0.1);
                
                wb.get(j).add(0.0);
            }
        }
        
        // Initializing Network's layers then adding weights and biases
        nnh.createNetwork(inpList, wb);
        
        // Writing out the network on the standart output
        nnh.printNetwork();
    }
}
