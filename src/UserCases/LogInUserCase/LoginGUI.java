package LogInUserCase;

import database.UserInfoDB;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;


public class LoginGUI implements ActionListener{

    private JFrame frame;
    private JPanel jPanel;

    public LoginGUI(){

        // creat an JFrame object: frame
        this.frame = new JFrame("Welcome to the Chess Game");
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

//    public static void main(String[] args) {
//        // creat an JFrame object: frame
//        JFrame frame = new JFrame("Welcome to the Chess Game");
//        // Setting the width and height of frame
//        frame.setSize(500, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        /* creat an JPanel object
//         * we can create multiple JPanel objects and add them to frame
//         * we can add text, button and others in panels
//         */
//        JPanel panel = new JPanel();
//        // add panel to frame
//        frame.add(panel);
//
//        /*
//         * call our placeComponents method to set up our panel
//         */
//        placeComponents(panel);
//
//        // set frame to be visible
//        frame.setVisible(true);
//
//    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);

        // create a JLabel for username and set its position and size
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(50,30,80,25);
        panel.add(userLabel);

        // create a JTextField for user to type username and set up its position and size
        JTextField userText = new JTextField(20);
        userText.setBounds(120,30,165,25);
        panel.add(userText);

        // create a JLabel for password and set its position and size
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50,60,80,25);
        panel.add(passwordLabel);

        // create a JTextField for user to type password and set up its position and size
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(120,60,165,25);
        panel.add(passwordText);

        // create a JButton for login
        JButton loginButton = new JButton("login");
        loginButton.setBounds(150, 100, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int count = this.jPanel.getComponentCount();
        String username = "";
        String password = "";
        for (int i = 0; i < count; i++){
            Component c = this.jPanel.getComponent(i);
            if (c instanceof JTextField) {
                username = ((JTextField) c).getText();
            }else if(c instanceof JPasswordField){
                password = ((JPasswordField) c).getPassword().toString();
            }
        }

        String res = logIn(username, password).toString();

        // create a JLabel for the login_info
        JLabel resultLabel = new JLabel(res);
        resultLabel.setBounds(70,70,80,25);

        jPanel.add(resultLabel);

        // display the updated Jframe
        this.run();

    }

    public LoginUseCase.LoginResult logIn(String username, String password) {
        UserInfoDB userInFoDB = new UserInfoDB();
        int res = userInFoDB.readUserInfo(username, password);

        LoginUseCase.LoginResult result = null;

        switch(res){
            case 0: {
                result =  LoginUseCase.LoginResult.NO_SUCH_USER;
                break;
            } case 1: {
                result = LoginUseCase.LoginResult.PASSWORD_WRONG;
                break;
            } case 2: {
                result = LoginUseCase.LoginResult.SUCCESS;
                break;
            }
        }

        return result;
    }

    public static void main(String[] args){
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.run();

    }

}
