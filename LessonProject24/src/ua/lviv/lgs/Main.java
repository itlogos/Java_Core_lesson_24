package ua.lviv.lgs;

import java.util.Scanner;

public class Main {
    static Cinema cinema = new Cinema(Time.timeFromString("10:00"), Time.timeFromString("24:00"));

    public static void main(String[] args) {
        Scanner sc;
        cinema.addMoviePreviously();
        cinema.addSeancePreviously();
        cinema.showSchedule();
        Menu();
        while (true) {
            sc = new Scanner(System.in);
            int menuInt = sc.nextInt();

            switch (menuInt) {
            case 1: {
                cinema.addMovieToTheLibrary();
                break;
            }
            case 2: {
                cinema.removeMovieFromTheLibrary();
                break;
            }
            case 3: {
                cinema.addSeanceBase();
                break;
            }
            case 4: {
                cinema.removeSeance();
                break;
            }

            case 5: {
                cinema.showScheduleByDay();
                break;
            }
            case 6: {
                cinema.showMovieLibrary();
                break;
            }
            case 7: {
                cinema.showSchedule();
                break;
            }
            case 0: {
                cinema = cinema.setTimeCinema();
                break;
            }
            case 8: {
                Menu();
                break;
            }
            case 9: {
                System.out.println("Exit.");
                System.exit(0);
                sc.close();
            }

            }
        }

    }

    public static void Menu() {
        System.out.println("  Menu ");
        System.out.println(" Input 1 to add movie to the movie library  ");
        System.out.println(" Input 2 to remove movie from the movie library  ");
        System.out.println(" Input 3 to add seance to the schedule of the day  ");
        System.out.println(" Input 4 to remove seance from the schedule of the day  ");
        System.out.println(" Input 5 to show schedule of the day  ");
        System.out.println(" Input 6 to show movie library  ");
        System.out.println(" Input 7 to show info about Cinema  ");
        System.out.println(" Input 8 to show all Menu again  ");
        System.out.println(" Input 9 to quit the program  ");
        System.out.println(" Open time of the Cinema seted " + cinema.getOpenTime());
        System.out.println(" Close time of the Cinema seted " + cinema.getCloseTime());
        System.out.println(" Input 0 to change opening and closing times Cinema  ");
    }

}