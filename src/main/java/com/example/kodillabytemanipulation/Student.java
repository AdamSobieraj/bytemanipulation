package com.example.kodillabytemanipulation;

import java.util.Random;

public class Student {
    private String indexNumber;

    public Student(int z) {
        this.indexNumber = generateRandomIndex(z);
    }

    private String generateRandomIndex(int z) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder index = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < z; i++) {
            int indexChar = random.nextInt(characters.length());
            index.append(characters.charAt(indexChar));
        }

        return index.toString();
    }

    public String getIndexNumber() {
        return indexNumber;
    }
}
