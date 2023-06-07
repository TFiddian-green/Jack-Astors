package zork;

public class Zombie {

    // zombie attributes
    private int health;
    private boolean isAlive;
    private String name;

    
    public Zombie(String name, Room room) {
        this.name = name;
        health = 100;
        isAlive = true;

        if (room.getRoomName().equals("Subway")) {
            System.out.println("There is a zombie named " + name + " in the Subway.");
        } else if (room.getRoomName().equals("ryanshousemain")) {
            System.out.println("There is a zombie named " + name + " in Ryan's house.");
        }
    }


    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        } else {
            System.out.println("The zombie named " + name + " has " + health + " health remaining.");
        }
    }

    private void die() {
        isAlive = false;
        System.out.println("The zombie named " + name + " is dead.");
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getHealth() {
        return health;
    }

}
