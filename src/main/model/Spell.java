package model;

//Represents a spell that can be used to attack the enemy by the wizard.
public class Spell {

    public String spellsName;
    // REQUIRES: atk >= 10 && atk <= 99.
    public int atk;
    public boolean isUnlocked;

    //Constructor
    //MODIFIES: this
    //EFFECTS: Construct a new spell
    public Spell(String spellsName, int atk) {
        this.spellsName = spellsName;
        this.atk = atk;
    }

    @Override
    public String toString() {
        return spellsName;
    }

    //EFFECTS: returns the name of the spell
    public String getSpellsName() {
        return spellsName;
    }

    //EFFECTS: returns the atk of the spell
    public int getAtk() {
        return atk;
    }

    //EFFECTS: setter
    public void setSpellsName(String spellsName) {
        this.spellsName = spellsName;
    }

    //EFFECTS: setter
    public void setAtk(int atk) {
        this.atk = atk;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }
}
