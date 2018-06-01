/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Kunkli Richárd
 */
public class NNSolutionTwo {
    
    public static void main(String[] args) throws IOException {
        
        // Initializing
        NeuralNetworkHandler nnh = new NeuralNetworkHandler();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Reading layer info
        String[] layerList = br.readLine().split(",");
        
        // Formatting layer info
        ArrayList<Integer> layerArray = new ArrayList<>();
        for (String s : layerList)
            layerArray.add(Integer.parseInt(s));
        
        // Counting how many neurons to read
        int numberOfNeurons = 0;
        for (int i = 1; i < layerArray.size(); i++)
            numberOfNeurons += layerArray.get(i);
        
        // Reading neuron info
        ArrayList<ArrayList<Double>> weightsAndBiases = new ArrayList<>();
        for (int i = 0; i < numberOfNeurons; i++){
            String[] neuronList = br.readLine().split(",");
            
            ArrayList<Double> tempList = new ArrayList<>();
            for (String s : neuronList)
                tempList.add(Double.parseDouble(s));
            
            weightsAndBiases.add(tempList);
        }
        
        // Reading input info
        int numberOfInputs = Integer.parseInt(br.readLine());
        
        // Reading inputs
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();
        for (int i = 0; i < numberOfInputs; i++) {
            String[] inputList = br.readLine().split(",");
            
            ArrayList<Double> tempList = new ArrayList<>();
            for (String s : inputList)
                tempList.add(Double.parseDouble(s));
            
            inputs.add(tempList);
        }
        
        // Creating Neural Network
        nnh.createNetwork(layerArray, weightsAndBiases);
        
        // Setting up inputs
        nnh.setInput(inputs, null);

        // Calculating the output
        nnh.calculateOutput(null);
        
        // Writing output to standard output
        nnh.printOutput();
            
        
    }
}
