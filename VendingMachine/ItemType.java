package Assignments.VendingMachine;

/**
 * @date October 14 2020
 * @author derekduong
 * This class defines the behaviours of Items for the vending machine
 */
public class ItemType {

    private int price;
    private int quantity;
    private String name;

    //constructor
  public ItemType(String n, int p)
  {
    this.name = n;
    this.price = p;
    this.quantity = 0;

    //makes the price 5 cents if the set price is zero or less than zero
    if (this.price <= 0) {
        this.price = 5;
    }
  }

  //getters
  public String getName()
  {
      return this.name;
  }
  public int getPrice()
  {
      return this.price;
  }
  public int getQuantity()
  {
      return this.quantity;
  }

    /**
     * takes in the int type parameter n that is used to represent the added quantity
     * and add it to the current quantity. Print the left over if the quantity is too large
     * and print a message if the value is negative
     * @param n
     * @return      returns the quantity of items
     */
  public int addQuantity(int n) 
  {
      if (n < 0) {
          return -9999;
      }

      if (n + this.quantity > 10) {
          int temp = this.quantity;
          this.quantity = 10;
          return n + temp - 10;

      }
      this.quantity += n;
      return 0;
  }

    /**
     * removes one of the item (decrements the quantity by one)
     * @return      returns the price of the item
     */
  public int getItem() 
  {
      this.quantity--;

      if (this.quantity < 0) {
          this.quantity = 0;
          return -9999;
      }

      return this.price;
  }
 
}
