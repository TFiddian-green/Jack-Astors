package zork;

public class Zombie {
    private int health;
    private boolean isAlive;
    // private Room room;

    public Zombie(String name, Room room){
        health = 100;
        isAlive = true;
        // String Zombiename = name;
        if(room.getRoomName().equals("Subway") ){
            System.out.println("Zombified Kevin Durant and Zombified Scottie Barnes are sitting on the subway bench next to you. They look over and make eye contact with you. Now they start approaching you menacingly");
        }
        if(room.getRoomName().equals("ryanshousemain") ){
            System.out.println("The Zombies of Messi and Ronaldo are right in front of you and they are trying to kill you, if you kill them you get a key to finish the game");
        }
    }


    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    private void die() {
        isAlive = false;
        System.out.println("The zombie is dead.");
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getHealth() {
        return health;
    }

}
