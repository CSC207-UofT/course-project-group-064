package Controllers;

import UseCases.RegisterLogin.LoginUseCase;
import UseCases.RegisterLogin.LoginGUI;
import UseCases.RegisterLogin.RegisterGUI;

import java.util.Scanner;

/**
 * Controls the process of user logging in
 */


public class LoginController {

    private LoginUseCase loginUseCase;
    private LoginGUI loginGUI;
    private RegisterGUI registerGUI;

    // Constructor: use dependency injection: Controllers2.LoginController composites LoginGUI and RegisterGUI objects but we do not
    // create them in the Controllers2.LoginController class. Instead, we inject them to the constructor.
    public LoginController(LoginGUI loginGUI, RegisterGUI registerGUI) {
        // this.loginUseCase = loginUseCase;
        this.loginGUI = loginGUI;
        this.registerGUI = registerGUI;
    }

    public void runLogin() {

        // display login window
        this.loginGUI.run();

        // action on the logInResult
//        LoginUseCase.LoginResult res = this.loginGUI.run();
//
//        switch (res) {
//            case SUCCESS ->
//                    // Should we be printing? How might you refactor this program
//                    // to fit the Clean Architecture?
//                    System.out.println("Success!");
//            case NO_SUCH_USER -> System.out.println("No Such User!");
//            case PASSWORD_WRONG -> System.out.println("Password Does Not Match Username!");
//        }
    }

    // TODO: to implement
    public void runRegister(){
        this.registerGUI.run();
    }

    public static void main(String[] args) {

        LoginGUI loginGUI = new LoginGUI();
        RegisterGUI registerGUI = new RegisterGUI();
        LoginController loginController = new LoginController(loginGUI, registerGUI);

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Our Chess Game! \nPlease Enter 1 If You want to Register and 2 If You want to " +
                "Login:\n");

        String choice = sc.nextLine().strip();

        switch(choice){
            case "1":
                loginController.runRegister();
                break;
            case "2":
                loginController.runLogin();
                break;
        }


    }

}
