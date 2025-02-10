package org.example;

public class Main {
    public static void main(String[] args) {
        Actor actors = new Actor();
        Director directors = new Director();
        Movie movies = new Movie();

        System.out.println("Loaded " + actors.actorList.size() + " actors.");
        System.out.println("Loaded " + directors.directorList.size() + " directors.");
        System.out.println("Loaded " + movies.movieList.size() + " movies.");
    }
}
