package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.service.util.Util.getMySQLConnection;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connector;

    public UserDaoJDBCImpl() {
        this.connector = getMySQLConnection();
    }

    public void createUsersTable() {
        String SQL = "create table if not exists users (id  bigint auto_increment primary key, name varchar(30)  null, lastName varchar(30)  null, age tinyint null)";
        try (Statement statement = connector.createStatement()) {
            if (statement.executeUpdate(SQL) == -1) {
                connector.rollback();
            } else {
                System.out.println("Создана таблица Users");
                connector.commit();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String SQL = "drop table if exists users";
        try (Statement statement = connector.createStatement()) {
            if (statement.executeUpdate(SQL) == -1) {
                connector.rollback();
            } else {
                connector.commit();
                System.out.println("Дроп таблицы Users");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "insert into users (name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connector.prepareStatement(SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            if (preparedStatement.executeUpdate() != -1) {
                connector.commit();
                System.out.println(name + " добавлен в базу данных");
            } else {
                connector.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String SQL = "delete from users where id = (?)";
        try (PreparedStatement preparedStatement = connector.prepareStatement(SQL)) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() != -1) {
                connector.commit();
                System.out.println("Удален user с id = " + id);
            } else {
                connector.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String SQL = "select * from users";
        try (Statement statement = connector.createStatement()) {
            ResultSet set = statement.executeQuery(SQL);
            while (set.next()) {
                User user = new User();
                user.setId(set.getLong(1));
                user.setName(set.getString(2));
                user.setLastName(set.getString(3));
                user.setAge(set.getByte(4));
                users.add(user);
            }

            connector.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    public void cleanUsersTable() {
        String SQL = "TRUNCATE users";
        try (Statement statement = connector.createStatement()) {
            if(statement.executeUpdate(SQL) == -1) {
                connector.rollback();
            } else {
                connector.commit();
                System.out.println("Таблица очищена");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}