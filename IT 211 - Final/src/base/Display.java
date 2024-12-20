package base;

import static java.lang.System.out;
import static base.CHARA.*;
import java.util.Scanner;
import objects.Barstat;

public class Display {

    public static void printStatBar(String desc, int max, int current, int length)
    {
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
        out.println(s);
        s = desc + current + "/" + max;
        out.println(s);
    }

    public static void printStatBar(Barstat stat, int length)
    {
        String desc = stat.getDesc();
        int current = stat.getCurrent();
        int max = stat.getMax();
        
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
        out.println(s);
        s = desc + ": " + current + "/" + max;
        out.println(s);
    }

    public static void println(String item)
    {
        out.println(item);
    }

    public static void print(String item)
    {
        out.print(item);
    }
    
    public static void cls()
    {
        out.print("\033[H\033[2J");
        out.flush();
    }
    
    public static void pause()
    {
        Scanner s = new Scanner(System.in);
        out.print("Press ENTER to continue");
        s.nextLine();
    }
    
    public static void pause(String msg)
    {
        Scanner s = new Scanner(System.in);
        out.print(msg);
        s.nextLine();
        //out.flush();
        //s.close();
    }

    
    
    public static void print(char item)
    {
        out.print(item);
    }
    
    public static void print(int item)
    {
        out.print(item);
    }
    
    public static void print(boolean item)
    {
        out.print(item);
    }
    
    public static void print(double item)
    {
        out.print(item);
    }
    
    public static void print(float item)
    {
        out.print(item);
    }
    
    public static void println(char item)
    {
        out.println(item);
    }
    
    public static void println(int item)
    {
        out.println(item);
    }
    
    public static void println(boolean item)
    {
        out.println(item);
    }
    
    public static void println(double item)
    {
        out.println(item);
    }
    
    public static void println(float item)
    {
        out.println(item);
    }

    public static String rjust(String text, int width)
    {
        return String.format("%" + width + "s", text);
    }

    public static String justify(String text1, String text2, int width)
    {
        String res = text1;
        width -= (text1.length() + text2.length());
        for (int i = 0; i < width; i++)
        {
            res += " ";
        }
        res += text2;

        return res;
    }

    public static void linebreak(int width)
    {
        for (int i = 0; i < width; i++)
        {
            print("-");
        }
        println("");
    }

    public static void linebreak(int width, char c)
    {
        for (int i = 0; i < width; i++)
        {
            print(c);
        }
        println("");
    }
}