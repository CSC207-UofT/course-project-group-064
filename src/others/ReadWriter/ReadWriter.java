package ReadWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface ReadWriter<K, V> {

    public HashMap<K, V> readFromFile(String fileName);

    public void saveToFile();
}
