/*
 * 
 * 
 * 
 */
package policymanager;

/**
 *
 * @author Carter Ridgeway (v8265314)
 */
public class Policy {
    //instance variables
    String clientName;
    String refNo;
    int noGadget;
    int mostExpGadVal;
    int gadLimit;
    int excess;
    String payTerm;
    char payTermChar;
    int premium;
    String date;
    
    //Default constructor
    Policy()
    {
        clientName = "";
        refNo = "";
        noGadget = -1;
        mostExpGadVal = -1;
        gadLimit = -1;
        excess = -1;
        payTerm = "";
        payTermChar = 'z';
        premium = -1;
        date = "";
    }
    
    //Main constructor
    Policy(String cN, String rN, int nG, int mEGV, int gL, int e, String pT, char pTC, int p, String d)
    {
        clientName = cN;
        refNo = rN;
        noGadget = nG;
        mostExpGadVal = mEGV;
        gadLimit = gL;
        excess = e;
        payTerm = pT;
        payTermChar = pTC;
        premium = p;
        date = d;
    }
    
    // accessor methods
    String getClientName()
    {
        return clientName;
    }
    
    String getRefNo()
    {
        
        return refNo;
    }
    
    int getNoGadget()
    {
        
        return noGadget;
    }
    
    int getMostExpGadVal()
    {
        
        return mostExpGadVal;
    }
    
    int getGadLimit()
    {
        
        return gadLimit;
    }
    
    int getExcess()
    {
        
        return excess;
    }
    
    String getPayTerm()
    {
        
        return payTerm;
    }
    
    char getPayTermChar()
    {
        
        return payTermChar;
    }
    
    int getPremium()
    {
        
        return premium;
    }
    
    String getDate()
    {
        
        return date;
    }
        
    // mutator methods
    void setClientName(String cN)
    {
        clientName = cN;
    }
    
    void setRefNo(String rN)
    {
        
        refNo = rN;
    }
    
    void setNoGadget(int nG)
    {
        
        noGadget = nG;
    }
    
    void setMostExpGadVal(int mEGV)
    {
        
        mostExpGadVal = mEGV;
    }
    
    void setGadLimit(int gL)
    {
        
        gadLimit = gL;
    }
    
    void setExcess(int e)
    {
        
        excess = e;
    }
    
    void setPayTerm(String pT)
    {
        
        payTerm = pT;
    }
    
    void setPayTermChar(char pTC)
    {
        
        payTermChar = pTC;
    }
    
    void setPremium(int p)
    {
        
        premium = p;
    }
    
    void setDate(String d)
    {
        
        date = d;
    }
    
    // toString() method to reflect the state of the object
}
