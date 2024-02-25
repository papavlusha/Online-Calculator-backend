package modelDAO;

import jakarta.persistence.*;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import model.User;

public class UserDAO {
    public static void insertUser(User user) throws DAOException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = ConnectionPool.getConnection();
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                throw new DAOException("Creating user failed, no rows affected");
            }
            throw new DAOException(e);
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException ignored) {}
            }
        }
    }

    public static void deleteUser(Long id) throws DAOException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = ConnectionPool.getConnection();
            transaction = em.getTransaction();
            transaction.begin();
            User userToDelete = em.find(User.class, id);
            if (userToDelete != null) {
                em.remove(userToDelete);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                throw new DAOException("Deleting user failed, no rows affected");
            }
            throw new DAOException(e);
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException ignored) {}
            }
        }
    }
}
