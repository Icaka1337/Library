package com.example.bookmanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {

    public List<Genre> getAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT * FROM Genres";
        try (Connection connection = DatabaseService.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int genreId = resultSet.getInt("genre_id");
                String name = resultSet.getString("name");
                genres.add(new Genre(genreId, name));
            }
        }
        return genres;
    }

    public void insertGenre(Genre genre) throws SQLException {
        String query = "INSERT INTO Genres (name) VALUES (?)";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, genre.getName());
            preparedStatement.executeUpdate();
        }
    }

    public void updateGenre(Genre genre) throws SQLException {
        String query = "UPDATE Genres SET name = ? WHERE genre_id = ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setInt(2, genre.getGenreId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteGenre(int genreId) throws SQLException {
        String query = "DELETE FROM Genres WHERE genre_id = ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, genreId);
            preparedStatement.executeUpdate();
        }
    }
    public List<Genre> searchGenresByName(String name) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM Genres WHERE name LIKE ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int genreId = resultSet.getInt("genre_id");
                    String genreName = resultSet.getString("name");
                    Genre genre = new Genre(genreId, genreName);
                    genres.add(genre);
                }
            }
        }
        return genres;
    }

}

