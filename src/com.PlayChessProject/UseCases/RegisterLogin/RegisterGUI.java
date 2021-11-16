package UseCases.RegisterLogin;

import Exceptions.UserAlreadyExistsException;
import Database.Database;
import Database.UserInfoDB2;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Entities.Users.User;


public class RegisterGUI implements ActionListener{

    private JFrame frame;
    private JPanel jPanel;

    public RegisterGUI(){

    // creat an JFrame object: frame
        this.frame = new JFrame("Welcome to the Chess Entities.Game");
    // Setting the width and height of frame
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    /* creat an JPanel object
     * we can create multiple JPanel objects and add them to frame
     * we can add text, button and others in panels
     */
        this.jPanel = new JPanel();
    // add panel to frame
        this.frame.add(this.jPanel);

    /*
     * call our placeComponents method to set up our panel
     */
    placeComponents(jPanel);

    }

    public void run(){
        // set frame to be visible
        this.frame.setVisible(true);
    }

    public void close(){
        this.frame.setVisible(false);
    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);

        // create a JLabel for username and set its position and size
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(50,30,80,25);
        panel.add(userLabel, 0);

        // create a JTextField for user to type username and set up its position and size
        JTextField userText = new JTextField(20);
        userText.setBounds(120,30,165,25);
        panel.add(userText, 1);

        // create a JLabel for password and set its position and size
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50,60,80,25);
        panel.add(passwordLabel, 2);

        // create a JTextField for user to type password and set up its position and size
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(120,60,165,25);
        panel.add(passwordText, 3);

        // create a JButton for login
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 100, 80, 25);
        panel.add(registerButton, 4);

        registerButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Component c = jPanel.getComponent(1);
        String username = ((JTextField) c).getText();

        c = jPanel.getComponent(3);
        String password = new String (((JPasswordField) c).getPassword());

        Database database = new UserInfoDB2();
        String msg = "";
        try {
            database.addUserInfo(new User(username, "0"), password);
            msg = "The User Successfully Registered!";

        } catch (UserAlreadyExistsException ex) {
            msg = ex.getMessage();
        }

        // check if there is a resultLabel in the jPanel
        try {
            Component x = jPanel.getComponent(5);
            // a. there is an existing JLabel
            ((JLabel) x).setText(msg);
        }catch (IndexOutOfBoundsException ee) {
            // b. not exists -> create a JLabel for the login_info
            JLabel resultLabel = new JLabel(msg);
            resultLabel.setBounds(70,150,300,25);

            jPanel.add(resultLabel, 5);
        };


        // display the updated Jframe
        this.close();
        this.run();

    }

    public static void main(String[] args) {
        RegisterGUI registerGUI = new RegisterGUI();
        registerGUI.run();
    }
}
