import base.DBMSys;
import base.Input;
import static base.Display.*;

public class Menu {
    DBMSys dbms = new DBMSys();
    Input input = new Input();
    Game game = new Game(input, dbms);

    public Menu()
    {
        dbms.connect();
    }

    public void run()
    {
        int choice;
        while (true)
        {
            cls();
            println("MOCK RPG\n");
            println("1. Sign Up");
            println("2. Log In");
            println("3. Exit");

            choice = input.choice("Action: ");

            switch (choice)
            {
                case 1: signup(); break;
                case 2: login(); break;
            }
        }
    }

    public void signup()
    {
        cls();
        println("Sign Up");
        String name = input.str("Enter Username: ");
        if (dbms.checkPlayer(name))
        {
            pause("Username already exists!");
            cls();
            return;
        }
        dbms.createPlayer(name, input.str("Password: "));
        pause("Player created!");
    }

    public void login()
    {
        cls();
        println("Log In");
        String name = input.str("Username: ");
        if (!dbms.checkPlayer(name))
        {
            pause("Username not found!");
            cls();
            return;
        }
        String pw = input.str("Password: ");
        if (dbms.verifyPassword(name, pw))
        {
            pause("Logging you in, " + name + "...");
            cls();
            game.ActionMenu(dbms.getPlayer(name));
        }
        else
        {
            pause("The password you entered is incorrect.");
            return;
        }
        
    }
}
