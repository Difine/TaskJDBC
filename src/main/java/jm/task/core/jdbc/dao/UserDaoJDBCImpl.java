package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    @Override
    public void createUsersTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT AUTO_INCREMENT NOT NULL, " +
                "name VARCHAR(30) NOT NULL, " +
                "lastName VARCHAR(30) NOT NULL, " +
                "age TINYINT UNSIGNED NOT NULL, " +
                "PRIMARY KEY (id))";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()){
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void dropUsersTable() {
        String deleteTableQuery = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUserQuery = "INSERT INTO users(id, name, lastName, age) VALUES(?,?,?,?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(saveUserQuery)) {
            statement.setLong(1, 0);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setByte(4, age);
            statement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных!" );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void removeUserById(long id) {
        String removeUserQuery = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(removeUserQuery)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAllUsersQuery = "SELECT * FROM users";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(getAllUsersQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    @Override
    public void cleanUsersTable() {
        String cleanUsersQuery = "DELETE FROM users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()){
            statement.executeUpdate(cleanUsersQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
