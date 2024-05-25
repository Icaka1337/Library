package com.example.bookmanager;

public class Author {
    final int authorId;
    private String name;
    private String birthdate;

    public Author(int authorId, String name, String birthdate) {
        this.authorId = authorId;
        this.name = name;
        this.birthdate = birthdate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}

