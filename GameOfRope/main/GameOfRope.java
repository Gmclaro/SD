package main;

import sharedRegions.*;
import entities.*;

import java.util.Scanner;
import java.io.File;

public class GameOfRope {
    Referee referee;

    public static void main(String[] args) {
        Referee referee;
        Coach[] coach;
        Contestant[][] contestants;

        GeneralRepository repo;
        String fileName;
        char option;
        boolean success = false;

        Scanner sc = new Scanner(System.in);

        System.out.println("Game of the Rope - Description of the internal state");
        do {
            System.out.println("Enter the name of the file to save the log: ");
            fileName = sc.nextLine();
            File file = new File(fileName);
            if (file.exists()) {
                do {
                    System.out.println("File already exists. Do you want to overwrite it? (y/n)");
                    String answer = sc.nextLine();
                    try {
                        option = answer.charAt(0);
                    } catch (StringIndexOutOfBoundsException e) {
                        option = 'y';
                    }
                } while (option != 'y' && option != 'n');

                if (option == 'n') {
                    success = false;
                } else {
                    success = true;
                }
            } else {
                success = true;
            }
        } while (!success);

        sc.close();

        repo = new GeneralRepository(fileName);
    }
}
