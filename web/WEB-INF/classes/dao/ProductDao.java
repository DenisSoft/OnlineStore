package dao;

import connection.ConnectionManager;
import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Dzianis on 09.04.2017.
 */
public class ProductDao {

    private static final Object LOCK = new Object();
    private static dao.ProductDao INSTANCE = null;

    public static dao.ProductDao getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new dao.ProductDao();
                }
            }
        }
        return INSTANCE;
    }

    public static boolean delete(long id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id FROM orderitem WHERE product_id = ?")) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return false;
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM product WHERE id = ?")) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Optional<Product> getById(long id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM product WHERE id = ?")) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(creatPerson(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<Product> save(Product product) {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product (category_id, product_name, description, price, residue)" +
                            "VALUES (?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setLong(1, product.getCategoryId());
                preparedStatement.setString(2, product.getProductName());
                preparedStatement.setString(3, product.getDescription());
                preparedStatement.setDouble(4, product.getPrice());
                preparedStatement.setInt(5, product.getResidue());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                    return Optional.of(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static Product creatPerson(ResultSet resultSet) {
        try {
            return new Product(resultSet.getLong("id"),
                    resultSet.getLong("category_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("residue"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<List<Product>> getAll() {
        List<Product> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM product")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(creatPerson(resultSet));
                    }
                }
                return Optional.of(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
