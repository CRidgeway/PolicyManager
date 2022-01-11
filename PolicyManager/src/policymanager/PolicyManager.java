/*
 * Gadget Insurance Policy Prototype
 * Simple insurance premium calculator
 * 
 */
package policymanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 *
 * @author Carter Ridgeway (v8265314)
 */
public class PolicyManager {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

        Scanner scanner = new Scanner(System.in);
        
    	int menuOption = 5;
        
        while (menuOption > 0)
        {
            menuOption = menu();
            
            switch (menuOption)
            {
                case 1:
                    System.out.println("Enter new Policy");
                    newPolicy();
                    menuOption = 5;
                    break;

                case 2:
                    System.out.println("Display Summary of Policies");
                    displayYearSummary();
                    menuOption = 5;
                    break;

                case 3:
                    System.out.println("Display Summary of Policies for Selected Month");
                    displayMonthSummary();
                    menuOption = 5;
                    break;

                case 4:
                    System.out.println("Find and display Policy");
                    findPolicy();
                    menuOption = 5;
                    break;

                default: 
                    System.out.println("Invalid option!");
                    menuOption = 5;
                    break;
            } 
            
        }
        
        System.out.println("Session Ended");
    }
    
    //This is a menu.
    public static int menu()
    {
        Scanner input = new Scanner(System.in);
        
        int menuOption = -1;
        
        while(menuOption < 0 || menuOption > 4)
        { 
            System.out.println(
                  "\nInput a number to select an option from the menu."
                + "\n1. Enter new Policy" 
                + "\n2. Display Summary of Policies"
                + "\n3. Display Summary of Policies for Selected Month"
                + "\n4. Find and display Policy"
                + "\n0. Exit");
            
            menuOption = input.nextInt();
        }
                 
        return menuOption;
    }
    
    //Obtains all data, the displays the policy and writes to a file.
    public static void newPolicy()
    {
        Policy newPolicy = new Policy();
        newPolicy.setClientName(getName());
        newPolicy.setRefNo(getRefNo());
        newPolicy.setNoGadget(getNoGadget());
        newPolicy.setMostExpGadVal(getMostExpGadVal());
        newPolicy.setGadLimit(formatGadLimit(newPolicy.getMostExpGadVal()));
        newPolicy.setExcess(getExcess());
        newPolicy.setPayTerm(getPayTerm());
        newPolicy.setPayTermChar(getPayTermChar(newPolicy.getPayTerm(), 
                                 newPolicy.getNoGadget(), newPolicy.getMostExpGadVal()));
        newPolicy.setPremium(calcPremium(newPolicy.getNoGadget(), newPolicy.getMostExpGadVal(), 
                             newPolicy.getExcess(), newPolicy.getPayTerm()));
        newPolicy.setDate(getDate());
           
     
       displayPolicy(newPolicy.getDate(), newPolicy.getRefNo(), newPolicy.getNoGadget(), 
                     newPolicy.getMostExpGadVal(), newPolicy.getGadLimit(), newPolicy.getExcess(),
                     newPolicy.getPremium(), newPolicy.getPayTerm(), newPolicy.getClientName());
       
       policyRegister(newPolicy.getRefNo(), newPolicy.getNoGadget(), newPolicy.getMostExpGadVal(), 
                      newPolicy.getExcess(), newPolicy.getPremium(), newPolicy.getPayTermChar(), 
                      newPolicy.getClientName(), newPolicy.getDate());
        
    }
    
    //Obtains and validates the client's name.
    public static String getName()
    {
        Scanner input = new Scanner(System.in);
        
        int nameLength = -1;
        
        String clientName = "";
        
        while(nameLength < 1 || nameLength > 20)
        {
            System.out.print("Please input the client's name. (1 - 20 characters long): ");
            clientName = input.nextLine().trim();
            nameLength = clientName.length();
            
            if (nameLength < 1 || nameLength > 20)
            {
                System.out.println("That is an invalid input!");
            }
            
        }
        
        return clientName;
    }
    
    //Obtains the reference number.
    public static String getRefNo()
    {
        Scanner input = new Scanner(System.in);
        
        String refNo = "";
        
        while(!validRefNo(refNo))
        {
	    System.out.print("Please input the client’s reference number. (Two letters, three numbers and one letter): ");
            refNo = input.next().trim().toUpperCase();
            
            if(!validRefNo(refNo))
            {
                System.out.println("That is an invalid input!");
            }
            
         }
        
        return refNo;
    }
    
    //Validates the reference number.
    public static boolean validRefNo(String refNo)
    {
        boolean valid = false;
        
        if(refNo.length() == 6
         && Character.isLetter(refNo.charAt(0))
         && Character.isLetter(refNo.charAt(1))
         && Character.isDigit(refNo.charAt(2))
         && Character.isDigit(refNo.charAt(3))
         && Character.isDigit(refNo.charAt(4))
         && Character.isLetter(refNo.charAt(5)))
        {
            valid = true;
        }
        
        return valid;
    }
    
    //Obtains and validates the amount of gadgets.
    public static int getNoGadget()
    {
        Scanner input = new Scanner(System.in);
        
        int noGadget = -1;
        
        while(noGadget < 1 || noGadget > 999)
        {
            System.out.print("Please input the amount of gadgets the client would like to insure. (1 - 999): ");
            noGadget = input.nextInt();
            
            if(noGadget < 1 || noGadget > 999)
            {
                System.out.println("That is an invalid input!");
            }
        }
        
        return noGadget;
    }
    
    //Obtains and validates the most expensive gadget.
    public static int getMostExpGadVal()
    {
        Scanner input = new Scanner(System.in);
        
        double dMostExpGadVal = -1;
        
        while(dMostExpGadVal < 0.01 ||  dMostExpGadVal > 99999.99)
        { 
            System.out.print("Please input the value of the clients most expensive gadget. (£0.01 - £99999.99): £");
            dMostExpGadVal = input.nextDouble();
            
            if(dMostExpGadVal < 0.01 ||  dMostExpGadVal > 99999.99)
            {
                System.out.println("That is an invalid input!");
            }
        }
        
        dMostExpGadVal = dMostExpGadVal * 100;
        int mostExpGadVal = (int) Math.round(dMostExpGadVal);
        
        return mostExpGadVal;
    }
    
    //Obtains and validates the excess.
    public static int getExcess()
    {
        Scanner input = new Scanner(System.in);
        
        int excess = -1;
        
        while(excess % 10 != 0 ||  excess < 30 || excess > 70)
        {
            System.out.print("Please input the amount of excess the client desires. (£30, £40, £50, £60, £70): £");
            excess = input.nextInt();
            
            if(excess % 10 != 0 ||  excess < 30 || excess > 70)
            {
                System.out.println("That is an invalid input!");
            }
        }
        excess = excess * 100;
        
        return excess;
    }
    
    //Obtains the payment term.
    public static String getPayTerm()
    {
        Scanner input = new Scanner(System.in);
        
        String payTerm = "";

        while(!validPayTerm(payTerm))
        {
            
	    System.out.print("Please input the client's payment term. (Annual or Monthly): ");

            payTerm = input.next().trim().toLowerCase();
                    
            if(!validPayTerm(payTerm))
            {
                System.out.println("That is an invalid input!");
            }
            
         }
        
        payTerm = formatPayTerm(payTerm);
        
        return payTerm;
        }
        
    //Validates the payment term.
    public static boolean validPayTerm(String payTermInput)
    {
        boolean valid = false;
       
        if(payTermInput.equals("a") 
        || payTermInput.equals("annual")
        || payTermInput.equals("m")
        || payTermInput.equals("monthly"))
        {
            valid = true;
        }
                
        return valid;
    }
    
    //Formats the payment term.
    public static String formatPayTerm(String payTerm)
    {
       
        if(payTerm.equals("m") || payTerm.equals("monthly")) 
        {
            payTerm = "Monthly";
        }
        
        else
        {
            payTerm = "Annual";
        }
     
        return payTerm;
    }
    
    //Checks if the policy should be rejected then returns either the first char
    // of the payment term or a R for rejected policies.
    public static char getPayTermChar(String payTerm, int noGadget, int mostExpGadVal)
    {
        char payTermChar;
        
       if(noGadget > 5 || mostExpGadVal > 100000)
       {
           payTermChar = 'R';
       }
       
       else
       {
           payTermChar = payTerm.charAt(0);
       }
        
        return payTermChar;
    }
    
    //Checks if the policy should be rejected then returns the premium with discounts
    //or -1 for rejected policies.
    public static int calcPremium(int noGadget, int mostExpGadVal, int excess, String payTerm)
    {
        double dPremium;
        int premium;
       
        double excessDisc = 1 - (((double)excess - 3000) / 20000);
       
        if(noGadget > 5 || mostExpGadVal > 100000)
        {
            premium = -1;
        }
       
        else
        {
           if(payTerm.equals("Monthly"))
            {
                dPremium = calcBasicPremium(noGadget, mostExpGadVal) * excessDisc;
            }
        
            else
            {
                dPremium = (calcBasicPremium(noGadget, mostExpGadVal) * excessDisc) * 12 * 0.9;
            }
       
            premium = (int) Math.round(dPremium);
            
        }
       return premium;
    }
    
    //Calculates the premium without discounts.
    public static int calcBasicPremium(int noGadget, int mostExpGadVal)
    {
       int basicPremium;
        
       if(noGadget == 1)
       {
           if(mostExpGadVal <= 50000)
           {
               basicPremium = 599;
           }
           
           else if(mostExpGadVal <= 80000)
           {
               basicPremium = 715;
           }
           
           else
           {
               basicPremium = 830;
           }
       }
       
       else if(noGadget > 1 && noGadget < 4)
       {
           if(mostExpGadVal <= 50000)
           {
               basicPremium = 1099;
           }
           
           else if(mostExpGadVal <= 80000)
           {
               basicPremium = 1335;
           }
           
           else
           {
               basicPremium = 1555;
           }
       }
       
       else
       {
           if(mostExpGadVal <= 50000)
           {
               basicPremium = 1599;
           }
           
           else if(mostExpGadVal <= 80000)
           {
               basicPremium = 1960;
           }
           
           else
           {
               basicPremium = 2282;
           }
       }
       
        return basicPremium;
    }
    
    //Obtains the current date.
    public static String getDate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyy");
        return sdf.format(cal.getTime());
    }
   
    //Writes the policy to a file.
    public static void policyRegister(String refNo, int noGadget, int mostExpGadVal, int excess, 
                                      int premium, char payTermChar,String clientName, String date) 
    {
        File policy = new File("policy.txt");
        
        PrintWriter writeFile = null;

        try
        {
            FileWriter fw = new FileWriter(policy, true);
            writeFile = new PrintWriter(fw);
        }
            
        catch(FileNotFoundException e)
        {
            System.out.println("Problem finding the file, ending session.");
            System.exit(0);
        }
        
        catch (IOException ex)
        {
            System.out.println("Problem finding the file, ending session.");
            System.exit(0);
        }
        
        writeFile.printf("%-11s %-6s %-3s %-7d %-2d %-5d %-1c %-20s\n", date, refNo
                      , noGadget, mostExpGadVal / 100, excess / 100, premium, payTermChar, clientName);
        
        writeFile.close();
    }
    
    //Formats and displays the policy.
    public static void displayPolicy(String date, String refNo, int noGadget, int mostExpGadVal,
                                   int gadLimit, int excess, int premium, String payTerm, String clientName)
    {

        System.out.printf(    "+==========================================+\n"
                            + "|                                          |\n"
                            + "|  Client: %-32s"     +                   "|\n"
                            + "|                                          |\n"
                            + "|    Date: %-20s"     +         "Ref: %-6s |\n"
                            + "|   Terms: %-18s"     +       "Items: %-6s |\n"
                            + "|  Excess: £%-31.2f"  +                   "|\n"
                            + "|                                          |\n"
                             , clientName, date, refNo, payTerm, numToWord(noGadget)
                             , (float)excess / 100);
        
        if(noGadget > 5 || mostExpGadVal > 100000)
        {
            
            System.out.printf("|                 Rejected                 |\n"
                            + "|                  Policy                  |\n"
                            + "|                                          |\n"
                            + "+==========================================+\n");
            
        }
        
        else
        {
            
            System.out.printf("|  %-22s"             + "Limit per         |\n"
                            + "| Premium: £%-16.2f"  +  "Gadget: %-7d" + "|\n"
                            + "|                                          |\n"
                            + "+==========================================+\n" 
                             , payTerm, (float)premium / 100, gadLimit);
            
        }

        
    }
    
    //Converts valid integer values into word Strings.
    public static String numToWord(int num)
    {
        String numWord;
        
        if(num == 1)
        {
            numWord = "One";
        }
        
        else if(num == 2)
        {
            numWord = "Two";
        }
        
        else if(num == 3)
        {
            numWord = "Three";
        }
        
        else if(num == 4)
        {
            numWord = "Four";
        }
        
        else if(num == 5)
        {
            numWord = "Five";
        }
        
        else
        {
            numWord = Integer.toString(num);
        }
        
        return numWord;
    }
    
    //Formats the limit per gadget.
    public static int formatGadLimit(int mostExpGadVal)
    {
        int gadLimit;
        
        if(mostExpGadVal <= 50000)
        {
            gadLimit = 500;
        }
        
        else if (mostExpGadVal <= 80000)
        {
            gadLimit = 800;
        }
        
        else
        {
            gadLimit = 1000;
        }
        
        return gadLimit;
    }

    //Reads file and summarises.
    public static void displayYearSummary()
    {

       Summary newSummary = registerSummary();
        
       int totalPolicy = 0;
       int totalValidPolicy = 0;
       int totalItem = 0;
       int totalPremium = 0;
       
       for (int monthIndex = 0; monthIndex < 12; monthIndex++)
       {
           totalPolicy = totalPolicy + newSummary.getVal(monthIndex, 0);
           totalValidPolicy = totalValidPolicy + newSummary.getVal(monthIndex, 1);
           totalItem = totalItem + newSummary.getVal(monthIndex, 2);
           totalPremium = totalPremium + newSummary.getVal(monthIndex, 3);
       }

       displaySummary(totalPolicy, totalValidPolicy, totalItem, totalPremium);
       
       System.out.println("\n\nJan  Feb  Mar  Apr  May  Jun  Jul  Aug  Sep  Oct  Nov  Dec");
       
       for (int monthIndex = 0; monthIndex < 12; monthIndex++)
       {
           System.out.printf("%-4s ", newSummary.getVal(monthIndex, 0));
       }
                       
    }
    
    //Reads file and summarises.
    public static void displayMonthSummary()
    {     
        Summary newSummary = registerSummary();
        
        int monthIndex = getMonth();        
                
        displaySummary(newSummary.getVal(monthIndex, 0), newSummary.getVal(monthIndex, 1), 
                       newSummary.getVal(monthIndex, 2), newSummary.getVal(monthIndex, 3));

    }
   
    //Obtains a month.
    public static int getMonth()
    {
        Scanner input = new Scanner(System.in);
        
        int monthNo = 12;
        
        while(monthNo > 11)
        {
            System.out.print("Please input which month you would like a summary: ");
            monthNo = monthToNum(input.next().toLowerCase().substring(0, 3));
            
            if(monthNo > 11)
            {
                System.out.println("That is an invalid input!");
            }
        }
        return monthNo;
    }
    
    //Formats and displays the summary.
    public static void displaySummary(int totalPolicy, int totalValidPolicy, int totalItem, int totalPremium)
    {
      double averageItem = totalItem / totalValidPolicy;
      double averagePremium = totalPremium / totalValidPolicy;
      
      System.out.printf("Total number of policies: "   +   totalPolicy   +   "\n"
                      + "Average number of items (accepted policies): %-2.1f  \n"
                      + "Average monthly premium: %-4.2f", averageItem, (float)averagePremium / 100);
    }
    
    //Reads file and summarises.
    public static Summary registerSummary()
    {
        Scanner readFile = null;
        try
        {
            File registry = new File(getFileName());
            readFile = new Scanner(registry);
        }
        
        catch(FileNotFoundException e)
        {
            System.out.println("Problem finding the file, ending session.");
            System.exit(0);
        }
        
        Summary newSummary = new Summary();
        
        while (readFile.hasNext())
        {
            String fileLine = readFile.nextLine();
            Scanner lineReader = new Scanner(fileLine);
            
            int monthIndex = monthToNum(fileLine.substring(3,6).toLowerCase());
            
            newSummary.setVal(monthIndex, 0, newSummary.getVal(monthIndex, 0) + 1);
            
            lineReader.next();
            lineReader.next();
            int noGad = lineReader.nextInt();
            
            lineReader.next();
            lineReader.next();
            int premium = lineReader.nextInt();
            
            String payTermChar = lineReader.next();

            if (!payTermChar.equals("R"))
            {
                newSummary.setVal(monthIndex, 1, newSummary.getVal(monthIndex, 1) + 1);
                newSummary.setVal(monthIndex, 2, newSummary.getVal(monthIndex, 2) + noGad);
                
                if (payTermChar.equals("A"))
                {
                    premium = premium / 12;
                }
                
                newSummary.setVal(monthIndex, 3, newSummary.getVal(monthIndex, 3) + premium);
                 
            }
            
        }

        readFile.close();
        
        return newSummary;
    }
    
    //Obtains the file name.
    public static String getFileName()
    {
        Scanner input = new Scanner(System.in);
        
        String fileName = "";

        while(!validFileName(fileName))
        {
            
	    System.out.print("Please input which file you would like a summary. (Policy or Archive): ");

            fileName = input.next().trim().toLowerCase();
                    
            if(!validFileName(fileName))
            {
                System.out.println("That is an invalid input!");
            }
            
         }
        
        fileName = formatFileName(fileName);
        
        return fileName;
    }
    
    //Validates the file name.
    public static boolean validFileName(String fileNameInput)
    {
        boolean valid = false;
       
        if(fileNameInput.equals("p") 
        || fileNameInput.equals("policy")
        || fileNameInput.equals("a")
        || fileNameInput.equals("archive"))
        {
            valid = true;
        }
                
        return valid;
    }
    
    //Formats the file name.
    public static String formatFileName(String fileName)
    {
       
        if(fileName.equals("p") || fileName.equals("policy")) 
        {
            fileName = "policy.txt";
        }
        
        else
        {
            fileName = "archive.txt";
        }
     
        return fileName;
    }
    
    //Translates the abbreviated month into an index.
    public static int monthToNum(String monthAbbr)
    {
       
        int monthNo;
        
        switch(monthAbbr)
        {
            case "jan":
                monthNo = 0;
                break;
                
            case "feb":
                monthNo = 1;
                break;
                
            case "mar":
                monthNo = 2;
                break;
                
            case "apr":
                monthNo = 3;
                break;
                
            case "may":
                monthNo = 4;
                break; 
                
            case "jun":
                monthNo = 5;
                break;
                
            case "jul":
                monthNo = 6;
                break;
                
            case "aug":
                monthNo = 7;
                break; 
                
            case "sep":
                monthNo = 8;
                break;  
                
            case "oct":
                monthNo = 9;
                break; 
                
            case "nov":
                monthNo = 10;
                break;
                
            case "dec":
                monthNo = 11;
                break;  
                
            default:
                monthNo = 12;
                break;
        }
        
        return monthNo;
    }
    
    //Reads and queries files, then displays policies.
    public static void findPolicy()
    {
        Scanner input = new Scanner(System.in);
        Scanner readFile = null;
        
        try
        {
            File registry = new File(getFileName());
            readFile = new Scanner(registry);
        }
        
        catch(FileNotFoundException e)
        {
            System.out.println("Problem finding the file, ending session.");
            System.exit(0);
        }
        
        System.out.println("Please input what to search for: ");
        String searchString = input.nextLine();
        
        while (readFile.hasNext())
        {
            String fileLine1 = readFile.nextLine();
            Scanner lineReader1 = new Scanner(fileLine1);
            
            String fileLine2 = fileLine1;
            Scanner lineReader2 = new Scanner(fileLine2);
            
            lineReader1.next();
            String refNo = lineReader1.next();
            
            lineReader1.next();
            lineReader1.next();
            lineReader1.next();
            lineReader1.next();
            lineReader1.next();
            String clientName = lineReader1.next() + " " + lineReader1.next();
            
            if(refNo.contains(searchString) || clientName.contains(searchString))
            {
                Policy newPolicy = new Policy();
                
                newPolicy.setDate(lineReader2.next());
                newPolicy.setRefNo(lineReader2.next());
                newPolicy.setNoGadget(Integer.parseInt(lineReader2.next()));
                newPolicy.setMostExpGadVal(Integer.parseInt(lineReader2.next()) * 100);
                newPolicy.setExcess(Integer.parseInt(lineReader2.next()) * 100);
                newPolicy.setPremium(Integer.parseInt(lineReader2.next()));
                String sPayTermChar = lineReader2.next();
                newPolicy.setPayTermChar(sPayTermChar.charAt(0));
                newPolicy.setClientName(lineReader2.next() + lineReader2.next());
                newPolicy.setGadLimit(formatGadLimit(newPolicy.getMostExpGadVal()));
                sPayTermChar = String.valueOf(newPolicy.getPayTermChar());
                newPolicy.setPayTerm(formatPayTerm(sPayTermChar));
          
                displayPolicy(newPolicy.getDate(), newPolicy.getRefNo(), newPolicy.getNoGadget(), 
                              newPolicy.getMostExpGadVal(), newPolicy.getGadLimit(), newPolicy.getExcess(),
                              newPolicy.getPremium(), newPolicy.getPayTerm(), newPolicy.getClientName());
            }
        }
     
    }
}   

