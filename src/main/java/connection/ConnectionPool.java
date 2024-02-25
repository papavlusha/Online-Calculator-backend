package connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import jakarta.persistence.*;

public class ConnectionPool {
    private static final BlockingQueue<EntityManager> pool;
    private static final String PERSISTENCE_UNIT_NAME = "simpleFactory";
    private static final EntityManagerFactory factory;
    private static final int POOL_SIZE = 1;

    static {
        try {
            pool = new LinkedBlockingQueue<>(POOL_SIZE);
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

            for (int i = 0; i < POOL_SIZE; i++)
                pool.put(factory.createEntityManager());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static EntityManager getConnection() throws ConnectionPoolException {
        try {
            return pool.take();
        } catch (InterruptedException | ExceptionInInitializerError e) {
            Thread.currentThread().interrupt();
            throw new ConnectionPoolException("Error: no available connections");
        }
    }

    public static void releaseConnection(EntityManager manager) throws ConnectionPoolException {
        try {
            manager.clear();
            pool.put(manager);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConnectionPoolException("Error: releasing connection failed");
        }
    }

    public static void closeFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
