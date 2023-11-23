import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rules extends JFrame implements ActionListener {

    // username to pass to Quiz Screen as an argument which is being passed here
    // from Main Frame.
    String username;
    String password;
    JButton back, start; // 2 global buttons, Action Listener can detect Events of these buttons.

    Rules(String username, String password) { // username and password is recieved as parameter.

        this.username = username; // name recived from Mian Frame being passed to global Name variable.
        this.password = password; // password recived from Mian Frame being passed to global Name variable.
        Color MainColor = new Color(30, 144, 254);
        Color PrimaryColor = new Color(160, 32, 240); // Same 2 Theme Colors.

        // Rules Frame Position and Size on Screen.
        setBounds(600, 200, 800, 800);
        setDefaultCloseOperation(Rules.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(Color.white);
        setLayout(null);

        // Welcome Text, taking name from the Main Frame.
        JLabel l1 = new JLabel("Welcome " + username + " to Simple Minds !");
        l1.setForeground(MainColor);
        l1.setFont(new Font("Mongolian Baiti", Font.BOLD, 28));
        l1.setBounds(50, 20, 700, 30);
        add(l1);

        // Rules Text - 9 points.
        JLabel l2 = new JLabel();
        l2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l2.setBounds(20, 90, 600, 400);
        l2.setText(
                "<html>" + // html based text to format text accordingly -- "<br><br>" to break line.
                        "1. You are trained to be a programmer and not a story teller, answer point to point."
                        + "<br><br>" +
                        "2. Do not unnecesserily smile at the person sitting next to you, they may also not know the answer."
                        + "<br><br>" +
                        "3. You may have a lot of options in life but here all the questions are compulsory."
                        + "<br><br>" +
                        "4. Crying is allowed but please do so quietly." + "<br><br>" +
                        "5. Only a fool asks and a wise answers." + "<br><br>" +
                        "6. Do not ger nervous if your friend is answering more questions, may be he/she is doing Akkar Bakkar."
                        + "<br><br>" +
                        "7. Brace Yourself, this paper is not for the faint hearted" + "<br><br>" +
                        "8. May you know more than what John Snow knows." + "<br><br>" +
                        "9. This is not the only examination of your life, Good Luck" +
                        "<html>");
        add(l2);

        //Back Button to go back to Main Frame.
        back = new JButton("Back");
        back.setBounds(250, 500, 100, 30);
        back.addActionListener(this); //connecting button to the event Listener.
        back.setForeground(Color.white);
        back.setBackground(MainColor);
        add(back);

        //Start Button to Start the quiz and go to Quiz Screen.
        start = new JButton("Start");
        start.setBounds(400, 500, 100, 30);
        start.addActionListener(this); //connecting button to the event Listener.
        start.setForeground(Color.white);
        start.setBackground(PrimaryColor);
        add(start);

        this.setVisible(true);

    }

    //actionPerformed() Overrided from ActionListener Interface.
    @Override
    public void actionPerformed(ActionEvent e) {

        //To check which Button was pressed by the user [Back or Start].
        if (e.getSource() == back) {
            this.dispose(); //closes the Rules Screen and Go Back to Main Frame.
            new SimpleMinds().setVisible(true);
        } else if (e.getSource() == start) {
            this.dispose(); //closes the Rules Screen and Open the Quiz Screen to Start Quiz.
            new Quiz(username,password).setVisible(true);
            //username is being passed to Quiz Screen as an argument.
        }
    }

    public static void main(String[] args) {

        //Class will be created and constructor will be called automatically.
        //Constructor holds all the functionality.
        new Rules("", "");
        // Empty String is passed as an argument to username and password parameter.
    }

}
