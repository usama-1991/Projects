import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Score extends JFrame implements ActionListener {

    Color MainColor = new Color(30, 144, 254); // Theme Colors.
    Color PrimaryColor = new Color(160, 32, 240);

    JLabel highScore;
    JButton playAgain, showScore, exit, remove;

    String username;
    int score;
    String password;

    // takes Name & Score from Quiz Screen as paremeters.
    Score(String username, int score, String password) {

        this.username = username;
        this.score = score;

        // sets Position & Size of Score Window.
        setBounds(600, 150, 750, 550);
        setDefaultCloseOperation(Score.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        setLayout(null);

        // Image is fetched from icons folder.
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/score.png"));
        // Image is scaled to fitt the available space.
        Image i2 = i1.getImage().getScaledInstance(300, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(0, 200, 300, 250);
        add(l1);

        // ThankYou Text, takes username as parameter recieved when called.
        JLabel l2 = new JLabel("Thank You " + username + " for Playing Simple Minds");
        l2.setBounds(45, 30, 700, 30);
        l2.setFont(new Font("RALEWAY", Font.BOLD, 26));
        add(l2);

        // Score Text, takes total Score as parameter recieved when called.
        JLabel l3 = new JLabel("Your Score is " + score);
        l3.setBounds(370, 200, 300, 30);
        l3.setFont(new Font("RALEWAY", Font.BOLD, 26));
        l3.setForeground(PrimaryColor);
        add(l3);

        // PlayAgain Button Takes you back to the Main Frame.
        playAgain = new JButton("Play Again");
        playAgain.setBackground(PrimaryColor);
        playAgain.setForeground(Color.white);
        playAgain.setBounds(330, 270, 120, 30);
        playAgain.addActionListener(this);
        add(playAgain);

        // showScore Button opens the DataBase File.
        showScore = new JButton("Score Board");
        showScore.setBackground(MainColor);
        showScore.setForeground(Color.white);
        showScore.setBounds(480, 270, 120, 30);
        showScore.addActionListener(this);
        add(showScore);

        exit = new JButton("Quit");
        exit.setBackground(PrimaryColor);
        exit.setForeground(Color.white);
        exit.setBounds(405, 330, 120, 30);
        exit.addActionListener(this);
        add(exit);

        remove = new JButton("Update / Remove Records");
        remove.setBackground(MainColor);
        remove.setForeground(Color.white);
        remove.setBounds(405, 430, 200, 30);
        remove.addActionListener(this);
        add(remove);

        if (password.equals("admin")){
            showScore.setEnabled(true);
            remove.setEnabled(true);
        }
        else {
            showScore.setEnabled(false);
            remove.setEnabled(false);
        }

    }

    // actionPerformed() Overrided from ActionListener Interface.
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == playAgain) {
            this.dispose();
            new SimpleMinds().setVisible(true); // Opens Main Frame on Click of PlayAgain button.
        } else if (e.getSource() == showScore) {
            showScore();
        } else if (e.getSource() == remove) {
            new dataManagemet().setVisible(true);
        } else {
            delFile();
            System.exit(0);
        }

    }

    public void showScore() {
        try {

            String path = "jdbc:ucanaccess://C:\\Quiz_App\\src\\database\\data.accdb";
            Connection connection = DriverManager.getConnection(path);
            java.sql.Statement statement = connection.createStatement();

            String selByName = "SELECT * FROM ScoreBoard";
            ResultSet result = statement.executeQuery(selByName);

            String id = "";
            String n = "";
            String s = "";
            String data = "";

            while (result.next()) {

                n = result.getString("Name");
                s = result.getString("score");
                id = result.getString("ID");
                String row = id + "\t" + n + "\t\t" + s + "\n";

                data += row;
            }

            result.close();
            statement.close();
            connection.close();

            System.out.println(data);

            FileWriter writer = new FileWriter(
                    "C:\\Quiz_App\\src\\database\\ScoreBoard.txt");
            writer.write(data);

            writer.close();

            File file = new File(
                    "C:\\Quiz_App\\src\\database\\ScoreBoard.txt");
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void delFile() {
        File file = new File(
                "C:\\Quiz_App\\src\\database\\ScoreBoard.txt");
        file.delete();
    }

    public static void main(String[] args) {
        // empty screen and 0 int is passed as arguments as initial values to minimize
        // errors.
        new Score("", 0,"admin").setVisible(true);
    }

}
