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
public class NNSolutionFive {
    public static void main(String[] args) throws IOException {
        
        // Initializing
        NeuralNetworkHandler nnh = new NeuralNetworkHandler();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Reading training info
        String[] trainingList = br.readLine().split(",");
        
        // Formatting training info
        ArrayList<Double> trainingArray = new ArrayList<>();
        for (String s : trainingList)
            trainingArray.add(Double.parseDouble(s));

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
        
        // Reading inputs and expections
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();
        ArrayList<ArrayList<Double>> expects = new ArrayList<>();
        for (int i = 0; i < Math.floor(numberOfInputs * trainingArray.get(2)); i++) {
            String[] inputList = br.readLine().split(",");
            
            ArrayList<Double> tempInputList = new ArrayList<>();
            ArrayList<Double> tempExpectedList = new ArrayList<>();
            
            for (int j = 0; j < layerArray.get(0); j++)
                tempInputList.add(Double.parseDouble(inputList[j]));
            
            for (int j = layerArray.get(0); j < layerArray.get(0) + layerArray.get(layerArray.size() - 1); j++)
                tempExpectedList.add(Double.parseDouble(inputList[j]));
            
            inputs.add(tempInputList);
            expects.add(tempExpectedList);
        }
        
        // Reading inputs and expections
        ArrayList<ArrayList<Double>> Validateinputs = new ArrayList<>();
        ArrayList<ArrayList<Double>> Validateexpects = new ArrayList<>();
        for (int i = 0; i < numberOfInputs - Math.floor(numberOfInputs * trainingArray.get(2)); i++) {
            String[] inputList = br.readLine().split(",");
            
            ArrayList<Double> tempInputList = new ArrayList<>();
            ArrayList<Double> tempExpectedList = new ArrayList<>();
            
            for (int j = 0; j < layerArray.get(0); j++)
                tempInputList.add(Double.parseDouble(inputList[j]));
            
            for (int j = layerArray.get(0); j < layerArray.get(0) + layerArray.get(layerArray.size() - 1); j++)
                tempExpectedList.add(Double.parseDouble(inputList[j]));
            
            Validateinputs.add(tempInputList);
            Validateexpects.add(tempExpectedList);
        }
        
        // Creating Neural Network
        nnh.createNetwork(layerArray, weightsAndBiases);
        
        // Setting up inputs
        nnh.setInput(inputs, expects);
        
        // Settings up validation
        nnh.setValidation(Validateinputs, Validateexpects);
        
        // Training the network
        nnh.trainNetwork(trainingArray);
        
        // Writing out the network to standard output
        nnh.printNetwork();
    }
}
