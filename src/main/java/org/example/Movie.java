package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Movie {
    static class MovieDetail {
        int id, year, duration, dirID;
        String title, genre;
        float rating;
        ArrayList<Integer> actorIDs;

        MovieDetail(int id, String title, int year, String genre, float rating, int duration, int dirID, ArrayList<Integer> actorIDs) {
            this.id = id;
            this.title = title;
            this.year = year;
            this.genre = genre;
            this.rating = rating;
            this.duration = duration;
            this.dirID = dirID;
            this.actorIDs = actorIDs;
        }
    }

    ArrayList<MovieDetail> movieList;
    HashMap<Integer, MovieDetail> movieMap;

    Movie() {
        movieList = new ArrayList<>();
        movieMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\hi\\Downloads\\Movie-assignment\\src\\main\\resources\\Dataset\\movies_large.csv"))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] movieData = line.split(",\\s*");

                if (movieData.length < 8) continue;

                try {
                    int id = Integer.parseInt(movieData[0].trim());
                    String title = movieData[1].trim();
                    int year = Integer.parseInt(movieData[2].trim());
                    String genre = movieData[3].trim();
                    float rating = Float.parseFloat(movieData[4].trim());
                    int duration = Integer.parseInt(movieData[5].trim());
                    int dirID = Integer.parseInt(movieData[6].trim());

                    // Process actor IDs safely
                    String act = movieData[7].trim();
                    act = act.replaceAll("[\"\\[\\]]", "");

                    ArrayList<Integer> actorIDs = new ArrayList<>();
                    if (!act.isEmpty()) {
                        for (String actor : act.split(",")) {
                            actor = actor.trim();
                            if (!actor.isEmpty()) {
                                actorIDs.add(Integer.parseInt(actor));
                            }
                        }
                    }

                    MovieDetail movie = new MovieDetail(id, title, year, genre, rating, duration, dirID, actorIDs);
                    movieList.add(movie);
                    movieMap.put(id, movie);

                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid row: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading movies file: " + e.getMessage()); // Fixed printStackTrace
        }
    }
}
