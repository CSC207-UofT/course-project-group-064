import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LoginUseCase {

    private final List<User> users;
    private ReadWriter readWriter;

    // Constructor
    public LoginUseCase(String filePath) {
        this.users = loadUsers();
    }

    /**load users
     *
     */
    private List<User> loadUsers() {

        List<User> users = new ArrayList<>();

        File file = new File("users.csv");

        Reader r = null;

        try {
            r = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(r);

        String line;
        try {
            while ((line = br.readLine()) != null) {

                String[] res = line.split(",");
                String username = res[0];
                String password = res[1];

                users.add(new User(username, password));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return users;

    }

    /**
     * / The "output" of this use case.
     */
    public enum LoginResult {
        SUCCESS, PASSWORD_WRONG, NO_SUCH_USER
    }

    /**
     * Run the login use case.
     * @param username the username
     * @param password the password
     * @return the login result
     */
    public LoginResult logIn(String username, String password){

        for (User user: users) {

            if (user.getName().equals(username)) {
                if (user.passwordMatch(password)) {
                    return LoginResult.SUCCESS;
                }else {
                    return LoginResult.PASSWORD_WRONG;
                }
            }
        }

        return LoginResult.NO_SUCH_USER;

    }
}
