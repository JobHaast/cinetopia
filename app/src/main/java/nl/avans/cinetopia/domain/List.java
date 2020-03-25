package nl.avans.cinetopia.domain;

import java.util.ArrayList;

public class List {
    private int id;
    private String name;

    public List(int id, String name){
        this.id = id;
        this.name = name;
    }

    public boolean removeMovie(int id){
        boolean result = true;
        //method call for removing movie
        return result;
    }

    public boolean addMovie(){
        boolean result = true;
        //method call for adding movie
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
