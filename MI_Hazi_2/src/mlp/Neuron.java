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
public class Neuron {
    NeuronLayer layer;
    
    ArrayList<Double> weights;
    ArrayList<Double> derivedWeights;
    
    Double bias;
    Double derivedBias;
    
    Double delta;
    Double sum;
    
    public Neuron(ArrayList<Double> w, Double b){
        weights = w;
        derivedWeights = new ArrayList<>();
        bias = b;
    }
    
    public Neuron(Neuron n){
        weights = n.weights;
        derivedWeights = new ArrayList<>();
        bias = n.bias;
        layer = n.layer;
    }
    
    public void setLayer(NeuronLayer l){
        layer = l;
    }
    
    public Double ReLu(Double x){
        return Math.max(0, x);
    }
    
    public Double ReluDerivate(){
        if (ReLu(sum) > 0)
            return 1.0;
        else
            return 0.0;
    }
    
    public void calculateDelta(ArrayList<Double> input, int index){
        Double deltaFirst = 0.0;
        for (int i = 0; i < layer.after.neurons.size(); i++)
            deltaFirst += layer.after.neurons.get(i).weights.get(index) * layer.after.neurons.get(i).delta;
        
        delta = deltaFirst * ReluDerivate();
        
        derivedBias = delta;
                
        derivedWeights.clear();
        for (Double d : input)
            derivedWeights.add(delta * d);
    }
    
    public void modidfyWeightsAndBias(Double mu){
        for (int i = 0; i < weights.size(); i++){
            Double modif = weights.get(i) + derivedWeights.get(i) * 2 * mu;
            weights.set(i, modif);
        }
        
        bias += derivedBias * 2 * mu;
    }
    
    public Double getDelta(int index, Integer inputIndex){
        return delta;
    }
    
    public Double calculateOutput(ArrayList<Double> input){
        sum = 0.0;
        for(int i = 0; i < input.size(); i++){
            sum += weights.get(i) * input.get(i);
        }
        sum += bias;
        
        return ReLu(sum);
    }
    
    public void printDerivedNeuron(){
        for (Double d : derivedWeights)
            System.out.print(d + ",");
        
        System.out.print(derivedBias + "\n");
    }
    
    public void printNeuron(){
        for (Double w : weights)
            System.out.print(w + ",");
        
        System.out.print(bias + "\n");
    }
}
