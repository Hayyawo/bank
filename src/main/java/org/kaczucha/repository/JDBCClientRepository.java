package org.kaczucha.repository;

import org.kaczucha.repository.entity.Client;

import java.sql.*;
import java.util.Optional;

public class JDBCClientRepository implements ClientRepository {

    public static final String USER = "postgres";
    public static final String PASSWORD = "admin";
    public static final String JDBC_URL = "jdbc:postgresql//localhost:5432/test";

    @Override
    public void save(Client client) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String name = client.getName();
            String email = client.getEmail();
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS(FIRST_NAME,MAIL) VALUES (?,?)");
            statement.setString(1, name);
            statement.setString(2, email);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClient(String email) {

    }

    @Override
    public Optional<Client> findByEmail(String email) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            final PreparedStatement statement = connection.prepareStatement("SELECT FIRST_NAME,MAIL FROM USERS" +
                    "WHERE MAIL = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("FIRST_NAME");
                String mail = resultSet.getString("MAIL");
                return Optional.of(new Client(name, email, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean notExistsByEmail(String email) {
        return false;
    }
}
