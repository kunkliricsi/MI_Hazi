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
public class NeuralNetwork {
    NeuralNetworkHandler handler;
    ArrayList<NeuronLayer> layers;
    
    public NeuralNetwork(NeuralNetworkHandler n){
        handler = n;
        layers = new ArrayList<>();
    }
    
    public void createLayers(ArrayList<Neuron> neurons, ArrayList<Integer> layerInfo){
        NeuronLayer tempLayer = new NeuronLayer(this);
        for (int i = 1; i < layerInfo.size(); i++){
            tempLayer = new NeuronLayer(this);
            
            for (int j = 0; j < layerInfo.get(i); j++){
                neurons.get(0).setLayer(tempLayer);
                tempLayer.addNeuron(neurons.remove(0));
            }
            
            layers.add(tempLayer);
        }
        
        for (int i = 0; i < layers.size() - 1; i++) {
            layers.get(i).setAfter(layers.get(i + 1));
            layers.get(i + 1).setBefore(layers.get(i));
        }
        
    }
    
    public void setInput(ArrayList<Double> input){
        layers.get(0).setInputs(input);
    }
    
    public ArrayList<Double> calculateOutput(){
        for(NeuronLayer l : layers){
            l.calculateOutput();
            l.sendInputs();
        }
        
        return layers.get(layers.size() - 1).outputs;
    }
    
    public void calculateDeltas(Integer inputIndex){
        for (int i = layers.size() - 1; i >= 0; i--){
            layers.get(i).calculateDelta(inputIndex);
        }
    }
    
    public void modifyWeightsAndBiases(Double mu){
        for (NeuronLayer nl : layers)
            nl.modifyWeightsAndBiases(mu);
    }
    
    public void printNetwork(){
        for (NeuronLayer n : layers)
            n.printLayer();
    }
    
    public void printDerivedNetwork(){
        for (NeuronLayer n : layers)
            n.printDerivedLayer();
    }
}
