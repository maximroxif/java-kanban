package manager;

import java.io.IOError;
import java.io.IOException;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(String message) {
        super(message);
    }
}
