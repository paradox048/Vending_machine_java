package Assignments.VendingMachine;
import java.text.DecimalFormat;
/**
 * this program simulates the operations/behaviours of a vending machine
 * @Author Derek Duong
 * @Date Oct 9 2020
 */
public class VendingMachine {

    private int bankAmount;
    private int changeAmount;
    private int deposit;
    private int itemListTracker;
    private ItemType[] itemList;


    //constructor
    // Initializes the float and certain values for the vending machine
    public VendingMachine() {
        this.itemListTracker = 0;
        this.changeAmount = 1000;
        this.itemList = new ItemType[10];
        this.deposit = 0;
        this.bankAmount = 0;

    }

    //returns the current Bank amount
    public int getBankAmount() {
        return this.bankAmount;
    }

    //returns the current deposit amount
    public int getDepositAmount() {
        return this.deposit;
    }

    //returns the current amount of change
    public int getChangeAmount() {
        return this.changeAmount;
    }


  /**
   * adds an item to the array if the array is not full
   * @param item        the item that will be added to the array if possible
   * @return            true/false
   */
    public boolean addItemType(ItemType item) {
        if (itemListTracker > 9) {
            return false;
        }

        if (!checkIfPriceIsCompatible(item.getPrice())) {
            return false;
        }

        this.itemList[itemListTracker] = item;
        this.itemListTracker++;
        return true;
    }

  /**
   * removes an item from the vending machine and shifts all the values down by one
   * @param index   the index of the item that wants to be removed
   * @return        true/false
   */
    public boolean removeItemType(int index) {
        if (index > 10 || index < 0 || this.itemList[index] == null) {
            return false;
        }

        shiftArray(this.itemList, index);
        this.itemListTracker--;
        return true;
    }

  /**
   * restocks the vending machine at index which and with the quantity of qty
   * @param which   the index of the item
   * @param qty     the quantity that the item wants to be updated  by
   * @return        true/false
   */
    public boolean reStock(int which, int qty) {
        if (which > 10 || which < 0) {
            return false;
        }

        ItemType restockedItem = this.itemList[which];

        if (restockedItem == null) {
            return false;
        }

        restockedItem.addQuantity(qty);
        return true;
    }

  /**
   * adds the parameter coin into the field deposit if it is a compatible coin
   * @param coin    the value that is trying to be deposited
   * @return        true/false
   */
    public boolean depositCoin(int coin) {
        if (checkDepositType(coin)) {
            this.deposit += coin;
            this.bankAmount += coin;
            return true;
        }
        return false;
    }

  /**
   * "dispenses" the selected item and decrements the quantity of said item
   * @param index   the index of the selected item
   * @return        returns the change due or nothing if their is no change or -9999 if there is an error
   */
    public int selectItem(int index) {

        //index out of bounds
        if (index > 10 || index < 0) {
          return -9999;
        }

        ItemType selectedItem = this.itemList[index];

        if (selectedItem == null) {
            return -9999;
        }

        //quantity of item is 0
        if (selectedItem.getQuantity() == 0) {
          return -9999;
        }

        //if the deposit is less than the price of the item
        if (getDepositAmount() < selectedItem.getPrice()) {
          return -9999;
        }

        //calculates the change and modifies the fields accordingly
        int changeDue = this.deposit - selectedItem.getPrice();
        this.deposit = 0;
        selectedItem.getItem();

        if (this.changeAmount - changeDue < 0) {
            int temp = this.changeAmount;
            this.changeAmount = 0;
            return temp;
        }

        this.changeAmount -= changeDue;
        if (this.changeAmount > 0) {
          return changeDue;
        }

        return 0;
    }

  /**
   * tops off the float and returns the profit the vending machine made
   * @return    returns the bankAmount
   */
  public int clearMoney() {
       int profit = this.bankAmount + this.changeAmount - 1000;
       this.bankAmount = 0;
       this.changeAmount = 1000;

       return profit;
    }

  /**
   * checks if the passed value is a valid coin type
    * @param coin   the checked value
   * @return         true/false
   */
  private boolean checkDepositType(int coin) {
        return (coin == 5 || coin == 10 || coin == 25 || coin == 100 || coin == 200);
    }

  /**
   * checks if the price can be divided by 5
   * @param price   the price that is being checked
   * @return        true/false
   */
    private boolean checkIfPriceIsCompatible(int price) {
        return (price % 5 == 0);
    }

  /**
   * shifts the indexes in the array down by one
   * @param itemArray   the array that the elements will be shifted down by one
   * @param index       the index of the array where the shifting starts
   */
    private void shiftArray(ItemType[] itemArray, int index) {
        for (int i = 0; i < itemArray.length; i++) {
            ItemType temp = itemArray[i];

            if (i > index) {
                itemArray[i - 1] = temp;
            }
        }
    }

    /**
     * Returns a String representation of the items in the vending machine
     * @return output       ouput is the String representation of the vending machine items
     */
    public String toString() {
        String output = "\n" + StringFormatter("IndexID", 10) +
                StringFormatter("Name", 15) + StringFormatter("Price", 10) +
                StringFormatter("Quantity", 10);
        output += "\n***********************************************\n";

        // adds the items of the vending machine to the output string
      for (int i = 0; i < this.itemListTracker; i++) {
        ItemType currentItem = this.itemList[i];

        output += NumberFormatter(i, 2) + StringFormatter("", 8)+
                StringFormatter(currentItem.getName(),13) +
                NumberFormatter(currentItem.getPrice(), 7) +
                StringFormatter("", 6)+
                NumberFormatter(currentItem.getQuantity(), 5) + "\n";
    }

        return output;
    }

    /***********************************************************************
     * Do not modify the code below. They are functions to assist you with
     * this assignment
     ***********************************************************************/

    /* this method takes in an integer value and field width. It return
     * a string of length fWidth with the integer value right justified
     */
    static public String NumberFormatter(int value, int fWidth) {
        DecimalFormat myFormatter = new DecimalFormat("########");
        String output = myFormatter.format(value);
        String filler = "";
        if (fWidth - output.length() < 0) {
            return output.substring(0, fWidth);
        } else {
            for (int i = 0; i < fWidth - output.length(); i++) {
                filler = filler + " ";
            }
            return filler + output;
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
        if (fWidth - output.length() < 0) {
            return output.substring(0, fWidth);
        } else {
            for (int i = 0; i < fWidth - output.length(); i++) {
                filler = filler + " ";
            }
            return filler + output;
        }
    }

    /* this method takes in an string value and field width. It return
     * a string of length fwidth with the string value left justified
     */
    static public String StringFormatter(String value, int fWidth) {
        String output = value.trim();
        String filler = "";
        if (fWidth - output.length() < 0) {
            return output.substring(0, fWidth);
        } else {
            for (int i = 0; i < fWidth - output.length(); i++) {
                filler = filler + " ";
            }
            return output + filler;
        }
    }

}
