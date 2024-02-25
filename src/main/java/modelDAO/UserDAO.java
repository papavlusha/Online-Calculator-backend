package modelDAO;

import jakarta.persistence.*;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import model.User;

import java.sql.SQLException;
import java.util.List;

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

    public static User getUserById(int userId) throws DAOException {
        User user = null;
        EntityManager em = null;
        try {
            em = ConnectionPool.getConnection();
            Query query = em.createNamedQuery("getUserById");
            query.setParameter("userId", userId);
            user = (User) query.getSingleResult();
            if (user == null)
                throw new SQLException("Receiving user failed, no user found");
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException ignored) {}
            }
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    public static List<User> getAllAdmins() throws DAOException {
        List<User> admins;
        EntityManager em = null;
        try {
            em = ConnectionPool.getConnection();
            Query query = em.createNamedQuery("getAllAdmins");
            admins = (List<User>)query.getResultList();
            if (admins.isEmpty())
                throw new SQLException("Receiving admins failed, no admins found");
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException ignored) {
                }
            }
        }
        return admins;
    }
}
