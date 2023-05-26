package zork;

import java.util.ArrayList;

public class Inventory {
  private ArrayList<Item> items;
  private int maxWeight;
  private int currentWeight;

  public Inventory(int maxWeight) {
    this.items = new ArrayList<Item>();
    this.maxWeight = maxWeight;
    this.currentWeight = 0;
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public int getCurrentWeight() {
    return currentWeight;
  }

  public boolean addItem(Item item) {
    if (item.getWeight() + currentWeight <= maxWeight)
      return items.add(item);
    else {
      System.out.println("There is no room to add the item.");
      return false;
    }
  }

  // remove item
  public Item removeItem(String itemName){

    for (int i = 0; i < items.size(); i++) {
      if (items.get(i).getName().equals(itemName))
        return items.remove(i);
    }

    return null;
  }

  public String toString(){
    String result = "";
    for (Item item : items) {
      result += item.getName() +"\n";
    }

    return result;
  }
}
