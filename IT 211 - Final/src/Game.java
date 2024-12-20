import base.DBMSys;
import base.Input;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static base.Display.*;
import static base.CONFIG.*;
import static base.Randomizer.*;

import objects.Enemy;
import objects.Player;
import objects.Item;

public class Game {
    Input input; DBMSys dbms; Player player;

    

    public Game(Input input, DBMSys dbms)
    {
        this.input = input;
        this.dbms = dbms;
    }

    public void setActivePlayer(Player player)
    {
        this.player = player;
    }

    public void ActionMenu(Player p)
    {
        int choice;
        this.player = p;
        while (true)
        {
            println("Welcome, " + player.getName() + "!\n");
            println("1. Hunt");
            println("2. Bag");
            println("3. Log Out");


            choice = input.choice("Action: ");

            switch (choice) {
                case 1: 
                    if (hunt() == 0)
                    {
                        return;
                    } 
                    break;
                case 2: bag(); break;
                case 3: return;
                default: cls();
            }
        }
    }

    public void bag()
    {
        cls();
        println("Inventory");
        HashMap<String, Integer> items = getItemCountMap(dbms.getItems(player));
        if (items.size() == 0)
        {
            print("Wow, such empty. ");
            println("Go hunt.");

        }

        for (Map.Entry<String, Integer> entry : items.entrySet())
        {
            String s = "- " + entry.getKey() + " x" + entry.getValue();
            println(s);
        }
        pause("");
        cls();
    }

    public int hunt()
    {
        Enemy enemy = spawnEnemy();
        String action;
        while (true) 
        {
            cls();  
            linebreak(WIDTH);
            println(justify("LVL " + player.getLevel() + " Player " + player.getName(), (String) ("EXP: " + player.getExpRatio(false) + player.getEXPBar(17)), WIDTH));
            player.printHPStat((int) Math.floor(WIDTH/2));
            linebreak(WIDTH);
            println(rjust(justify(enemy.getName(), "LVL " + enemy.getLevel(), (int) Math.floor(WIDTH/2)), WIDTH));
            enemy.printHPStat((int) Math.floor(WIDTH/2), WIDTH);
            linebreak(WIDTH);
            action = input.str("Action: ");

            if (action.equals("a"))
            {
                int playerDmg = forbidZero(enemy.getAtk() + generateInt(-2, 1));
                int monsterDmg = forbidZero(player.getAtk() + generateInt(-1, 1));
                player.receiveDmg(playerDmg);
                enemy.receiveDmg(monsterDmg);
                println("The " + enemy.getName() + " received " + monsterDmg + " damage.");
                println("You received " + playerDmg + " damage.");
                pause("");
                
                if (!enemy.checkLife())
                {

                        Item drop = dropItem(enemy);
                        int expGain = generateExp(enemy, player);
                        int lvlGain = player.gainEXP(expGain);
                        println("Enemy " + enemy.getName() + " killed. You gained " + expGain + " EXP!");
                        if (drop != null)
                        {
                            println("The enemy " + enemy.getName() + " dropped a " + drop.getName());
                            dbms.addDrop(player, drop);
                        }
                        pause("");
                    

                    if (lvlGain == 1)
                    {
                        println("You leveled up! You recovered back to full health!");
                        println("Atk +2!\nHP +15!");
                        pause("");
                    }
                    else if (lvlGain > 1)
                    {
                        int a = 2 * lvlGain; int b = 15 * lvlGain;
                        println("You leveled up " + lvlGain + " times! You recovered back to full health!");
                        println("Atk +" + a +"!\nHP +" + b + "!");
                        pause("");
                    }

                    if (player.checkLife())
                    {
                        cls();
                        dbms.savePlayer(player);
                        return 1;
                    }
                }
                
                if (!player.checkLife())
                {
                    cls();  
                    linebreak(WIDTH);
                    println(justify("LVL " + player.getLevel() + " Player " + player.getName(), (String) ("EXP: " + player.getExpRatio(false) + player.getEXPBar(17)), WIDTH));
                    player.printHPStat((int) Math.floor(WIDTH/2));
                    linebreak(WIDTH);
                    println(rjust(justify(enemy.getName(), "LVL " + enemy.getLevel(), (int) Math.floor(WIDTH/2)), WIDTH));
                    enemy.printHPStat((int) Math.floor(WIDTH/2), WIDTH);
                    linebreak(WIDTH);
                    pause("...");
                    pause("...");
                    pause("...");
                    pause("HP Depleted. Your account will now be deleted.");

                    pause("Thank you for playing, " + player.getName() + "!");
                    dbms.killPlayer(player);
                    cls();
                    return 0;
                }
            }
        }
    }

    public Enemy spawnEnemy()
    {
        int i = Math.ceilDivExact(player.getLevel(), 5);
        ArrayList<Enemy> enemies = dbms.getEnemies(i);

        //pause("" + i);
        
        return enemies.get(generateInt(0, enemies.size()-1));
    }

    public Item dropItem(Enemy enemy)
    {
        HashMap<Item, Float> items = dbms.getDrops(enemy);
        return rollItem(items);
    }
}
