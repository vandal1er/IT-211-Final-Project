package objects;

public class Enemy {
    String name; int tier; int lvl; int atk;
    int exp = 0;

    Barstat hp;

    int id;

    public Enemy()
    {

    }

    public Enemy(String name, int tier, int lvl, int hp, int atk, int id)
    {
        this.name = name;
        this.tier = tier;
        this.lvl = lvl;
        this.hp = new Barstat("HP: ", hp);
        this.atk = atk;
        this.id = id;
    }

    public boolean checkLife()
    {
        if (hp.getCurrent() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getID()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getLevel()
    {
        return lvl;
    }

    public int getMaxHP()
    {
        return hp.getMax();
    }

    public int getCurrHP()
    {
        return hp.getCurrent();
    }

    public int getAtk()
    {
        return atk;
    }

    public void printHPStat(int length, int width)
    {
        hp.rJustStatBar(length, width);
    }

    public void receiveDmg(int dmg)
    {
        hp.reduce(dmg);
    }

}
