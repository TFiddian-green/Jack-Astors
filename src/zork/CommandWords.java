package zork;

public class CommandWords {
  // a constant array that holds all valid command words
private static final String validCommands[] = { "go", "quit", "help", "eat" ,"north", "east", "south", "west", "jump", "take", "pickup", "grab", "inventory", "drop", "remove", "throw", "shoot", "fire", "kill zombie", "shoot zombie", "hurt zombie", "hit zombie", "cut", "stab", "print health", "show health", "health" , "drink", "gobble"};

  /**
   * Constructor - initialise the command words.
   */
  public CommandWords() {
    // nothing to do at the moment...
  }

  /**
   * Check whether a given String is a valid command word. Return true if it is,
   * false if it isn't.
   **/
  public boolean isCommand(String aString) {
    for (String c : validCommands) {
      if (c.equals(aString))
        return true;
    }
    // if we get here, the string was not found in the commands
    return false;
  }

  /*
   * Print all valid commands to System.out.
   */
  public void showAll() {
    for (String c : validCommands) {
      System.out.print(c + "  ");
    }
    System.out.println();
  }
}
