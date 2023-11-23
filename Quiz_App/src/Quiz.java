import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Quiz extends JFrame implements ActionListener {

    public static int count = 0; //counter to keep track of No. of Question currently displayed on screen.
    public static int timer = 15; //15 seconds of time to solve each Question.
    public static boolean ans_given = false; //keep track whether the user has answerd or not.
    public static int score = 0; //Stores total Score of the user.

    JButton next, lifeline, submit; //Buttons [Next, LifeLine, Submit]
    JLabel qno, question; //Question No. Text, Actual Quesion Text.
    JRadioButton opt1, opt2, opt3, opt4; //4 options given to the user.
    ButtonGroup options; //4 options are grouped as 1 input.

    String q[][] = new String[10][5]; //Array Stores Question and it's 4 options.
    String qa[][] = new String[10][2]; //Array Stores Correct Answers. 10 questions - 1 correct answer. (2 to use lifeline).
    String pa[][] = new String[10][1]; //Answers choosen by the user - 10 questions - 1 option chosen.

    String    username; //username to send to Score screen.
    String    password; //password to send to Score screen.

    Quiz(String username, String password) { //username is being accepted as a parameter.

        this.username = username; //Name teken from previous Screen to stored to pass to Score Screen.
        this.password = password; //Name teken from previous Screen to stored to pass to Score Screen.
        Color MainColor = new Color(30, 144, 254);
        Color PrimaryColor = new Color(160, 32, 240);

        //sets the Position and Size of Quiz Screen.
        setBounds(250, 50, 1440, 950);
        setDefaultCloseOperation(Quiz.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        setLayout(null);

        //Image Icons takes image from icons folder and adds it to label to show on screen.
        ImageIcon I1 = new ImageIcon(ClassLoader.getSystemResource("icons/quiz.jpg"));
        JLabel l1 = new JLabel(I1);
        l1.setBounds(0, 0, 1440, 392);
        add(l1);

        // Questions and thier Options and the correct answer
        try (BufferedReader br = new BufferedReader(new FileReader("questions.txt"))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println(parts[5]);
                q[i][0] = parts[0];
                q[i][1] = parts[1];
                q[i][2] = parts[2];
                q[i][3] = parts[3];
                q[i][4] = parts[4];
                qa[i][0] = parts[5];
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Question Number Text.
        qno = new JLabel();
        qno.setBounds(100, 450, 50, 30);
        qno.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(qno);

        //Actual Question Text.
        question = new JLabel();
        question.setBounds(150, 450, 900, 30);
        question.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(question);

        //Option 1.
        opt1 = new JRadioButton();
        opt1.setFont(new Font("Dialog", Font.PLAIN, 20));
        opt1.setBackground(Color.white);
        opt1.setBounds(170, 520, 400, 30);
        add(opt1);

        //Option 2.
        opt2 = new JRadioButton();
        opt2.setFont(new Font("Dialog", Font.PLAIN, 20));
        opt2.setBackground(Color.white);
        opt2.setBounds(170, 560, 400, 30);
        add(opt2);

        //Option 3.
        opt3 = new JRadioButton();
        opt3.setFont(new Font("Dialog", Font.PLAIN, 20));
        opt3.setBackground(Color.white);
        opt3.setBounds(170, 600, 400, 30);
        add(opt3);

        //Option 4.
        opt4 = new JRadioButton();
        opt4.setFont(new Font("Dialog", Font.PLAIN, 20));
        opt4.setBackground(Color.white);
        opt4.setBounds(170, 640, 400, 30);
        add(opt4);

        //4 options grouped as 1 selectable Button.
        options = new ButtonGroup();
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        options.add(opt4);

        //Next Button to Go to Next Question.
        next = new JButton("Next");
        next.setFont(new Font("Tahoma", Font.PLAIN, 22));
        next.addActionListener(this);
        next.setBackground(PrimaryColor);
        next.setForeground(Color.white);
        next.setBounds(1100, 550, 200, 40);
        add(next);

        //LifeLine Button to limit the options from 4 to 2.(1 wrong, 1 correct)
        lifeline = new JButton("50 - 50 Lifeline");
        lifeline.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lifeline.addActionListener(this);
        lifeline.setBackground(PrimaryColor);
        lifeline.setForeground(Color.white);
        lifeline.setBounds(1100, 630, 200, 40);
        add(lifeline);

        //Submit Button to End the Quiz on last Quesion.
        submit = new JButton("Submit");
        submit.setFont(new Font("Tahoma", Font.PLAIN, 22));
        submit.addActionListener(this);
        submit.setEnabled(false);
        submit.setBackground(MainColor);
        submit.setForeground(Color.white);
        submit.setBounds(1100, 710, 200, 40);
        add(submit);

        start(0);

    }

    //actionPerformed() Overrided from ActionListener Interface.
    @Override
    public void actionPerformed(ActionEvent e) {

        //check which button is pressed. [Next, Submit, Lifeline]
        if (e.getSource() == next) {
            repaint(); //calls Paint() function to change values on same screen.
            opt1.setEnabled(true); //will enable all options if lifeline is used in the previos question.
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);

            ans_given = true; //User has chosen some Option on the Click of Next Button.

            if (options.getSelection() == null) {
                //if user does not chose any option within 15 sec, Empty String will be saved in the Answer Array.
                pa[count][0] = "";
            } else {
                //Value of choosen answer will be fetched and stored in the 1st memory block of the Answer Array.
                pa[count][0] = options.getSelection().getActionCommand();
            }

            if (count == 8) {
                //Submit Button will be Disabled until last Question.
                //Next Button will be Enabled until last Question.
                next.setEnabled(false);
                submit.setEnabled(true);
            }

            count++; //Next Question will show up on the click of Next Button.
            start(count); //calls start() function and passes real question count as argument to update initial values.

        } else if (e.getSource() == submit) {

            //User has chosen some Option on the Click of Submit Button.
            ans_given = true;

            if (options.getSelection() == null) {
                 //if user does not chose any option within 15 sec, Empty String will be saved in the Answer Array.
                pa[count][0] = "";
            } else {
                //Value of choosen answer will be fetched and stored in the 1st memory block of the Answer Array.
                pa[count][0] = options.getSelection().getActionCommand();
            }

            //on Click on Submit Button, It matches all the Answers Choosen saved in pa[][] Array to the Array with correct Answers qa[][].
            for (int i = 0; i < pa.length; i++) {
                System.out.println(pa[i][0] + "\n" + qa[i][0]);
                if (pa[i][0].equals(qa[i][0])) {

                    //if answers right, Total Score will increment by 10.
                    score += 10;
                } else {
                    //if answers wrong, Total Score will increment by 0.
                    score += 0;
                }
            }
            Add(); //call Add function, which will add Name & Score to the DataBase file.
            count = 0;

            this.dispose(); //Quiz Screen will dispose on click of Submit Button.

            //Score Screen will open and Name & Score will be passed as an argument.
            new Score(username, score, password).setVisible(true);
            score = 0;



        } else if (e.getSource() == lifeline) {

            //LifeLine Functionality Trick.. only Options 1 or 4 are correct in Question 2, 4, 6, 8, 9.
            //if Lifeline is used on these questions Only Option 2 & 3 will disable.
            if(count == 2 || count == 4 || count == 6 || count == 8 || count == 9) {
                opt2.setEnabled(false);
                opt3.setEnabled(false);
            } else {
                //if Lifeline is used on 0,1,3,5,7 questions Only Option 1 & 4 will disable.
                //Because only Option 2 or 3 is Correct.
                opt1.setEnabled(false);
                opt4.setEnabled(false);
            }
            lifeline.setEnabled(false);

        }
    }

    //same paint() function called by Next Button.
    public void paint(Graphics g) {
        super.paint(g); //Parent Only rebuilds assets that were changed in childs.

        //Timer Text counting from 15 - 0.
        String time = "Time Left = " + timer + " seconds"; // 15
        g.setColor(Color.RED);
        g.setFont(new Font("Tahoma", Font.BOLD, 25));

        //checks if the timer has reached 0 and changes text to Times Up.
        if (timer > 0) {
            g.drawString(time, 1100, 500);
        } else {
            g.drawString("Times Up!!", 1100, 500);
        }

        timer--; //decrements timer by 1 on every repaint.

        try {
            //Repaints Screen after every 1 second.
            Thread.sleep(1000);
            repaint();

        } catch (Exception e) {
            e.printStackTrace(); //Catched any Error in Runtime.
        }

        if (ans_given) {
            //check if timer reaches 0, it resets back to 15.
            ans_given = false;
            timer = 15;
        } else if (timer < 0) {
            //checks if user has pressed next button and resets timer back to 15.
            timer = 15;

            //will enable all options if lifeline is used in the previos question.
            opt1.setEnabled(true);
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);

            if (count == 8) {
                //Submit Button will be Disabled until last Question.
                //Next Button will be Enabled until last Question.
                next.setEnabled(false);
                submit.setEnabled(true);
            }
            if (count == 9) {
                if (options.getSelection() == null) {
                    //if user does not chose any option within 15 sec, Empty String will be saved in the Answer Array.
                    pa[count][0] = "";
                } else {
                    //Value of choosen answer will be fetched and stored in the 1st memory block of the Answer Array.
                    pa[count][0] = options.getSelection().getActionCommand();
                }

                //if Time ends, It matches all the Answers Choosen saved in pa[][] Array to the Array with correct Answers qa[][].
                for (int i = 0; i < pa.length; i++) {
                    if (pa[i][0].equals(qa[i][0])) {
                        score += 10; //if answers right, Total Score will increment by 10.
                    } else {
                        score += 0; //if answers wrong, Total Score will increment by 0.
                    }
                }

                Add();
                count = 0;

                this.dispose(); // Closes Quiz Screen.
                new Score(username, score, password).setVisible(true);
                score = 0;
                //Opens Score Screen and passes username, Calculated Score as arguments.

            } else {
                if (options.getSelection() == null) {
                    //if user does not chose any option within 15 sec, Empty String will be saved in the Answer Array.
                    pa[count][0] = "";
                } else {
                    //Value of choosen answer will be fetched and stored in the 1st memory block of the Answer Array.
                    pa[count][0] = options.getSelection().getActionCommand();
                }
                count++; //Next Question will show up on Time end.
                start(count); //calls start() function and passes real question count as argument to update initial values.
            }
        }
    }

    //Start() takes count as parameter and Calculates Dynamically
    //No. of Question, Title of Question, Options of Question, Correct Answer, Choosed Answer.
    public void start(int count) {
        qno.setText(count + 1 + ". "); //Question Number.
        question.setText(q[count][0]); //Question Title.

        opt1.setText(q[count][1]);          //Takes the Values of the chosen option.
        opt1.setActionCommand(q[count][1]); //and stores that value in Option 1.

        opt2.setText(q[count][2]);
        opt2.setActionCommand(q[count][2]);

        opt3.setText(q[count][3]);
        opt3.setActionCommand(q[count][3]);

        opt4.setText(q[count][4]);
        opt4.setActionCommand(q[count][4]);

        options.clearSelection(); //let's user to to choose any option.
    }

    public void Add() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String path = "jdbc:ucanaccess://C:\\Quiz_App\\src\\database\\data.accdb";
            Connection connection = DriverManager.getConnection(path);
            java.sql.Statement statement = connection.createStatement();
            String qry = "INSERT INTO ScoreBoard (Name,Score) VALUES ('" + username + "'," + score + ")";
            statement.executeUpdate(qry);
            System.out.println(username + " has been successfully Stored in DataBase having " + score + " Points");

        } catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //empty String is passed on startup because the real Name will be passed from the Rules Screen.
        new Quiz("","").setVisible(true);
    }

}
