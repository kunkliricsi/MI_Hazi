/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

import java.util.ArrayList;

/**
 *
 * @author Kunkli Richárd
 */
public class NeuralNetworkHandler {
    NeuralNetwork network;
    
    ArrayList<Integer> layerSizes;
    ArrayList<ArrayList<Double>> outputList;
    ArrayList<ArrayList<Double>> inputList;
    ArrayList<ArrayList<Double>> expectList;
    ArrayList<ArrayList<Double>> errorList;
    
    ArrayList<ArrayList<Double>> ValidationInputList;
    ArrayList<ArrayList<Double>> ValidationExpectList;
    
    
    public NeuralNetworkHandler(){
        network = new NeuralNetwork(this);
        outputList = new ArrayList<>();
        inputList = new ArrayList<>();
        expectList = new ArrayList<>();
        errorList = new ArrayList<>();
        ValidationExpectList = new ArrayList<>();
        ValidationInputList = new ArrayList<>();
    }
    
    public void createNetwork(ArrayList<Integer> layerSizes, ArrayList<ArrayList<Double>> weightsAndBiases){
        this.layerSizes = layerSizes;
        ArrayList<Neuron> tempNeurons = new ArrayList<>();
        ArrayList<Double> tempWeights;
        Double tempBias;
        for (int i = 0; i < weightsAndBiases.size(); i++){
            tempWeights = weightsAndBiases.get(i);
            tempBias = tempWeights.remove(tempWeights.size() - 1);
            
            if (i > weightsAndBiases.size() - layerSizes.get(layerSizes.size() - 1) - 1)
                tempNeurons.add(new LastNeuron(tempWeights, tempBias));
            else
                tempNeurons.add(new Neuron(tempWeights, tempBias));
        }
        
        network.createLayers(tempNeurons, layerSizes);
    }
    
    public void setInput(ArrayList<ArrayList<Double>> inputs, ArrayList<ArrayList<Double>> expects){
        inputList = inputs;
        expectList = expects;
    }
    
    public void setValidation(ArrayList<ArrayList<Double>> inputs, ArrayList<ArrayList<Double>> expects){
        ValidationInputList = inputs;
        ValidationExpectList = expects;
    }
    
    public void calculateOutput(Integer inputIndex){
        if (inputIndex == null){
            for (ArrayList<Double> list : inputList){
                network.setInput(list);
                outputList.add(new ArrayList<>(network.calculateOutput()));
            }
        }
        else {
            network.setInput(inputList.get(inputIndex));
            outputList.add(new ArrayList<>(network.calculateOutput()));
        }
    }
    
    public void calculateError(Integer inputIndex){
        if (inputIndex == null)
            return;
        
        ArrayList<Double> tempList = new ArrayList<>();

        for (int j = 0; j < expectList.get(inputIndex).size(); j++)
            tempList.add(expectList.get(inputIndex).get(j) - outputList.get(inputIndex).get(j));

        errorList.add(new ArrayList<>(tempList));
        
    }
    
    public void epoch(Double mu){
        errorList.clear();
        outputList.clear();
        for (int i = 0; i < inputList.size(); i++){
            calculateOutput(i);
            calculateError(i);
            calculateDeltas(i);
            modifyWeightsAndBiases(mu);
        }
    }
    
    public void validate(){
        ArrayList<ArrayList<Double>> tempinputList = new ArrayList<>(inputList);
        ArrayList<ArrayList<Double>> tempexpectList = new ArrayList<>(expectList);
        ArrayList<ArrayList<Double>> temperrorList = new ArrayList<>(errorList);
        ArrayList<ArrayList<Double>> tempoutputList = new ArrayList<>(outputList);
        
        errorList.clear();
        outputList.clear();
        inputList = ValidationInputList;
        expectList = ValidationExpectList;
        
        for (int i = 0; i < inputList.size(); i++){
            calculateOutput(i);
            calculateError(i);
        }
        
        printAvarageSquaredError();
        
        inputList = tempinputList;
        expectList = tempexpectList;
        errorList = temperrorList;
        outputList = tempoutputList;
    }
    
    public void trainNetwork(ArrayList<Double> trainingInfo){
        for (int i = 0; i < trainingInfo.get(0); i++){
            epoch(trainingInfo.get(1));
            validate();
        }
    }
    
    public void calculateDeltas(Integer inputIndex){
        network.calculateDeltas(inputIndex);
    }
    
    public void modifyWeightsAndBiases(Double mu){
        network.modifyWeightsAndBiases(mu);
    }

    public void printOutput(){
        System.out.print(inputList.size() + "\n");
        
        for (ArrayList<Double> output : outputList){
            for (int i = 0; i < output.size() - 1; i++)
                System.out.print(output.get(i) + ",");
            
            System.out.print(output.get(output.size() - 1));
            
            System.out.print("\n");
        }
    }
    
    public void printLayers(){
         for(int i = 0; i < layerSizes.size() - 1; i++)
            System.out.print(layerSizes.get(i) + ",");
        
        System.out.print(layerSizes.get(layerSizes.size() - 1) + "\n");
    }
    
    public void printNetwork(){
        printLayers();
        network.printNetwork();
    }
    
    public void printDerivedNetwork(){
        printLayers();
        network.printDerivedNetwork();
    }
    
    public void printAvarageSquaredError(){
        Double sum = 0.0;
        for (int i = 0; i < errorList.size(); i++){
            for (int j = 0; j < errorList.get(i).size(); j++)
                sum += Math.pow(errorList.get(i).get(j), 2);
        }
        
        sum /= (errorList.size() * layerSizes.get(layerSizes.size() - 1));
        
        System.out.print(sum + "\n");
    }
}
