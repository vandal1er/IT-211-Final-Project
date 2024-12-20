package objects;

import static base.CHARA.*;

import static base.Display.rjust;

public class Barstat {
    private String desc;
    private int max; 
    private int current;


    public Barstat(String desc, int max)
    {
        this.desc = desc;
        this.max = max;
        this.current = max;
    }
    public String getDesc()
    {
        return desc;
    }

    public int getMax()
    {
        return max;
    }

    public int getCurrent()
    {
        return current;
    }

    private void clamp()
    {
        if (current > max)
        {
            current = max;
        }
        else if (current < 0)
        {
            current = 0;
        }
    }

    public void increaseMax(int value)
    {
        max += value;
    }

    public void setMax(int value)
    {
        max = value;
    }

    public String getBarString(int length)
    {
        length -= 2;
        String s = "";

        int _fill = (int) (Math.ceil(length * current / max));

        s += edge1;

        for (int i = 0; i < length; i++)
        {
           if (i < _fill)
           {
                s += fill;
           }
           else 
           {
                s += nofill;
           }
        }
        s += edge2;
        return s;
    }

    public void printStatBar(int length)
    {
        length -= 2;
        String s = "";

        int _fill = (int) (Math.ceil(length * current / max));

        s += edge1;

        for (int i = 0; i < length; i++)
        {
           if (i < _fill)
           {
                s += fill;
           }
           else 
           {
                s += nofill;
           }
        }
        s += edge2;
        System.out.println(s);
        s = desc + ": " + current + "/" + max;
        System.out.println(s);
    }

    public void rJustStatBar(int length, int width)
    {
        String s = "";
        length -= 2;


        int _fill = (int) (Math.ceil(length * current / max));

        s += edge1;

        for (int i = 0; i < length; i++)
        {
           if (i < _fill)
           {
                s += fill;
           }
           else 
           {
                s += nofill;
           }
        }
        s += edge2;
        System.out.println(rjust(s, width));
        s = desc + ": " + current + "/" + max;
        System.out.println(rjust(s, width));
    }

    public void reduce(int value)
    {
        if (value > 0)
        {
            current -= value;
        }
        clamp();
    }

    public void recover(int value)
    {
        if (value > 0)
        {
            current += value;
        }
        clamp();
    }

    public void refill()
    {
        current = max;
    }

    public void empty()
    {
        current = 0;
    }

    public int loopedIncrement(int value)
    {
        int gain = 0;
        while (value + current >= max)
        {
            value -= max - current;
            current = 0;
            gain += 1;
        }
        current += value;
        return gain;
    }
}
