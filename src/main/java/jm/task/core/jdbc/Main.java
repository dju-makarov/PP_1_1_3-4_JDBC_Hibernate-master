package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.service.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("ivan", "petrov", (byte) (25));
        userService.saveUser("elena", "sergeeva", (byte) (57));
        userService.saveUser("mark", "sidorov", (byte) (45));
        userService.saveUser("anton", "ivanov", (byte) (11));
        userService.getAllUsers().forEach(e -> System.out.println(e.toString()));


    }
}
