//package UseCases.RegisterLogin;
//
//import Database.*;
//import Database.impl.UserInfoDB2;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextField;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class LoginGUI implements ActionListener{
//
//    private JFrame frame;
//    private JPanel jPanel;
//
//    public LoginGUI(){
//
//        // creat an JFrame object: frame
//        this.frame = new JFrame("Welcome to the Chess Entities.Game");
//        // Setting the width and height of frame
//        frame.setSize(500, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        /* creat an JPanel object
//         * we can create multiple JPanel objects and add them to frame
//         * we can add text, button and others in panels
//         */
//        this.jPanel = new JPanel();
//        // add panel to frame
//        this.frame.add(this.jPanel);
//
//        /*
//         * call our placeComponents method to set up our panel
//         */
//        placeComponents(jPanel);
//
//    }
//
//    public void run(){
//        // set frame to be visible
//        this.frame.setVisible(true);
//    }
//
//    public void close(){
//        this.frame.setVisible(false);
//    }
//
////    public static void main(String[] args) {
////        // creat an JFrame object: frame
////        JFrame frame = new JFrame("Welcome to the Chess Entities.Game");
////        // Setting the width and height of frame
////        frame.setSize(500, 300);
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////
////        /* creat an JPanel object
////         * we can create multiple JPanel objects and add them to frame
////         * we can add text, button and others in panels
////         */
////        JPanel panel = new JPanel();
////        // add panel to frame
////        frame.add(panel);
////
////        /*
////         * call our placeComponents method to set up our panel
////         */
////        placeComponents(panel);
////
////        // set frame to be visible
////        frame.setVisible(true);
////
////    }
//
//    private void placeComponents(JPanel panel) {
//
//        panel.setLayout(null);
//
//        // create a JLabel for username and set its position and size
//        JLabel userLabel = new JLabel("User:");
//        userLabel.setBounds(50,30,80,25);
//        panel.add(userLabel);
//
//        // create a JTextField for user to type username and set up its position and size
//        JTextField userText = new JTextField(20);
//        userText.setBounds(120,30,165,25);
//        panel.add(userText);
//
//        // create a JLabel for password and set its position and size
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordLabel.setBounds(50,60,80,25);
//        panel.add(passwordLabel);
//
//        // create a JTextField for user to type password and set up its position and size
//        JPasswordField passwordText = new JPasswordField(20);
//        passwordText.setBounds(120,60,165,25);
//        panel.add(passwordText);
//
//        // create a JButton for login
//        JButton loginButton = new JButton("login");
//        loginButton.setBounds(150, 100, 80, 25);
//        panel.add(loginButton);
//
//        loginButton.addActionListener(this);
//
//    }
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        int count = this.jPanel.getComponentCount();
//        String username = "";
//        String password = "";
//        for (int i = 0; i < count; i++){
//            Component c = this.jPanel.getComponent(i);
//            if (c instanceof JPasswordField) {
//                password = new String(((JPasswordField) c).getPassword());
//                // System.out.println("我是密码" + password);
//            }else if(c instanceof JTextField){
//                username = new String(((JTextField) c).getText());
//                // System.out.println("我是姓名" + username);
//            }
//        }
//
//        LoginUseCase.LoginResult res = logIn(username, password);
//
//        // check if there is a resultLabel in the jPanel
//        try {
//            Component x = jPanel.getComponent(5);
//            // a. there is an existing JLabel
//            ((JLabel) x).setText(res.toString());
//        }catch (IndexOutOfBoundsException ee) {
//            // b. not exists -> create a JLabel for the login_info
//            JLabel resultLabel = new JLabel(res.toString());
//            resultLabel.setBounds(70,150,150,25);
//
//            jPanel.add(resultLabel, 5);
//        };
//
//        // display the updated Jframe
//        this.close();
//        this.run();
//
//    }
//
//    private LoginUseCase.LoginResult logIn(String username, String password) {
//        Database database  = new UserInfoDB2();     // Dependency Injection!!!
//        boolean res = database.checkUserExistence(username);
//
//        boolean res2 = database.checkUserPassword(username, password);
//
//        if (res) {
//            // a. the password matches
//            if (res2){
//                return LoginUseCase.LoginResult.SUCCESS;
//            }else {
//            // b. the password does not match
//            return LoginUseCase.LoginResult.PASSWORD_WRONG;}
//
//        }else{
//            // c. the user does not exist
//            return LoginUseCase.LoginResult.NO_SUCH_USER;
//
//        }
//
//    }
//
//    public static void main(String[] args){
//        LoginGUI loginGUI = new LoginGUI();
//        loginGUI.run();
//
//    }
//
//}
