import java.io.*;
import java.util.List;

/**
 * Controls the process of user logging in
 */


public class LoginController {

    private LoginUseCase loginUseCase;

    // Constructor
    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void runLogin(String username, String password) {
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
