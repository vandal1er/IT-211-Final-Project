package base;

public class CHARA {
    public static final char fill = '|';
    public static final char nofill = '-';
    public static final char edge1 = '[';
    public static final char edge2 = ']';
    public static final String prevLine = "\033[A\r";

    public static String prevLine(int end)
    {
        String s = "\033[A\r" + "\033[" + end + "C";
        return s;
    }
}
