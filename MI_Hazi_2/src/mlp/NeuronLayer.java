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
public class NeuronLayer {
    NeuralNetwork network;
    NeuronLayer before, after;
    ArrayList<Neuron> neurons;
    ArrayList<Double> inputs;
    ArrayList<Double> outputs;
    
    public NeuronLayer(NeuralNetwork n){
        network = n;
        neurons = new ArrayList<>();
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        before = null;
        after = null;
    }
    
    public ArrayList<Neuron> getNeurons(){
        return neurons;
    }
    
    public NeuronLayer(ArrayList<Neuron> n){
        neurons = n;
    }
    
    public void addNeuron(Neuron n){
        neurons.add(n);
    }
    
    public void setBefore(NeuronLayer b){
        before = b;
    }
    
    public void setAfter(NeuronLayer a){
        after = a;
    }
    
    public void setInputs(ArrayList<Double> i){
        inputs = i;
    }
    
    public void sendInputs(){
        if (after != null)
            after.setInputs(outputs);
    }
    
    public void calculateOutput(){
        if (!neurons.isEmpty()){
            outputs.clear();
            for(Neuron n : neurons){
                outputs.add(n.calculateOutput(inputs));
            }
        }
    }
    
    public void calculateDelta(Integer inputIndex){
        int idx = 0;
        for (Neuron n : neurons){
            if (after == null)
                n.getDelta(idx, inputIndex);
            
            n.calculateDelta(inputs, idx++);
        }
    }
    
    public void modifyWeightsAndBiases(Double mu){
        for (Neuron n : neurons)
            n.modidfyWeightsAndBias(mu);
    }
    
    public void printLayer(){
        for (Neuron n : neurons)
            n.printNeuron();
    }
    
    public void printDerivedLayer(){
        for (Neuron n : neurons)
            n.printDerivedNeuron();
    }
}
