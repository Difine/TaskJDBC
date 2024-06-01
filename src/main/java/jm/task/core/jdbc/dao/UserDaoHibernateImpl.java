package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    @Override
    public void createUsersTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT AUTO_INCREMENT NOT NULL, " +
                "name VARCHAR(30) NOT NULL, " +
                "lastName VARCHAR(30) NOT NULL, " +
                "age TINYINT UNSIGNED NOT NULL, " +
                "PRIMARY KEY (id))";
        try (SessionFactory factory = Util.getSessionFactory();
             Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(createTableQuery).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public void dropUsersTable() {
        String deleteTableQuery = "DROP TABLE IF EXISTS users";
        try (SessionFactory factory = Util.getSessionFactory();
             Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(deleteTableQuery).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (SessionFactory factory = Util.getSessionFactory();
             Session session = factory.getCurrentSession()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем " + name + " добавлен в базу данных!" );
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public void removeUserById(long id) {
        String removeUserIdQuery = "delete User where id = " + id;
        try (SessionFactory factory = Util.getSessionFactory();
             Session session = factory.getCurrentSession()){
            session.beginTransaction();
            session.createQuery(removeUserIdQuery).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }

    }
    @Override
    public List<User> getAllUsers() {
        String getUsersQuery = "from User";
        List users;
        try (SessionFactory factory = Util.getSessionFactory();
             Session session = factory.getCurrentSession()){
            session.beginTransaction();
            users = session.createQuery(getUsersQuery).getResultList();
        } catch (Exception e) {
            throw e;
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        String cleanUsersQuery = "delete User";
        try (SessionFactory factory = Util.getSessionFactory();
             Session session = factory.getCurrentSession()){
            session.beginTransaction();
            session.createQuery(cleanUsersQuery).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
}
