import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class dataManagemet extends JFrame implements ActionListener {

    // 2 Colors selected. So, I don't have to pick them again and agian.
    Color MainColor = new Color(30, 144, 254);
    Color PrimaryColor = new Color(160, 32, 240);

    JButton remove, bestScore, search, close;
    JLabel l3;
    JTextField t1, t2;

    dataManagemet() {

        setBounds(400, 300, 700, 450);
        setTitle("Data Management");
        setResizable(false);
        setDefaultCloseOperation(dataManagemet.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel l1 = new JLabel("Enter the Values you want to edit.");
        l1.setBounds(30, 30, 450, 45);
        l1.setFont(new Font("Mongolian Baiti", Font.BOLD, 28));
        l1.setForeground(MainColor);
        add(l1);

        JLabel l2 = new JLabel("Enter Student Name: ");
        l2.setBounds(100, 80, 200, 30);
        l2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(l2);

        t1 = new JTextField();
        t1.setBounds(330, 80, 200, 30);
        t1.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(t1);

        JLabel l4 = new JLabel("Enter Student ID: ");
        l4.setBounds(100, 140, 200, 30);
        l4.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(l4);

        t2 = new JTextField();
        t2.setBounds(330, 140, 200, 30);
        t2.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(t2);

        l3 = new JLabel();
        highScore();
        l3.setForeground(MainColor);
        l3.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(l3);

        search = new JButton("Search");
        search.setBounds(70, 250, 150, 45);
        search.addActionListener(this);
        search.setFont(new Font("Tahoma", Font.BOLD, 18));
        search.setBackground(MainColor);
        search.setForeground(Color.white);
        add(search);

        remove = new JButton("Remove");
        remove.setBounds(270, 250, 150, 45);
        remove.addActionListener(this);
        remove.setFont(new Font("Tahoma", Font.BOLD, 18));
        remove.setBackground(PrimaryColor);
        remove.setForeground(Color.white);
        add(remove);

        bestScore = new JButton("High Score");
        bestScore.setBounds(470, 250, 150, 45);
        bestScore.addActionListener(this);
        bestScore.setFont(new Font("Tahoma", Font.BOLD, 18));
        bestScore.setBackground(MainColor);
        bestScore.setForeground(Color.white);
        add(bestScore);

        close = new JButton("Close");
        close.setBounds(540, 350, 80, 25);
        close.addActionListener(this);
        close.setBackground(PrimaryColor);
        close.setForeground(Color.white);
        add(close);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == search) {
            search();
        } else if (e.getSource() == remove) {
            delete();
        } else if (e.getSource() == bestScore) {
            highScore();
        } else {
            this.dispose();
        }

    }

    public void delete() {
        try {
            String path = "jdbc:ucanaccess://C:\\Quiz_App\\src\\database\\data.accdb";
            Connection connection = DriverManager.getConnection(path);
            java.sql.Statement statement = connection.createStatement();

            String Name = t1.getText();
            String ID = t2.getText();

            if (Name.isEmpty() || ID.isEmpty()) {
                l3.setBounds(220, 180, 500, 30);
                l3.setForeground(Color.RED);
                l3.setText("Please Enter Name & ID of the User!");
            }
            else {
                String delByName = "DELETE FROM ScoreBoard WHERE Name = '" + Name + "' AND ID =  '" + ID + "'";
                int result = statement.executeUpdate(delByName);

                if (result > 0) {
                    l3.setBounds(120, 180, 500, 30);
                    l3.setText(Name + "'s Record has beed Deleted Successfully !");
                } else {
                    l3.setBounds(220, 180, 500, 30);
                    l3.setForeground(Color.RED);
                    l3.setText(Name + "'s Record not Found !");
                }
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search() {
        try {

            String path = "jdbc:ucanaccess://C:\\Quiz_App\\src\\database\\data.accdb";
            Connection connection = DriverManager.getConnection(path);
            java.sql.Statement statement = connection.createStatement();

            String selByName = "SELECT * FROM ScoreBoard";
            ResultSet result = statement.executeQuery(selByName);

            boolean dataFound = false;

            while (result.next() && dataFound == false) {

                String Name = t1.getText();
                String ID = t2.getText();

                String name = result.getString("Name");
                String score = result.getString("Score");
                String id = result.getString("ID");

                if (Name.isEmpty()) {
                    dataFound = true;
                    l3.setBounds(220, 180, 500, 30);
                    l3.setForeground(Color.RED);
                    l3.setText("Please Enter Name of the User!");
                    System.out.println("Empty");

                } else if (Name.equals(name) && ID.equals(id)) {
                    dataFound = true;
                    l3.setBounds(230, 180, 500, 30);
                    l3.setForeground(PrimaryColor);
                    l3.setText("ID " + id + ": " + Name + "'s Score is " + score);
                    System.out.println(score);

                } else {
                    l3.setBounds(220, 180, 500, 30);
                    l3.setForeground(Color.RED);
                    l3.setText(Name + "'s Record not Found !");
                    System.out.println(name);
                }
            }
            result.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void highScore() {
        try {
            String path = "jdbc:ucanaccess://C:\\Quiz_App\\src\\database\\data.accdb";
            Connection connection = DriverManager.getConnection(path);
            java.sql.Statement statement;
            statement = connection.createStatement();
            String selByName = "SELECT * FROM ScoreBoard";
            ResultSet result = statement.executeQuery(selByName);

            String max_name = "";
            int max = 0;
            String name = "", score = "";
            while (result.next()) {

                name = result.getString("Name");
                score = result.getString("Score");

                int highScore = +result.getInt("Score");
                if (highScore > max) {
                    max = +highScore;
                    max_name =  result.getString("Name");
                }
            }

            l3.setBounds(200, 190, 500, 30);
            l3.setForeground(PrimaryColor);
            l3.setText(max_name + " has Highest Score of " + max);
            System.out.println(max_name + " has Highest Score of " + max);

            result.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new dataManagemet().setVisible(true);
    }

}
