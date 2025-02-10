package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Director {
    static class DirectorDetail {
        int id;
        String name;

        DirectorDetail(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    ArrayList<DirectorDetail> directorList;
    HashMap<Integer, DirectorDetail> directorMap;

    Director() {
        directorList = new ArrayList<>();
        directorMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\hi\\Downloads\\Movie-assignment\\src\\main\\resources\\Dataset\\directors_large.csv"))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] directorData = line.split(",\\s*");

                if (directorData.length < 2) continue;

                try {
                    int id = Integer.parseInt(directorData[0].trim());
                    String name = directorData[1].trim();

                    DirectorDetail director = new DirectorDetail(id, name);
                    directorList.add(director);
                    directorMap.put(id, director);

                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid row: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading directors file: " + e.getMessage()); // Fixed printStackTrace
        }
    }
}

