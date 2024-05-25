package com.example.bookmanager;

public class Book {
    final int bookId;
    private String title;
    private int authorId;
    private int genreId;
    private String authorName;
    private String genreName;


    public Book(int bookId, String title, int authorId, int genreId) {
        this.bookId = bookId;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }
    public Book(int bookId, String title, int authorId, int genreId, String authorName, String genreName) {
        this.bookId = bookId;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}

