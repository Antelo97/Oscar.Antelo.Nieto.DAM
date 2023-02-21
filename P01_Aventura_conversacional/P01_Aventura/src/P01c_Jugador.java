import java.util.ArrayList;

public class P01c_Jugador {

    // Representa la energía
    private int health;

    // Representa la economía
    private int rupees;

    // Representan el inventario
    private boolean initialKey;
    private boolean mysteryKey;
    private boolean weapon;
    private boolean shield;

    public P01c_Jugador(int health, int rupees, boolean initialKey, boolean mysteryKey, boolean weapon, boolean shield) {
        this.health = health;
        this.rupees = rupees;
        this.initialKey = initialKey;
        this.mysteryKey = mysteryKey;
        this.weapon = weapon;
        this.shield = shield;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRupees() {
        return rupees;
    }

    public void setRupees(int rupees) {
        this.rupees = rupees;
    }

    public boolean isInitialKey() {
        return initialKey;
    }

    public void setInitialKey(boolean initialKey) {
        this.initialKey = initialKey;
    }

    public boolean isMysteryKey() {
        return mysteryKey;
    }

    public void setMysteryKey(boolean mysteryKey) {
        this.mysteryKey = mysteryKey;
    }

    public boolean isWeapon() {
        return weapon;
    }

    public void setWeapon(boolean weapon) {
        this.weapon = weapon;
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    @Override
    public String toString() {
        String str = "\nESTADO\n● Energía: " + health + "\n● Economía: " + rupees + " rupias\n● Inventario: " +
                "\n    - Llave inicial: " + initialKey + "\n    - Llave misteriosa: " + mysteryKey +
                "\n    - Arma: " + weapon + "\n    - Escudo: " + shield + "\n";
        return str;
    }
}