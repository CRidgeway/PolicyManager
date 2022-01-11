/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package policymanager;

/**
 *
 * @author Carter Ridgeway (v8265314)
 */
public class Summary {
    //instance variables
    int[][] summary;
    
    // constructors
    Summary()
    {
        summary = new int[12][4];
    }
    
    Summary(int i, int j)
    {
        summary = new int[i][j];
    }
    
    // accessor methods
    int getVal(int i, int j)
    {
        int val = summary[i][j];
        
        return val;
    }
    
    // mutator methods
    void setVal(int i, int j, int val)
    {
        summary[i][j] = val;
    }
   
}
