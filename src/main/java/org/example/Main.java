package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.Period;

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
                    case 7 -> addNewMovie();
                    case 8 -> updateMovieRating();
                    case 9 -> deleteMovie();
                    case 10 -> sortMoviesByReleaseYear();
                    case 11 -> getDirectorsWithMostMovies();
                    case 12-> getActorsWithMultipleMovies();
                    case 13 -> getMoviesOfYoungestActor();
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

    private static void addNewMovie() {
        System.out.print("Enter Movie Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Release Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter Rating: ");
        float rating = scanner.nextFloat();
        System.out.print("Enter Duration: ");
        int duration = scanner.nextInt();
        System.out.print("Enter Director ID: ");
        int dirID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Actor IDs (comma separated): ");
        String[] actorIdsInput = scanner.nextLine().split(",");
        ArrayList<Integer> actorIDs = new ArrayList<>();
        for (String id : actorIdsInput) actorIDs.add(Integer.parseInt(id.trim()));

        int id = movies.movieList.size() + 1;
        Movie.MovieDetail newMovie = new Movie.MovieDetail(id, title, year, genre, rating, duration, dirID, actorIDs);
        movies.movieList.add(newMovie);
        movies.movieMap.put(id, newMovie);
        System.out.println("Movie added successfully!");
    }
    private static void updateMovieRating() {
        System.out.print("Enter Movie ID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter New Rating: ");
        float newRating = scanner.nextFloat();
        if (movies.movieMap.containsKey(movieId)) {
            movies.movieMap.get(movieId).rating = newRating;
            System.out.println("Movie rating updated successfully!");
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static void deleteMovie() {
        System.out.print("Enter Movie ID: ");
        int movieId = scanner.nextInt();
        if (movies.movieMap.containsKey(movieId)) {
            movies.movieList.removeIf(m -> m.id == movieId);
            movies.movieMap.remove(movieId);
            System.out.println("Movie deleted successfully!");
        } else {
            System.out.println("Movie not found.");
        }
    }
    private static void sortMoviesByReleaseYear() {
        movies.movieList.stream()
                .sorted(Comparator.comparingInt(m -> m.year))
                .limit(15)
                .forEach(m -> System.out.println(m.title + " (" + m.year + ")"));
    }
    private static void getDirectorsWithMostMovies() {
        Map<Integer, Long> directorMovieCount = movies.movieList.stream()
                .collect(Collectors.groupingBy(m -> m.dirID, Collectors.counting()));

        directorMovieCount.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.println(directors.directorMap.get(entry.getKey()).name + " - " + entry.getValue() + " movies"));
    }

    private static void getActorsWithMultipleMovies() {
        Map<Integer, Long> actorMovieCount = movies.movieList.stream()
                .flatMap(m -> m.actorIDs.stream())
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));

        actorMovieCount.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.println(actors.actorMap.get(entry.getKey()).name + " - " + entry.getValue() + " movies"));
    }

    private static void getMoviesOfYoungestActor() {
        LocalDate referenceDate = LocalDate.of(2025, 2, 10);
        Optional<Actor.ActorDetail> youngestActor = actors.actorList.stream()
                .min(Comparator.comparing(actor -> Period.between(actor.dateOfBirth, referenceDate).getYears()));

        youngestActor.ifPresent(actor -> {
            System.out.println("Youngest Actor: " + actor.name);
            movies.movieList.stream()
                    .filter(m -> m.actorIDs.contains(actor.id))
                    .forEach(m -> System.out.println(m.title + " (" + m.year + ")"));
        });
    }
}
