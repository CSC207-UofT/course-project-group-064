package ReadWriter;

import java.io.*;
import java.util.HashMap;

public class ReadWriterImpl1 implements ReadWriter{


    @Override
    public HashMap<String, String> readFromFile(String csvFile) {

        Reader r = null;
        try {
            r = new FileReader(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(r);

        String separator = ",";

        HashMap<String, String> users = new HashMap<>();

        String line;

        try {
            while ((line = br.readLine()) != null) {
                String[] res = line.split(separator);
                String username = res[0];
                String password = res[1];
                users.put(username, password);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return users;

    }

    @Override
    public void saveToFile() {

    }
}
