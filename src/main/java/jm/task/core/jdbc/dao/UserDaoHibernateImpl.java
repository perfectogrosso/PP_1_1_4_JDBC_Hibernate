package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
//        String query = "CREATE TABLE USERS (" +
//                "ID BIGINT(19) NOT NULL AUTO_INCREMENT UNIQUE," +
//                "NAME VARCHAR(64) NOT NULL," +
//                "LAST_NAME VARCHAR(64) NOT NULL," +
//                "AGE TINYINT NOT NULL," +
//                "CONSTRAINT PRIMARY KEY (ID)" +
//                ");";
//
//        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query);) {
//            if (!isTableExists("USERS")) {
//                preparedStatement.execute();
//                System.out.println("Таблица USERS создана");
//            } else {
//                System.out.println("Таблица USERS уже существует");
//            }
//        }
    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }

//    public boolean isTableExists(String tableName) {
//        String query = "SHOW TABLES;";
//        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                if (resultSet.getString(1).equals(tableName)) {
//                    return true;
//                }
//            }
//            return false;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public boolean isUserExists(long id) {
//        String query = "SELECT * FROM USERS WHERE ID = ?;";
//        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            return resultSet.next();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
