package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Actor actors = new Actor();
    private static final Director directors = new Director();
    private static final Movie movies = new Movie();

        public static void main(String[] args) {
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Get Movie Information");
                System.out.println("2. Get Top 10 Rated Movies");
                System.out.println("3. Get Movies by Genre");
                System.out.println("4. Get Movies by Director");
                System.out.println("5. Get Movies by Release Year");
                System.out.println("6. Get Movies by Release Year Range");
                System.out.println("7. Add a New Movie");
                System.out.println("8. Update Movie Rating");
                System.out.println("9. Delete a Movie");
                System.out.println("10. Sort and Return 15 Movies by Release Year");
                System.out.println("11. Get Directors with the Most Movies");
                System.out.println("12. Get Actors Who Worked in Multiple Movies");
                System.out.println("13. Get Movies of the Youngest Actor as of 10-02-2025");
                System.out.println("14. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> getMovieInformation();
                    case 2 -> getTopRatedMovies();
                    case 3 -> getMoviesByGenre();
                    case 4 -> getMoviesByDirector();
                    case 5 -> getMoviesByReleaseYear();
                    case 6 -> getMoviesByReleaseYearRange();
                    case 14 -> {
                        System.out.println("Exiting program.");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    private static void getMovieInformation() {
        System.out.print("Enter Movie ID or Title: ");
        String input = scanner.nextLine();

        try {
            int movieId = Integer.parseInt(input);
            if (movies.movieMap.containsKey(movieId)) {
                displayMovieInfo(movies.movieMap.get(movieId));
                return;
            }
        } catch (NumberFormatException ignored) {
        }

        for (Movie.MovieDetail movie : movies.movieList) {
            if (movie.title.equalsIgnoreCase(input)) {
                displayMovieInfo(movie);
                return;
            }
        }
        System.out.println("Movie not found.");

    }

    private static void displayMovieInfo(Movie.MovieDetail movie) {
        System.out.println("\nTitle: " + movie.title);
        System.out.println("Year: " + movie.year);
        System.out.println("Genre: " + movie.genre);
        System.out.println("Rating: " + movie.rating);
        System.out.println("Duration: " + movie.duration + " minutes");

        Director.DirectorDetail director = directors.directorMap.get(movie.dirID);
        System.out.println("Director: " + (director != null ? director.name : "Unknown"));

        System.out.print("Actors: ");
        movie.actorIDs.forEach(id -> {
            Actor.ActorDetail actor = actors.actorMap.get(id);
            if (actor != null) {
                System.out.print(actor.name + ", ");
            }
        });
        System.out.println();
    }


    private static void getTopRatedMovies() {
        movies.movieList.stream()
                .sorted(Comparator.comparingDouble((Movie.MovieDetail m) -> m.rating).reversed())
                .limit(10)
                .forEach(Main::displayMovieInfo);
    }

    private static void getMoviesByGenre() {
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine();
        movies.movieList.stream()
                .filter(m -> m.genre.equalsIgnoreCase(genre))
                .forEach(Main::displayMovieInfo);
    }

    private static void getMoviesByDirector() {
        System.out.print("Enter Director Name: ");
        String directorName = scanner.nextLine();
        directors.directorList.stream()
                .filter(d -> d.name.equalsIgnoreCase(directorName))
                .map(d -> d.id)
                .flatMap(id -> movies.movieList.stream().filter(m -> m.dirID == id))
                .forEach(Main::displayMovieInfo);
    }

    private static void getMoviesByReleaseYear() {
        System.out.print("Enter Release Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        movies.movieList.stream()
                .filter(m -> m.year == year)
                .forEach(Main::displayMovieInfo);
    }

    private static void getMoviesByReleaseYearRange() {
        System.out.print("Enter Year Range (e.g., 2010-2020): ");
        String[] range = scanner.nextLine().split("-");
        int startYear = Integer.parseInt(range[0].trim());
        int endYear = Integer.parseInt(range[1].trim());
        movies.movieList.stream()
                .filter(m -> m.year >= startYear && m.year <= endYear)
                .forEach(Main::displayMovieInfo);
    }

    }
