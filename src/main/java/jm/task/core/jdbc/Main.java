package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Ivan", "Ivanov", (byte) 25);
        userDaoJDBC.saveUser("Petr", "Petrov", (byte) 30);
        userDaoJDBC.saveUser("Arnold", "Stallone", (byte) 55);
        userDaoJDBC.saveUser("Vasily", "Vasilev", (byte) 35);

        List<User> users = new ArrayList<>();
        users.forEach(System.out::println);

        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
