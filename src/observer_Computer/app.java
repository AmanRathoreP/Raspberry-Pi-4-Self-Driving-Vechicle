
import src.basic_utilities;
import src.my_logger;

import java.lang.Thread;

public class app {
    public static void main(String[] args) throws InterruptedException {
        basic_utilities obj = new basic_utilities();
        new Thread(obj).start();
        my_logger logger = new my_logger("logs\\logs.log");
        while (true) {
            logger.addLog(obj.received_data);
            Thread.sleep(100);
        }
    }
}