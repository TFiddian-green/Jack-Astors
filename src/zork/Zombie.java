package zork;

public class Zombie {
    private int health;
    private int attackDamage;
    private boolean isAlive;

    public void Zombie(){
        health = 100;
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
        System.out.println("The zombie is dead.");
    }

    public boolean isAlive() {
        return isAlive;
    }
}
