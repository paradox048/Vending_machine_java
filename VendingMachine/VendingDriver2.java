package Assignments.VendingMachine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

class VendingDriver2 extends JFrame implements KeyListener, ActionListener{
    
    // vending machine variables
    ArrayList<ItemType> myOnHandList;
    VendingMachine myVendingMachine;
    
    
    // Name-constants for the various dimensions
    public static final int CANVAS_WIDTH = 400;
    public static final int CANVAS_HEIGHT = 400;
    public static final Color MENU_BACKGROUND = Color.BLUE;
    public static final Color VENDING_BACKGROUND = Color.GREEN;
    public static final Color ONHAND_BACKGROUND = Color.RED;
    
    // the custom drawing canvas (extends JPanel)
    private JPanel canvasOnHand;
    private JPanel canvasVending; 
    private JPanel canvasMenu;
    
    private JTextArea menuText;
    private JTextField optionTextF;
    
    private JTextArea vendingText;
    private JTextArea onHandText;
    
    private String option;
    
    /** Constructor to set up the GUI */
    public VendingDriver2(){
        
        myOnHandList = new ArrayList<ItemType>();
        myVendingMachine = new VendingMachine();
       
        canvasMenu = new JPanel();
        canvasMenu.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvasMenu.setLayout(new BoxLayout(canvasMenu, BoxLayout.PAGE_AXIS));
        
        menuText = new JTextArea(24,50);
        menuText.setLineWrap(true);
        menuText.setEditable(false);
        menuText.setWrapStyleWord(true);
        menuText.setFont(new Font("Courier New", Font.BOLD, 12));
        
        optionTextF = new JTextField(5);
        optionTextF.setFont(new Font("Courier New", Font.BOLD, 16));
        optionTextF.addActionListener(this);
        
        canvasMenu.add(menuText);
        canvasMenu.add(optionTextF);
        
        canvasMenu.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        
        canvasOnHand = new JPanel();
        canvasOnHand.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        onHandText = new JTextArea(24,50);
        onHandText.setLineWrap(true);
        onHandText.setEditable(false);
        onHandText.setWrapStyleWord(true);
        onHandText.setFont(new Font("Courier New", Font.BOLD, 12));
        canvasOnHand.add(onHandText);
                
        canvasVending = new JPanel();
        canvasVending.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        vendingText = new JTextArea(24,50);
        vendingText.setLineWrap(true);
        vendingText.setEditable(false);
        vendingText.setWrapStyleWord(true);
        vendingText.setFont(new Font("Courier New", Font.BOLD, 12));
        canvasVending.add(vendingText);
        
        // Add panel to this JFrame
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        
        cp.add(canvasMenu);
        cp.add(canvasVending);
        cp.add(canvasOnHand);
        
         canvasMenu.setBackground(MENU_BACKGROUND);
         canvasVending.setBackground(VENDING_BACKGROUND);
         canvasOnHand.setBackground(ONHAND_BACKGROUND);
        
        
        // "this" JFrame fires KeyEvent
        addKeyListener(this);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // Handle the CLOSE button
        setTitle("Vending Machine");
        // pack all the components in the JFrame
        pack();           
        // show it
        setVisible(true); 
        requestFocus();          
    }
    
    public void displayMenu(){
        
        menuText.setText("MENU OPTIONS \n\nWhat would you like to do?"+
                         "\n\n********** ITEM TYPE **********"+
                         "\n[1]  Create a new item type"+
                         "\n[2]  Add quantity to an item"+
                         "\n\n********** VENDING MACHINE **********"+
                         "\n[3]  Add an item type to the machine"+           
                         "\n[4]  Remove an item type on the machine"+
                         "\n[5]  Restock an item type on the machine"+
                         "\n[6]  Deposit Coin"+
                         "\n[7]  Select Item to Purchase"+
                         "\n\n********** MISCELLANEOUS **********"+
                         "\n[8]  Reset/clear the items types"+
                         "\n[9]  Reset/clear the vending machine"+
                         "\n[10] Clear the money from machine"+
                         "\n[11] End program");       
        updateScreen();
       
    }
    public void processOptionSelected(){
        
        if(option.trim().equals("")){
            displayMenu();
        }
        
        else if (option.equals("11")){
           this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));           
        }
        else if (option.trim().equals("1")) {   
            try{
                createItem();                            
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(canvasMenu,"EXCEPTION WAS THROWN: Selection was not processed.","", JOptionPane.ERROR_MESSAGE);                
                e.printStackTrace();
            }
                                 
            
        }    
        else if (option.trim().equals("2")){
            try{
                updateQtyOfItem();           
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(canvasMenu,"EXCEPTION WAS THROWN: Selection was not processed.","", JOptionPane.ERROR_MESSAGE);                
                e.printStackTrace();
            }
        }    
        else if (option.trim().equals("3")){
            try{
                addItemToMachine();                               
             }
            catch (Exception e){
                JOptionPane.showMessageDialog(canvasMenu,"EXCEPTION WAS THROWN: Selection was not processed.","", JOptionPane.ERROR_MESSAGE);                
                 e.printStackTrace();
            }
        }  
        else if (option.trim().equals("4")){
            try{
                removeItemOnMachine();                           
             }
            catch (Exception e){
                JOptionPane.showMessageDialog(canvasMenu,"EXCEPTION WAS THROWN: Selection was not processed.","", JOptionPane.ERROR_MESSAGE);                
                 e.printStackTrace();
            }
        }  
       
        // restock
        else if (option.trim().equals("5")){
            try{
                reStockMachine();                 
             }
            catch (Exception e){
                JOptionPane.showMessageDialog(canvasMenu,"EXCEPTION WAS THROWN: Selection was not processed.","", JOptionPane.ERROR_MESSAGE);                
                e.printStackTrace();
            }
        }  
        //deposit coin
        else if (option.trim().equals("6")){
            try{
                depositCoinOnMachine();                            
             }
            catch (Exception e){
                JOptionPane.showMessageDialog(canvasMenu,"EXCEPTION WAS THROWN: Selection was not processed.","", JOptionPane.ERROR_MESSAGE);                
                e.printStackTrace();
            }
        }  
        // purchase item      
        else if (option.trim().equals("7")){
            try{
                purchaseItemOnMachine();      
             }
            catch (Exception e){
                JOptionPane.showMessageDialog(canvasMenu,"EXCEPTION WAS THROWN: Selection was not processed.","", JOptionPane.ERROR_MESSAGE);                
                e.printStackTrace();
            }
                  
        }  
        else if (option.trim().equals("8")){
            resetItemsOnHand();                 
        } 
        else if (option.equals("9")){
            resetVendingMachineCorea();                  
        }  
        else if (option.trim().equals("10")){
            clearMoneyFromMachine();                 
        }          
        else{
            optionTextF.setText("OPTION NOT RECOGNIZED");       
            optionTextF.selectAll();
        }       
    }
    public void clearMoneyFromMachine(){
        int itemNum, money;
        money = myVendingMachine.clearMoney();        
        updateScreen();
        JOptionPane.showMessageDialog(canvasVending, "Money Cleared. Removed " + money + " cents");                    
        
    }
    
    public void purchaseItemOnMachine(){
        int itemNum, change;
        
        String itemNumS;
        
        itemNumS = JOptionPane.showInputDialog(canvasVending,"Which item would you like purchase on the machine?", null);
        itemNum = Integer.parseInt(itemNumS);
                
        change = myVendingMachine.selectItem(itemNum);
        updateScreen();
        if(change >=0){ 
              
              JOptionPane.showMessageDialog(canvasVending, "Successfully Purchased\n" + "Your Change is " + change + " cents");                    
          
        }
        else if (change ==-9999){
             JOptionPane.showMessageDialog(canvasVending,"ERROR: Item was not purchased ","", JOptionPane.ERROR_MESSAGE);                
        }
    }
    public void depositCoinOnMachine(){
        int value;
        boolean success;
       
        String valueS;
        
        valueS = JOptionPane.showInputDialog(canvasVending,"What the value in cents of the coin being deposited?", null);
        value = Integer.parseInt(valueS);
                
        success = myVendingMachine.depositCoin(value);
        updateScreen();
        if(success){             
            JOptionPane.showMessageDialog(canvasVending, "Coin Accepted");                    
        }
        else{
            JOptionPane.showMessageDialog(canvasVending,"ERROR: Coin Rejected","", JOptionPane.ERROR_MESSAGE);      
        }   
    }
   
    public void reStockMachine(){
        int itemNum, qty;
        boolean success;
        
        String itemNumS, qtyS;        
        
        itemNumS = JOptionPane.showInputDialog(canvasVending,"Which item would you like restock on the machine?", null);
        itemNum = Integer.parseInt(itemNumS);
        
        qtyS = JOptionPane.showInputDialog(canvasVending,"How what is the quantity that you are adding?", null);
        qty=Integer.parseInt(qtyS);
             
        success = myVendingMachine.reStock(itemNum, qty);
        updateScreen();
        if(success){ 
            JOptionPane.showMessageDialog(canvasVending, "Item Successfully Restocked");          
        }
        else{
            JOptionPane.showMessageDialog(canvasVending,"ERROR: Item was not stocked since it was not found","", JOptionPane.ERROR_MESSAGE);    
        }    
    }
   
    public void resetItemsOnHand(){
        myOnHandList.clear();   
        updateScreen();
    }
    
    public void resetVendingMachineCorea(){
        myVendingMachine = new VendingMachine();
        updateScreen();
    } 
 
    public void removeItemOnMachine(){
        int itemNum;
        String itemNumS;
        boolean success;
        
        itemNumS = JOptionPane.showInputDialog(canvasVending,"Which item would you like remove on the machine?", null);
        itemNum = Integer.parseInt(itemNumS);
                
        success = myVendingMachine.removeItemType(itemNum);
        updateScreen();
        if(success){             
            JOptionPane.showMessageDialog(canvasVending, "ITEM REMOVED FROM MACHINE");             
        }
        else{
            JOptionPane.showMessageDialog(canvasVending, "ITEM WAS NOT REMOVED TO MACHINE");             
        }    
    }
   
     public boolean updateQtyOfItem(){
        int itemNum, qty, extra;
        String itemNumS, qtyS;        
        
        itemNumS = JOptionPane.showInputDialog(canvasOnHand,"Which item would you like to update?", null);
        itemNum = Integer.parseInt(itemNumS);
             
        if (itemNum < 0 || itemNum > myOnHandList.size()-1){
            JOptionPane.showMessageDialog(canvasOnHand, "ERROR: Could not add qty to item. Index is out of range", "", JOptionPane.ERROR_MESSAGE);  
            return false;
        }
        qtyS = JOptionPane.showInputDialog(canvasOnHand,"How many are you adding?", null);
        qty=Integer.parseInt(qtyS);
               
        extra = myOnHandList.get(itemNum).addQuantity(qty);
        updateScreen();
        if (extra == -9999){
            JOptionPane.showMessageDialog(canvasOnHand, "ERROR: Could not add qty to item", "", JOptionPane.ERROR_MESSAGE);  
            return false;
        }
        else{
            JOptionPane.showMessageDialog(canvasOnHand, "Quantity updated. Number of Item Qty  rejected: " + extra);
            return true;
        }
    }
    
    public void addItemToMachine(){
        int itemNum;
        boolean success;
      
        String itemNumS;
        itemNumS = JOptionPane.showInputDialog(canvasOnHand,"Which item would you like add to the machine?", null);
                
        itemNum = Integer.parseInt(itemNumS);
        if (itemNum < 0 || itemNum > myOnHandList.size()-1){
            JOptionPane.showMessageDialog(canvasOnHand, "ERROR: Could not add item to vending machine, index out of bounds", "", JOptionPane.ERROR_MESSAGE);  
        }  
        else{
            success = myVendingMachine.addItemType(myOnHandList.get(itemNum));
            if(success){               
                 myOnHandList.remove(itemNum);    
                 updateScreen();                
                JOptionPane.showMessageDialog(canvasOnHand, "ITEM ADDED TO MACHINE");                 
            }
            else{
                JOptionPane.showMessageDialog(canvasOnHand, "ITEM WAS NOT ADDED TO MACHINE");              
            }   
        }
    }  
    
    public void updateScreen(){
        updateMachineConsole();
        printItemOnHandList();
    }
    
    
    public void updateMachineConsole(){
        vendingText.setText("VENDING MACHINE\n\n");
        vendingText.append("Current Bank Amount: " + myVendingMachine.getBankAmount());
        vendingText.append("\nCurrent Change Amount: " + myVendingMachine.getChangeAmount());
        vendingText.append("\nCurrent deposited: " + myVendingMachine.getDepositAmount());
        vendingText.append("\n"+myVendingMachine.toString());
        
    }  
    
   
    
    public void printItemOnHandList(){
        onHandText.setText("QUANTITY ON HAND\n\n" + StringFormatter("IndexID", 10) + 
                           StringFormatter("Name", 15) +  StringFormatter("Price", 10) + 
                           StringFormatter("Quantity",10));
        onHandText.append("\n***********************************************");  
        
        if (myOnHandList.isEmpty()){
            onHandText.append("\nThere are no items types");
        }
        else{
            for(int i=0; i<myOnHandList.size(); i++){
                onHandText.append("\n"+NumberFormatter(i, 2) + StringFormatter("", 8)+
                                  StringFormatter(myOnHandList.get(i).getName(),13) + 
                                  NumberFormatter(myOnHandList.get(i).getPrice(), 7) + 
                                  StringFormatter("", 6)+
                                  NumberFormatter(myOnHandList.get(i).getQuantity(), 5));  
            }
            onHandText.append("\n");
        }
    }    
       
    public void createItem(){
        String name, priceS, qtyS;
        int price;
        int qty, extra;
        
        name = JOptionPane.showInputDialog(canvasMenu,"What is the name of this item?", null);
        priceS = JOptionPane.showInputDialog(canvasMenu,"What is the PRICE of this new item?", null);
        qtyS = JOptionPane.showInputDialog(canvasMenu,"What is the QUANTITY you adding?", null);
        
        price = Integer.parseInt(priceS);
        qty = Integer.parseInt(qtyS);
               
        ItemType item = new ItemType(name, price);
        extra = item.addQuantity(qty);
        myOnHandList.add(item);   
        updateScreen();
        JOptionPane.showMessageDialog(canvasMenu, "Item Created. Number of Item Qty  rejected: " + extra);             
    }  
     /***********************************************************************
     * Do not modify the code below. They are functions to assist you with 
     * this assignment
     ***********************************************************************/
    
    /* this method takes in an integer value and field width. It return
     * a string of length fWidth with the integer value right justified    
     */
    public static String NumberFormatter(int value, int fWidth) {
        DecimalFormat myFormatter = new DecimalFormat("########");
        String output = myFormatter.format(value); 
        String filler = "";
        if (fWidth - output.length() < 0 ){
            return output.substring(0, fWidth);
        }
        else{
            for(int i=0; i < fWidth - output.length(); i++)
            {
                filler = filler + " ";
            }   
            return filler+output;
        }
    }
    /* this method takes in an double value and field width. It return
     * a string of length fWidth with the double value right justified 
     * with two digits to the right of the the decimal point
     */
    static public String NumberFormatter(double value, int fWidth) {
        DecimalFormat myFormatter = new DecimalFormat("########.00");
        String output = myFormatter.format(value); 
        String filler = "";
        if (fWidth - output.length() < 0 ){
            return output.substring(0, fWidth);
        }
        else{
            for(int i=0; i < fWidth - output.length(); i++)
            {
                filler = filler + " ";
            }   
            return filler+output;
        }
    }
    /* this method takes in an string value and field width. It return
     * a string of length fwidth with the string value left justified 
     */
    static public String StringFormatter(String value, int fWidth) {
        String output = value.trim();
        String filler = "";
        if (fWidth - output.length() < 0 ){
            return output.substring(0, fWidth);
        }
        else{
            for(int i=0; i < fWidth - output.length(); i++)
            {
                filler = filler + " ";
            }   
            return output+filler;
        }
    }    
    
    
    public void actionPerformed(ActionEvent ev){
        if (ev.getSource() == optionTextF)
        {
            option = optionTextF.getText();
            optionTextF.setText("");
            processOptionSelected();
            updateScreen();
        }
    }
    
    public void keyPressed(KeyEvent evt) {}
    
    public void keyReleased(KeyEvent evt) {}
    
    public void keyTyped(KeyEvent evt) {}
    
    
    
    /** The entry main() method */
    public static void main(String[] args) {
        
        VendingDriver2 kf = new VendingDriver2(); // set up the game    
        kf.displayMenu();              
    }      
}
