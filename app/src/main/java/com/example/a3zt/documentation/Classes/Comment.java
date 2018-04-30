package com.example.a3zt.documentation.Classes;

public class Comment {
    private String Name;
    private String Comment;

    public Comment(String name, String comment) {
        Name = name;
        Comment = comment;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
