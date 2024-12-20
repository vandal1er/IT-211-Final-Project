package base;

import java.util.Scanner;

import static base.CHARA.prevLine;
import static base.Display.*;

public class Input {
    Scanner scanner = new Scanner(System.in);

    public Input()
    {

    }

    public String str(String prompt)
    {
        String s = "";
        print(prompt);
        while (s == "")
        {
            s = scanner.nextLine();
            if (s == "")
            {
                print(prevLine(prompt.length()));
            }
        }
        return s;
    }

    public int choice(String prompt)
    {
        String s = "";
        print(prompt);
        while (s == "")
        {
            s = scanner.nextLine();
            if (s == "")
            {
                print(prevLine(prompt.length()));
            }
            else if (s.length() > 1)
            {
                s = "";
            }
            else
            {
                if (Character.isDigit(s.charAt(0)))
                {
                    return ((int) s.charAt(0) - (int) '0');
                }
                else
                {
                    return -1;
                }
            }
        }
        return -1;
    }
}
