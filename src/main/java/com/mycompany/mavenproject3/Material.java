package com.mycompany.mavenproject3;

import java.io.Serializable;

public abstract class Material implements Serializable {
    private String title;
    private  String author;
    private  String publisher;
    private String genre;
    private  int yearPublished;
    private  final int materialID;
    private static int nextID = 1;
    protected int copies;
    
    public Material(String title, String author, String genre, String publisher, int yearPublished) {
        this.materialID = nextID++;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
    }

    public int GetMaterialID() {
        return materialID;
    }

    public String GetTitle(){
        return title;
    }

    public void SetTitle(String title){
        this.title = title;
    }

    public String GetGenre(){
        return genre;
    }

    public void SetGenre(String genre){
        this.genre = genre;
    }

    public int GetYearPublished(){
        return yearPublished;
    }

    public void SetYearPublished(int yearPublished){
        this.yearPublished = yearPublished;
    }

    public String GetPublisher(){
        return publisher;
    }

    public void SetPublisher(String publisher){
        this.publisher = publisher;
    }

    public String GetAuthor(){
        return author;
    }

    public void SetAuthor(String author){
        this.author = author;
    }

    public int GetCopies(){
        return copies;
    }

    public void SetCopies(int copies){
        this.copies = copies;
    }

    public abstract String GetType();
}