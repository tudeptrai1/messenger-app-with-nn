
package Network;

import java.util.concurrent.TimeUnit;



public class Config {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8888;
    public static final int DELAY_TIME = 1000;
    public static final int WIDTH_ROOM = 500;
    public static final int HEIGHT_ROOM = 200;
    public static final int WIDTH_MESSAGE = 500;
    public static final int HEIGHT_MESSAGE = 200;
    public static int LENGTH_KEY = 1024;
    public static int TIME_OTP = 600;
    public static int corePoolSize = 200;
    public static int maximumPoolSize = 500;
    public static long keepAliveTime = 200;
    public static TimeUnit unit = TimeUnit.SECONDS;
    public static long FILE_SIZE = 1000000;
}
