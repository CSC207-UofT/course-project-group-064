import LogInUserCase.LoginUseCase;
import LogInUserCase.LoginGUI;

/**
 * Controls the process of user logging in
 */


public class LoginController {

    private LoginUseCase loginUseCase;
    private LoginGUI loginGUI;

    // Constructor
    public LoginController(LoginUseCase loginUseCase, LoginGUI loginGUI) {
        this.loginUseCase = loginUseCase;
        this.loginGUI = loginGUI;
    }

    public void runLogin(String username, String password) {

        // display login window
        this.loginGUI.run();

        // action on the logInResult
        LoginUseCase.LoginResult res = this.loginUseCase.logIn(username, password);

        switch (res) {
            case SUCCESS ->
                    // Should we be printing? How might you refactor this program
                    // to fit the Clean Architecture?
                    System.out.println("Success!");
            case NO_SUCH_USER -> System.out.println("No Such User!");
            case PASSWORD_WRONG -> System.out.println("Password Does Not Match Username!");
        }
    }

}
