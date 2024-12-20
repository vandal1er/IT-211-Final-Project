package objects;

public class Player {
    private String username;
    private int lvl;
    private int atk;
    private Barstat hp;
    private Barstat exp;

    public Player()
    {
        
    }

    public Player(String n, int l, int a, int h)
    {
        username = n;
        lvl = l;
        atk = a;
        hp = new Barstat("HP", h);
        exp = new Barstat("EXP", 100);
        exp.empty();
    }

    public Player(String n, int l, int h, int currH, int e, int currE, int a)
    {
        username = n;
        lvl = l;
        atk = a;
        hp = new Barstat("HP", h);
        exp = new Barstat("EXP", e);
        exp.empty();
        hp.empty();
        exp.recover(currE);
        hp.recover(currH);
    }

    public String getName()
    {
        return username;
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

    public int getMaxEXP()
    {
        return exp.getMax();
    }

    public int getCurrEXP()
    {
        return exp.getCurrent();
    }

    public int getAtk()
    {
        return atk;
    }

    public void receiveDmg(int dmg)
    {
        hp.reduce(dmg);
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

    public String getPlayerDetails()
    {
        String s = username + "\nLVL: " + lvl + "\nATK: " + atk + "\nHP: " + hp.getCurrent() + "/" + hp.getMax();
        return s;
    }

    public void printHPStat(int length)
    {
        hp.printStatBar(length);
    }

    public String getEXPBar(int length)
    {
        return exp.getBarString(length);
    }

    public void printEXPStat(int length)
    {
        exp.printStatBar(length);
    }

    public int gainEXP(int exp)
    {
        int gain = this.exp.loopedIncrement(exp);
        lvl += gain;
        for (int i = 0; i < gain; i++)
        {
            levelUp();
        }
        return gain;
    }

    public String getExpRatio(boolean percentage)
    {
        if (percentage)
        {
            float f = exp.getCurrent()/exp.getMax();
            return "" + (f * 100) + "%";
        }
        else
        {
            return "" + exp.getCurrent() + "/" + exp.getMax();
        }
    }

    public void levelUp()
    {
        hp.increaseMax(15);
        exp.increaseMax(10);
        atk += 2;
        hp.refill();
    }
}
