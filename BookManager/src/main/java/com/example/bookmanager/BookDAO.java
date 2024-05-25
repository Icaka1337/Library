package com.example.bookmanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books";
        String queryAuthors = "SELECT * FROM Authors WHERE author_id = ?";
        String queryGenres = "SELECT * FROM Genres WHERE genre_id = ?";
        try (Connection connection = DatabaseService.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                int genreId = resultSet.getInt("genre_id");
                String authorName="";
                String genreName="";
                try(PreparedStatement preparedStatement = connection.prepareStatement(queryAuthors)) {
                    preparedStatement.setInt(1, authorId);
                    try (ResultSet resultSetAuthors = preparedStatement.executeQuery()) {
                        if (resultSetAuthors.next()) {
                            authorName = resultSetAuthors.getString("name");
                        }
                    }
                }
                try(PreparedStatement preparedStatement = connection.prepareStatement(queryGenres)) {
                    preparedStatement.setInt(1, genreId);
                    try (ResultSet resultSetGenres = preparedStatement.executeQuery()) {
                        if (resultSetGenres.next()) {
                            genreName = resultSetGenres.getString("name");
                        }
                    }
                }
                books.add(new Book(bookId, title, authorId, genreId, authorName, genreName));
            }
        }
        return books;
    }

    public void insertBook(Book book) throws SQLException {
        String query = "INSERT INTO Books (title, author_id, genre_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthorId());
            preparedStatement.setInt(3, book.getGenreId());
            preparedStatement.executeUpdate();
        }
    }

    public void updateBook(Book book) throws SQLException {
        String query = "UPDATE Books SET title = ?, author_id = ?, genre_id = ? WHERE book_id = ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthorId());
            preparedStatement.setInt(3, book.getGenreId());
            preparedStatement.setInt(4, book.getBookId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteBook(int bookId) throws SQLException {
        String query = "DELETE FROM Books WHERE book_id = ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
        }
    }

    public List<Book> searchBooksByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books WHERE title LIKE ?";
        String queryAuthors = "SELECT * FROM Authors WHERE author_id = ?";
        String queryGenres = "SELECT * FROM Genres WHERE genre_id = ?";
        try (Connection connection = DatabaseService.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + title + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    String bookTitle = resultSet.getString("title");
                    int authorId = resultSet.getInt("author_id");
                    int genreId = resultSet.getInt("genre_id");
                    String authorName = "";
                    String genreName = "";
                    try(PreparedStatement preparedStatement = connection.prepareStatement(queryAuthors)) {
                        preparedStatement.setInt(1, authorId);
                        try (ResultSet resultSetAuthors = preparedStatement.executeQuery()) {
                            if (resultSetAuthors.next()) {
                                authorName = resultSetAuthors.getString("name");
                            }
                        }
                    }
                    try(PreparedStatement preparedStatement = connection.prepareStatement(queryGenres)) {
                        preparedStatement.setInt(1, genreId);
                        try (ResultSet resultSetGenres = preparedStatement.executeQuery()) {
                            if (resultSetGenres.next()) {
                                genreName = resultSetGenres.getString("name");
                            }
                        }
                    }
                    Book book = new Book(bookId, bookTitle, authorId, genreId, authorName, genreName);
                    books.add(book);
                }
            }
        }
        return books;
    }
}


