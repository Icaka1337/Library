package com.example.bookmanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT author_id, name, CONVERT(varchar, birthdate, 105) AS birthdate FROM Authors";
        try (Connection connection = DatabaseService.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int authorId = resultSet.getInt("author_id");
                String name = resultSet.getString("name");
                String birthdate = resultSet.getString("birthdate");
                authors.add(new Author(authorId, name, birthdate));
            }
        }
        return authors;
    }

    public void insertAuthor(Author author) throws SQLException {
        String query = "INSERT INTO Authors (name, birthdate) VALUES (?, CONVERT(date, ?, 105))";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getBirthdate());
            preparedStatement.executeUpdate();
        }
    }

    public void updateAuthor(Author author) throws SQLException {
        String query = "UPDATE Authors SET name = ?, birthdate = CONVERT(date, ?, 105) WHERE author_id = ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getBirthdate());
            preparedStatement.setInt(3, author.getAuthorId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteAuthor(int authorId) throws SQLException {
        String query = "DELETE FROM Authors WHERE author_id = ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, authorId);
            preparedStatement.executeUpdate();
        }
    }
    public List<Author> searchAuthorsByName(String name) throws SQLException {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT author_id, name, CONVERT(varchar, birthdate, 105) AS birthdate FROM Authors WHERE name LIKE ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int authorId = resultSet.getInt("author_id");
                    String authorName = resultSet.getString("name");
                    String birthdate = resultSet.getString("birthdate");
                    Author author = new Author(authorId, authorName, birthdate);
                    authors.add(author);
                }
            }
        }
        return authors;
    }

}

