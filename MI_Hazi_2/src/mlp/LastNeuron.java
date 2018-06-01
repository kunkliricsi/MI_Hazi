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
public class LastNeuron extends Neuron {
    
    public LastNeuron(ArrayList<Double> w, Double b){
        super(w, b);
    }
    
    public LastNeuron(Neuron n){
        super(n);
    }
    
    @Override
    public void calculateDelta(ArrayList<Double> input, int index){
        derivedBias = delta;        
        
        derivedWeights.clear();
        for (Double d : input)
            derivedWeights.add(delta * d);
    }
    
    @Override
    public Double getDelta(int index, Integer inputIndex){
        if (inputIndex == null)
            delta = 1.0;
        else {
            delta = layer.network.handler.errorList.get(inputIndex).get(index);
        }
        
        return delta;
    }
    
    @Override
    public Double ReLu(Double x){
        return x;
    }
    
    @Override    
    public Double ReluDerivate(){
        return 1.0;
    }
    
}
