package base;

import static base.Randomizer.generateInt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;

import objects.*;

public class DBMSys {
    public DBMSys()
    {

    }

    String DB_URL = "jdbc:mysql://localhost:3306/gamedb?useSSL=false&serverTimezone=UTC";
    String DB_USER = "vandal";
    String DB_PASSWORD = "james";

    public String connect()
    {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            return ("Connected to the database successfully!");
        } catch (SQLException e) {
            return ("Error connecting to the database: " + e.getMessage());
        }
    }

    public String createPlayer(String username, String password)
    {
        String command = "INSERT INTO players (name, password) VALUES (?, ?)";

        if (checkPlayer(username))
        {
            return "Username is already taken!";
        }

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.addBatch();

            pstmt.executeBatch();
            return "Player creation successful.";
        } catch (SQLException e) {
            return ("Error creating player: " + e.getMessage());
        }
    }

    public String updatePlayer(Player player)
    {
        String command = "UPDATE players SET lvl = ?, hp = ?, currentHP = ?, atk = ? WHERE name = ?";

        if (!checkPlayer(player.getName()))
        {
            return "Player Username not found!";
        }

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setInt(1, player.getLevel());
            pstmt.setInt(2, player.getMaxHP());
            pstmt.setInt(3, player.getCurrHP());
            pstmt.setInt(4, player.getAtk());
            pstmt.setString(5, player.getName());

            pstmt.executeUpdate();
            return "Player data saved!";
        } catch (SQLException e) {
            String s = ("Error saving player: " + e.getMessage());
            return s;
        }
    }

    public String deletePlayer(Player player)
    {
        String command = "DELETE FROM players WHERE name = ?";

        if (!checkPlayer(player.getName()))
        {
            return "Player Username not found!";
        }

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setString(1, player.getName());

            pstmt.executeUpdate();
            return "The Player's data has been deleted!";
        } catch (SQLException e) {
            String s = ("Error deleting player: " + e.getMessage());
            return s;
        }
    }

    public boolean checkPlayer(String username)
    {
        String command = "SELECT * FROM players WHERE name = ?";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error getting player: " + e.getMessage());
            return false;
        }
    }

    public Player getPlayer(String username)
    {
        String command = "SELECT * FROM players WHERE name = ?";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                return new Player(rs.getString("name"), rs.getInt("lvl"), rs.getInt("hp"), rs.getInt("currentHP"), rs.getInt("exp"), rs.getInt("currentEXP"), rs.getInt("atk"));
            }
            else
            {
                return new Player();
            }
        } catch (SQLException e) {
            System.out.println("Error getting player: " + e.getMessage());
            return new Player();
        }
    }

    public boolean verifyPassword(String username, String password)
    {
        String command = "SELECT * FROM players WHERE name = ?";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                String pw = (rs.getString("password"));

                //rs.getString("name")
                if (password.equals(pw))
                {
                    
                    return true;
                }
                else{
                    return false;
                }
            }
            else
            {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error getting player: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Enemy> getEnemies(int tier)
    {
        String command = "SELECT * FROM monsters WHERE tier <= ?";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setInt(1, tier);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Enemy> enemies = new ArrayList<>();

            while (rs.next())
            {
                enemies.add(new Enemy(rs.getString("monsterName"), rs.getInt("tier"), rs.getInt("lvl") + generateInt(0, tier), rs.getInt("hp") + generateInt(0, 5), rs.getInt("atk") + generateInt(-1, 2), rs.getInt("monsterID")));
            }
            return enemies;
            

        } catch (SQLException e) {
            System.out.println("Error getting player: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Item> getItems(Player player)
    {
        String command = "SELECT \r\n" + //
                        "    players.name AS playerName,\r\n" + //
                        "    items.itemID AS itemID,\r\n" + //
                        "    items.itemName AS itemName\r\n" + //
                        "FROM \r\n" + //
                        "    inventory\r\n" + //
                        "JOIN \r\n" + //
                        "    players ON inventory.playername = players.name\r\n" + //
                        "JOIN\r\n" + //
                        "    items ON inventory.itemID = items.itemID\r\n" + // 
                        "WHERE playerName = ?";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setString(1, player.getName());
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Item> items = new ArrayList<>();

            while (rs.next())
            {
                items.add(new Item(rs.getString("itemName"), rs.getInt("itemID")));
            }
            return items;
            

        } catch (SQLException e) {
            System.out.println("Error getting player: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public HashMap<Item, Float> getDrops(Enemy enemy)
    {
        String command = "select      \r\n" + //
                        "    items.itemID AS itemID,          \r\n" + //
                        "    items.itemName AS itemName,       \r\n" + //
                        "    droptables.droprate AS dropRate,\r\n" + //
                        "    droptables.monsterID AS monsterID,\r\n" + //
                        "    monsters.monsterName AS monsterName\r\n" + //
                        "\r\n" + //
                        "FROM\r\n" + //
                        "    droptables\r\n" + //
                        "join \r\n" + //
                        "    items on droptables.itemID = items.itemID\r\n" + //
                        "join \r\n" + //
                        "    monsters on droptables.monsterID = monsters.monsterID\r\n" + 
                        "WHERE droptables.monsterID = ?";
        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setInt(1, enemy.getID());
            ResultSet rs = pstmt.executeQuery();

            HashMap<Item, Float> items = new HashMap<>();

            while (rs.next())
            {
                items.put(new Item(rs.getString("itemName"), rs.getInt("itemID")), rs.getFloat("droprate"));
            }
            return items;
            

        } catch (SQLException e) {
            System.out.println("Error getting player: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public String addDrop(Player player, Item item)
    {
        String command = "INSERT INTO inventory (playername, itemID) VALUES (?, ?)";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, item.getID());
            pstmt.addBatch();

            pstmt.executeBatch();
            return "Item stash successful.";
        } catch (SQLException e) {
            return ("Error stashing item: " + e.getMessage());
        }
    }

    public String savePlayer(Player player)
    {
        String command = "UPDATE players SET lvl = ?, hp = ?, currentHP = ?, exp = ?, currentEXP = ?, atk = ? WHERE name = ?";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command)
            ) 
        {
            pstmt.setInt(1, player.getLevel());
            pstmt.setInt(2, player.getMaxHP());
            pstmt.setInt(3, player.getCurrHP());
            pstmt.setInt(4, player.getMaxEXP());
            pstmt.setInt(5, player.getCurrEXP());
            pstmt.setInt(6, player.getAtk());
            pstmt.setString(7, player.getName());

            pstmt.addBatch();

            pstmt.executeBatch();
            return "Player save successful.";
        } catch (SQLException e) {
            return ("Error saving data: " + e.getMessage());
        }
    }

    public String killPlayer(Player player)
    {
        String command = "DELETE FROM inventory WHERE playername = ?";
        String command2 = " DELETE FROM players WHERE name = ?";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = connection.prepareStatement(command);
            PreparedStatement pstmt2 = connection.prepareStatement(command2)
            ) 
        {

            pstmt.setString(1, player.getName());
            pstmt2.setString(1, player.getName());


            pstmt.addBatch();
            pstmt2.addBatch();


            pstmt.executeBatch();
            pstmt2.executeBatch();

            return "Player kill successful.";
        } catch (SQLException e) {
            return ("Error deleting data: " + e.getMessage());
        }
    }
}
