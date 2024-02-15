package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
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

    @Override
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

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().getCurrentSession();

        session.beginTransaction();
        session.save(new User(name, lastName, age));
        System.out.println("User с именем " + name + " добавлен в БД");
        session.getTransaction();
    }

    @Override
    public void removeUserById(long id) {
        if (isUserExists(id)) {
            Session session = Util.getSessionFactory().getCurrentSession();

            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("User с id " + id + " удален с БД");
            session.getTransaction();
        } else {
            System.out.println("User с id " + id + " не существует");
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().getCurrentSession();

        session.beginTransaction();
        List<User> users = session.createQuery("FROM User").getResultList();
        session.getTransaction();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();

        session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        session.getTransaction();
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
