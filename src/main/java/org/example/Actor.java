package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Actor {
    static class ActorDetail {
        int id;
        String name;
        LocalDate dateOfBirth;

        ActorDetail(int id, String name, LocalDate dateOfBirth) {
            this.id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
        }
    }

    ArrayList<ActorDetail> actorList;
    HashMap<Integer, ActorDetail> actorMap;

    Actor() {
        actorList = new ArrayList<>();
        actorMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\hi\\Downloads\\Movie-assignment\\src\\main\\resources\\Dataset\\actors_large.csv"))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] actorData = line.split(",\\s*");

                if (actorData.length < 3) continue;

                try {
                    int id = Integer.parseInt(actorData[0].trim());
                    String name = actorData[1].trim();
                    LocalDate dateOfBirth = LocalDate.parse(actorData[2].trim());

                    ActorDetail actor = new ActorDetail(id, name, dateOfBirth);
                    actorList.add(actor);
                    actorMap.put(id, actor);

                } catch (Exception e) {
                    System.err.println("Skipping invalid row: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading actors file: " + e.getMessage()); // Fixed printStackTrace
        }
    }
}

