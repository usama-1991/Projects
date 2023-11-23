import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AdminPanel {
    JFrame frame;
    JButton backButton;
    JButton editButton;
    JTextField questionField;
    JTextField option1Field;
    JTextField option2Field;
    JTextField option3Field;
    JTextField option4Field;
    JTextField answerField;
    JLabel questionLabel;
    JLabel option1Label;
    JLabel option2Label;
    JLabel option3Label;
    JLabel option4Label;
    JLabel answerLabel;
    JButton nextButton;
    int questionNumber;
    Color MainColor = new Color(30, 144, 254);
    Color PrimaryColor = new Color(160, 32, 240);

    public AdminPanel() {
        // Create the frame and set its properties
        frame = new JFrame("Admin Panel");
        frame.setSize(1200, 600);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().setBackground(Color.white);
        frame.setDefaultCloseOperation(SimpleMinds.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create the buttons and add their action listeners
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SimpleMinds();
            }
        });
        editButton = new JButton("Edit all questions");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButton.setVisible(false);
                editButton.setVisible(false);
                try {
                    FileWriter writer = new FileWriter("questions.txt");
                    writer.write("");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                showQuestionFields();
            }
        });

        // Create the text fields and labels
        questionField = new JTextField();
        questionField.setPreferredSize(new Dimension(1100, 80));
        option1Field = new JTextField();
        option1Field.setPreferredSize(new Dimension(550, 30));
        option2Field = new JTextField();
        option2Field.setPreferredSize(new Dimension(550, 30));
        option3Field = new JTextField();
        option3Field.setPreferredSize(new Dimension(550, 30));
        option4Field = new JTextField();
        option4Field.setPreferredSize(new Dimension(550, 30));
        answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(550, 30));
        questionLabel = new JLabel("Question 1:");
        option1Label = new JLabel("Option 1:");
        option2Label = new JLabel("Option 2:");
        option3Label = new JLabel("Option 3:");
        option4Label = new JLabel("Option 4:");
        answerLabel = new JLabel("Answer:");

        // Create the next button and add its action listener
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = questionField.getText();
                String option1 = option1Field.getText();
                String option2 = option2Field.getText();
                String option3 = option3Field.getText();
                String option4 = option4Field.getText();
                String answer = answerField.getText();

                try (BufferedWriter bw = new BufferedWriter(new FileWriter("questions.txt", true))) {
                    bw.write(question + "," + option1 + "," + option2 + "," + option3 + "," + option4 + "," + answer + "\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                questionNumber++;
                if (questionNumber == 10) {
                    frame.dispose();
                    new SimpleMinds();
                } else {
                    questionField.setText("");
                    option1Field.setText("");
                    option2Field.setText("");
                    option3Field.setText("");
                    option4Field.setText("");
                    answerField.setText("");
                    questionLabel.setText("Question " + (questionNumber + 1) + ":");
                }
            }
        });


        editButton.setBackground(MainColor);
        editButton.setForeground(Color.white);
        editButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 18)); // use the same font as in SimpleMinds

        backButton.setBackground(PrimaryColor);
        backButton.setForeground(Color.white);
        backButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));

        nextButton.setBackground(PrimaryColor);
        nextButton.setForeground(Color.white);
        nextButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        frame.add(backButton);
        frame.add(editButton);
        frame.setVisible(true);
    }

    public void showQuestionFields() {
        frame.add(questionLabel);
        frame.add(questionField);
        frame.add(option1Label);
        frame.add(option1Field);
        frame.add(option2Label);
        frame.add(option2Field);
        frame.add(option3Label);
        frame.add(option3Field);
        frame.add(option4Label);
        frame.add(option4Field);
        frame.add(answerLabel);
        frame.add(answerField);
        frame.add(nextButton);
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}
