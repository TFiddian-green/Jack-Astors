package zork;

public class Zombie {
    private int health;
    private int attackDamage;
    private boolean isAlive;

    public Zombie100;
        isAlive = true;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    private void die() {
        isAlive = false;
        System.out.println("The zombie has been defeated!");
    }

    public boolean isAlive() {
        return isAlive;
    }
}
