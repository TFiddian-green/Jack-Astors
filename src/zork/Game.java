package zork;

// test change

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
// test
public class Game {

  public static HashMap<String, Room> roomMap = new HashMap<String, Room>();

  private Parser parser;
  private Room currentRoom;
  private Inventory playerInventory;
  private int playerHealth;
  private boolean isPlaying;
  private boolean potionSpawned;
  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      playerInventory = new Inventory(100);
      //loads the rooms from room.json
      
      initRooms("src\\zork\\data\\rooms.json");
      initItems("src\\zork\\data\\items.json");
      currentRoom = roomMap.get("JA1Bar");
      playerHealth = 50;
      isPlaying = true;
      // Creates the four zombies
      roomMap.get("Subway").setZombie(new Zombie("Kevin_Durant", roomMap.get("Subway")));
      roomMap.get("ryanshousemain").setZombie(new Zombie("Messi", roomMap.get("ryanshousemain")));
      roomMap.get("Subway").setZombie(new Zombie("Scottie_Barnes", roomMap.get("Subway")));
      roomMap.get("ryanshousemain").setZombie(new Zombie("Ronaldo", roomMap.get("ryanshousemain")));
  } catch (Exception e) {
      e.printStackTrace();
    }
    parser = new Parser();
  }

  private void initItems(String fileName) throws Exception{
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonItems = (JSONArray) json.get("items");

    for (Object itemObj : jsonItems) {
      Item item = new Item();
      String itemName = (String) ((JSONObject) itemObj).get("name");
      // String itemId = (String) ((JSONObject) itemObj).get("id");
      // String itemDescription = (String) ((JSONObject) itemObj).get("description");
      String loc = (String) ((JSONObject) itemObj).get("location");

      item.setName(itemName);
      // item.setDescription(itemDescription);
      // item.setId(itemId);
      Room room = roomMap.get(loc);
      room.addItem(item);
    }
  }

  private void initRooms(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonRooms = (JSONArray) json.get("rooms");

    for (Object roomObj : jsonRooms) {
      Room room = new Room();
      String roomName = (String) ((JSONObject) roomObj).get("name");
      String roomId = (String) ((JSONObject) roomObj).get("id");
      String roomDescription = (String) ((JSONObject) roomObj).get("description");
      room.setDescription(roomDescription);
      room.setRoomName(roomName);

      JSONArray jsonExits = (JSONArray) ((JSONObject) roomObj).get("exits");
      ArrayList<Exit> exits = new ArrayList<Exit>();
      for (Object exitObj : jsonExits) {
        String direction = (String) ((JSONObject) exitObj).get("direction");
        String adjacentRoom = (String) ((JSONObject) exitObj).get("adjacentRoom");
        String keyId = (String) ((JSONObject) exitObj).get("keyId");
        Boolean isLocked = (Boolean) ((JSONObject) exitObj).get("isLocked");
        Boolean isOpen = (Boolean) ((JSONObject) exitObj).get("isOpen");
        Exit exit = new Exit(direction, adjacentRoom, isLocked, keyId, isOpen);
        exits.add(exit);
      }
      room.setExits(exits);
      roomMap.put(roomId, room);
    }
  }

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    printWelcome();
    currentRoom = roomMap.get("Subway");
    
    while (isPlaying) {
      Command command;
      try {
        command = parser.getCommand();
        processCommand(command);
        playersDead();
        spawnPotion();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    System.out.println("Thank you for playing.  Good bye.");
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println();
    System.out.println("█ █ █ █▀▀ █   █▀▀ █▀█ █▀▄▀█ █▀▀   ▀█▀ █▀█     █ ▄▀█ █▀▀ █▄▀   ▄▀█ █▀ ▀█▀ █▀█ █▀█ █▀ █");
    System.out.println("▀▄▀▄▀ ██▄ █▄▄ █▄▄ █▄█ █ ▀ █ ██▄    █  █▄█   █▄█ █▀█ █▄▄ █ █   █▀█ ▄█  █  █▄█ █▀▄ ▄█ ▄");
    System.out.println("");
    System.out.println("This is a new and incredible text adventure game.");
    System.out.println("");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.longDescription());
  }

  /**
   * Given a command, process (that is: execute) the command. If this command ends
   * the game, true is returned, otherwise false is returned.
   */
  private boolean processCommand(Command command) {
    if (command.isUnknown()) {
      System.out.println("I don't know what you mean...");
      return false;
    }

    String commandWord = command.getCommandWord();
    if (commandWord.equals("help"))
      printHelp();
    else if (commandWord.equals("go"))
      goRoom(command);
    else if (commandWord.equals("quit")) 
    {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        return true; // signal that we want to quit
    } else if (commandWord.equals("take") || commandWord.equals("grab") || commandWord.equals("pickup")){
      takeItem(command.getSecondWord());
    } else if(commandWord.equals("drop") || commandWord.equals("remove") || commandWord.equals("throw"))
    {
      dropItem(command.getSecondWord());
     } //else if(commandWord.equals("drink") || commandWord.equals("gobble")){

    // }
     else if(commandWord.equals("shoot") || commandWord.equals("fire") || commandWord.equals("shoot zombie") || commandWord.equals("kill zombie") || commandWord.equals("hit zombie") || commandWord.equals("hurt zombie")){
      shootGun(command.getSecondWord());
    } else if(commandWord.equals("stab") || commandWord.equals("cut")){
      stabKnife(command.getSecondWord());

    }else if(commandWord.equals("health"))
    {
  
      System.out.println(currentRoom.getZombie().getHealth());
    }

    

    else if(commandWord.equals("eat")) 
    {
      System.out.println("Do you really think you should be eating at a time like this?");
    } else if(commandWord.equals("inventory"))
    {
      System.out.println("Inventory: " + playerInventory);
    }
    else if (commandWord.equals("jump"))
    {
      System.out.println("You are jumping.");
    }
    return false;
  }

  // implementations of user commands:

  private void takeItem(String itemName) {
    Item item = currentRoom.getInventory().removeItem(itemName);
    if (item != null){
      if(item.getName().equals("potion")){
        if (potionSpawned){
          playerInventory.addItem(item);
          System.out.println("You took the " + itemName + ".");
          return;
        } else {
          System.out.println("You cannot pick this up right now");
          return;
        }
      }
      playerInventory.addItem(item);
      System.out.println("You took the " + itemName + ".");
    }else{
      System.out.println("I don't see a " + itemName + " here.");
    }
  }
  private void dropItem(String itemName)
  {
    
    Item item = playerInventory.removeItem(itemName);
    if(item !=null){
    currentRoom.getInventory().addItem(item); 
    System.out.println("you have dropped the " + itemName + " here.");
    }
    else{
      System.out.println("there is no " + itemName + " to drop");
    }
}
  private void shootGun(String itemName){
    if(playerInventory.contains("gun"))
    {
      System.out.println("bang bang");
      Zombie zombie = currentRoom.getZombie();
      if (zombie == null){
        System.out.println("What are you shooting at!");
      }else if (zombie.isAlive()){
        // random # to get how much damge to do store it in damage
        int damage = (int)(Math.random() * 60);
        zombie.takeDamage(damage);
        zombieDamage();
       }else{
        System.out.println("You are shooting a a dead zombie.");
      }
    }else{
      System.out.println("you don't have a gun");
      zombieDamage();
    }
  }
  private void stabKnife(String itemName)
  {
    if(playerInventory.contains("knife"))
    {
      System.out.println("yo wanna run it right now? I'll stab you");
      Zombie zombie2 = currentRoom.getZombie();
      if(zombie2 == null)
      {
        System.out.println("what are you stabbing?");
      } else if(zombie2.isAlive())
      {
        int damage2 = (int)(Math.random()*30);
        zombie2.takeDamage(damage2);
        zombieDamage();
      } else{
        System.out.println("you're stabbing a dead zombie");
      }
    }else{
        System.out.println("you don't have a knife");
        zombieDamage();
    }
      }
      private void zombieDamage()
      { 
        try{
          
          Zombie zombie3 = currentRoom.getZombie();
          if(zombie3.isAlive() && zombie3 != null){
            int damage3 = (int)(Math.random()*10);
           playerHealth-= damage3;
           System.out.println("you have " + playerHealth +" health left (:");
  
          }
        }
        catch(Exception e)
        {
          
        }
      }

      private void playersDead()
      {
        if(playerHealth <= 0)
        {
          System.out.println(" you lose...");
          isPlaying = false;
        }
      }

        private void spawnPotion(){
          Zombie zombie = roomMap.get("ryanshousemain").getZombie();
          Zombie zombie2 = roomMap.get("Subway").getZombie();
          if (zombie.getHealth() <= 0 && zombie2.getHealth() <= 0){
            System.out.println("All zombies are dead, spawning potion");
            potionSpawned = true;
          }


        }


  

  /**
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("You are starting the game.");
    System.out.println();
    System.out.println("Your command words are:");
    System.out.println();
    parser.showCommands();
  }

  /**
   * Try to go to one direction. If there is an exit, enter the new room,
   * otherwise print an error message.
   */
  private void goRoom(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to go...
      System.out.println("Go where?");
      return;
    }

    String direction = command.getSecondWord();

    // Try to leave current room.
    Room nextRoom = currentRoom.nextRoom(direction);

    if (nextRoom == null)
      System.out.println("There is no door!");
    else {
      currentRoom = nextRoom;
      System.out.println(currentRoom.longDescription());
    }
  }
}
