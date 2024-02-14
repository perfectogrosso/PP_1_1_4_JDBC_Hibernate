package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE USERS (" +
                "ID BIGINT(19) NOT NULL AUTO_INCREMENT UNIQUE," +
                "NAME VARCHAR(64) NOT NULL," +
                "LAST_NAME VARCHAR(64) NOT NULL," +
                "AGE TINYINT NOT NULL," +
                "CONSTRAINT PRIMARY KEY (ID)" +
                ");";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query);) {
            if (!isTableExists("USERS")) {
                preparedStatement.execute();
                System.out.println("Таблица USERS создана");
            } else {
                System.out.println("Таблица USERS уже существует");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE USERS;";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            if (isTableExists("USERS")) {
                preparedStatement.execute();
                System.out.println("Таблица USERS удалена");
            } else {
                System.out.println("Таблица USERS уже была удалена");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO USERS (NAME, LAST_NAME, AGE) VALUES (?,?,?);";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем " + name + " добавлен в БД");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM USERS WHERE ID = ?;";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            if (!isUserExists(id)) {
                System.out.println("User с id " + id + " не существует");
            } else {
                preparedStatement.setLong(1, id);
                preparedStatement.execute();
                System.out.println("User с id " + id + " удален с БД");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM USERS;";
        List<User> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM USERS;";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.execute();
            System.out.println("Данные с таблицы удалены");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTableExists(String tableName) {
        String query = "SHOW TABLES;";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(tableName)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUserExists(long id) {
        String query = "SELECT * FROM USERS WHERE ID = ?;";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
