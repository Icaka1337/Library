package com.example.bookmanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class LibraryApp extends Application {

    private AuthorDAO authorDAO = new AuthorDAO();
    private GenreDAO genreDAO = new GenreDAO();
    private BookDAO bookDAO = new BookDAO();

    private TableView<Author> authorTable = new TableView<>();
    private TableView<Genre> genreTable = new TableView<>();
    private TableView<Book> bookTable = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        TabPane tabPane = new TabPane();

        Tab authorsTab = new Tab("Authors", createAuthorsPane());
        Tab genresTab = new Tab("Genres", createGenresPane());
        Tab booksTab = new Tab("Books", createBooksPane());

        tabPane.getTabs().addAll(authorsTab, genresTab, booksTab);

        VBox vbox = new VBox(tabPane);
        Scene scene = new Scene(vbox, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createAuthorsPane() {
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField birthdateField = new TextField();
        birthdateField.setPromptText("Birthdate (DD-MM-YYYY)");

        Button addButton = new Button("Add Author");
        addButton.setOnAction(e -> {
            try {
                authorDAO.insertAuthor(new Author(0, nameField.getText(), birthdateField.getText()));
                refreshAuthorTable();
                nameField.clear();
                birthdateField.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button updateButton = new Button("Update Author");
        updateButton.setOnAction(e -> {
            Author selectedAuthor = authorTable.getSelectionModel().getSelectedItem();
            if (selectedAuthor != null) {
                try {
                    selectedAuthor.setName(nameField.getText());
                    selectedAuthor.setBirthdate(birthdateField.getText());
                    authorDAO.updateAuthor(selectedAuthor);
                    refreshAuthorTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button deleteButton = new Button("Delete Author");
        deleteButton.setOnAction(e -> {
            Author selectedAuthor = authorTable.getSelectionModel().getSelectedItem();
            if (selectedAuthor != null) {
                try {
                    authorDAO.deleteAuthor(selectedAuthor.getAuthorId());
                    refreshAuthorTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        TextField searchField = new TextField();
        searchField.setPromptText("Search by Name");
        Button searchButton = new Button("Search");
        Button clearSearchButton = new Button("Clear Search");
        searchButton.setOnAction(e -> {
            try {
                String keyword = searchField.getText();
                List<Author> authors = authorDAO.searchAuthorsByName(keyword);
                authorTable.setItems(FXCollections.observableArrayList(authors));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        clearSearchButton.setOnAction(e -> {
            refreshAuthorTable();
            searchField.clear();
        });

        authorTable.setItems(getAuthors());

        TableColumn<Author, Integer> authorIdColumn = new TableColumn<>("ID");
        authorIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getAuthorId()).asObject());

        TableColumn<Author, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Author, String> birthdateColumn = new TableColumn<>("Birthdate");
        birthdateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBirthdate()));

        authorTable.getColumns().addAll(authorIdColumn, nameColumn, birthdateColumn);

        VBox vbox = new VBox(nameField, birthdateField, addButton, updateButton, deleteButton, searchField, searchButton,clearSearchButton, authorTable);
        return vbox;
    }

    private VBox createGenresPane() {
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        Button addButton = new Button("Add Genre");
        addButton.setOnAction(e -> {
            try {
                genreDAO.insertGenre(new Genre(0, nameField.getText()));
                refreshGenreTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button updateButton = new Button("Update Genre");
        updateButton.setOnAction(e -> {
            Genre selectedGenre = genreTable.getSelectionModel().getSelectedItem();
            if (selectedGenre != null) {
                try {
                    selectedGenre.setName(nameField.getText());
                    genreDAO.updateGenre(selectedGenre);
                    refreshGenreTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button deleteButton = new Button("Delete Genre");
        deleteButton.setOnAction(e -> {
            Genre selectedGenre = genreTable.getSelectionModel().getSelectedItem();
            if (selectedGenre != null) {
                try {
                    genreDAO.deleteGenre(selectedGenre.getGenreId());
                    refreshGenreTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        TextField searchField = new TextField();
        searchField.setPromptText("Search by Name");
        Button searchButton = new Button("Search");
        Button clearSearchButton = new Button("Clear Search");
        searchButton.setOnAction(e -> {
            try {
                String keyword = searchField.getText();
                List<Genre> genres = genreDAO.searchGenresByName(keyword);
                genreTable.setItems(FXCollections.observableArrayList(genres));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        clearSearchButton.setOnAction(e -> {
            refreshGenreTable();
            searchField.clear();
        });

        genreTable.setItems(getGenres());

        TableColumn<Genre, Integer> genreIdColumn = new TableColumn<>("ID");
        genreIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getGenreId()).asObject());

        TableColumn<Genre, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));

        genreTable.getColumns().addAll(genreIdColumn, nameColumn);

        VBox vbox = new VBox(nameField, addButton, updateButton, deleteButton, searchField, searchButton,clearSearchButton, genreTable);
        return vbox;
    }

    private VBox createBooksPane() {
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorIdField = new TextField();
        authorIdField.setPromptText("Author ID");
        TextField genreIdField = new TextField();
        genreIdField.setPromptText("Genre ID");

        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> {
            try {
                bookDAO.insertBook(new Book(0, titleField.getText(), Integer.parseInt(authorIdField.getText()), Integer.parseInt(genreIdField.getText())));
                refreshBookTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button updateButton = new Button("Update Book");
        updateButton.setOnAction(e -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                try {
                    selectedBook.setTitle(titleField.getText());
                    selectedBook.setAuthorId(Integer.parseInt(authorIdField.getText()));
                    selectedBook.setGenreId(Integer.parseInt(genreIdField.getText()));
                    bookDAO.updateBook(selectedBook);
                    refreshBookTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button deleteButton = new Button("Delete Book");
        deleteButton.setOnAction(e -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                try {
                    bookDAO.deleteBook(selectedBook.getBookId());
                    refreshBookTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        TextField searchField = new TextField();
        searchField.setPromptText("Search by Title");
        Button searchButton = new Button("Search");
        Button clearSearchButton = new Button("Clear Search");
        searchButton.setOnAction(e -> {
            try {
                String keyword = searchField.getText();
                List<Book> books = bookDAO.searchBooksByTitle(keyword);
                bookTable.setItems(FXCollections.observableArrayList(books));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        clearSearchButton.setOnAction(e -> {
            refreshBookTable();
            searchField.clear();
        });

        bookTable.setItems(getBooks());

        TableColumn<Book, Integer> bookIdColumn = new TableColumn<>("ID");
        bookIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getBookId()).asObject());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<Book, Integer> authorIdColumn = new TableColumn<>("Author ID");
        authorIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getAuthorId()).asObject());

        TableColumn<Book, Integer> genreIdColumn = new TableColumn<>("Genre ID");
        genreIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getGenreId()).asObject());

        TableColumn<Book, String> authorNameColumn = new TableColumn<>("Author Name");
        authorNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthorName()));

        TableColumn<Book, String> genreNameColumn = new TableColumn<>("Genre Name");
        genreNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGenreName()));

        bookTable.getColumns().addAll(bookIdColumn, titleColumn, authorIdColumn, genreIdColumn, authorNameColumn, genreNameColumn);

        VBox vbox = new VBox(titleField, authorIdField, genreIdField, addButton, updateButton, deleteButton, searchField, searchButton,clearSearchButton, bookTable);
        return vbox;
    }

    private ObservableList<Author> getAuthors() {
        ObservableList<Author> authors = FXCollections.observableArrayList();
        try {
            List<Author> authorList = authorDAO.getAllAuthors();
            authors.addAll(authorList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    private ObservableList<Genre> getGenres() {
        ObservableList<Genre> genres = FXCollections.observableArrayList();
        try {
            List<Genre> genreList = genreDAO.getAllGenres();
            genres.addAll(genreList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    private ObservableList<Book> getBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            List<Book> bookList = bookDAO.getAllBooks();
            books.addAll(bookList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private void refreshAuthorTable() {
        authorTable.setItems(getAuthors());
    }

    private void refreshGenreTable() {
        genreTable.setItems(getGenres());
    }

    private void refreshBookTable() {
        bookTable.setItems(getBooks());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
