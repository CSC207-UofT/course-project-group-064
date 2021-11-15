package UseCases.RegisterLogin;

import Database.UserInfoDB;

import ReadWriter.ReadWriter;

public class LoginUseCase {

    //private final List<User> users;
    private ReadWriter readWriter;

    // Constructor
//    public LoginUseCase(String filePath) {
//        this.users = loadUsers();
//    }
//
//    /**load users
//     *
//     */
//    private List<User> loadUsers() {
//
//        List<User> users = new ArrayList<>();
//
//        /**
//         * Connect to sql database and read data from there
//         */
//
//
//        File file = new File("users.csv");
//
//        Reader r = null;
//
//        try {
//            r = new FileReader(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        BufferedReader br = new BufferedReader(r);
//
//        String line;
//        try {
//            while ((line = br.readLine()) != null) {
//
//                String[] res = line.split(",");
//                String username = res[0];
//                String password = res[1];
//
//                users.add(new User(username, password));
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return users;
//
//    }

    /**
     * / The "output" of this use case.
     */
    public enum LoginResult {
        SUCCESS, PASSWORD_WRONG, NO_SUCH_USER
    }

    /**
     * returns the loginResult for the particular username and password
     * @param username
     * @param password
     * @return 0 if the username does not exist, 1 if the username exists but the password does not match, 2 if
     *         both username and password match, or null if we have a connection to the database issue.
     */
//    public LoginResult logIn(String username, String password) {
//        UserInfoDB userInFoDB = new UserInfoDB();
//        int res = userInFoDB.readUserInfo(username, password);
//
//        LoginResult result = null;
//
//        switch(res){
//            case 0: {
//                result =  LoginResult.NO_SUCH_USER;
//                break;
//            } case 1: {
//                result = LoginResult.PASSWORD_WRONG;
//                break;
//            } case 2: {
//                result = LoginResult.SUCCESS;
//                break;
//            }
//        }
//
//        return result;
//    }

}
