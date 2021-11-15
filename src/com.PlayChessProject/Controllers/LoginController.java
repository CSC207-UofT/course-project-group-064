package Controllers;

import UseCases.RegisterLogin.LoginUseCase;
import UseCases.RegisterLogin.LoginGUI;
import UseCases.RegisterLogin.RegisterGUI;

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

    public void runLogin(String username, String password) {

        // display login window
        this.loginGUI.run();

        // action on the logInResult
        LoginUseCase.LoginResult res = this.loginGUI.logIn(username, password);

        switch (res) {
            case SUCCESS ->
                    // Should we be printing? How might you refactor this program
                    // to fit the Clean Architecture?
                    System.out.println("Success!");
            case NO_SUCH_USER -> System.out.println("No Such User!");
            case PASSWORD_WRONG -> System.out.println("Password Does Not Match Username!");
        }
    }

    // TODO: to implement
    public void runRegister(){
        this.registerGUI.run();
    }

    public static void main(String[] args) {

        LoginGUI loginGUI = new LoginGUI();
        RegisterGUI registerGUI = new RegisterGUI();
        LoginController loginController = new LoginController(loginGUI, registerGUI);

        loginController.runRegister();

    }

}
