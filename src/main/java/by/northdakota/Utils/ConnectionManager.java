package by.northdakota.Utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ConnectionManager {
    private final static String URL_KEY = "db.url";
    private final static String USER_KEY = "db.user";
    private final static String PASSWORD_KEY = "db.password";
    private final static int DEFAULT_POOL_SIZE = 10;
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static BlockingQueue<Connection> pool;

    static {
        try {
            loadDriver();
            initConnectionPool();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConnectionPool() throws SQLException {
        String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            Connection connection = getConnect();
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (
                            (proxy, method, args) ->
                                    method.getName().equals("close") ?
                                            pool.add((Connection) proxy) : method.invoke(connection,args))
            );
            pool.add(proxyConnection);
        }
    }

    public static Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    private static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USER_KEY),
                PropertiesUtil.get(PASSWORD_KEY)
        );
    }

    private static void loadDriver() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }
}
