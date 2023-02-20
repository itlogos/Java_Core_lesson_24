package ua.lviv.lgs;

//import java.awt.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.Map.Entry;
import static java.util.stream.Collectors.toList;

public class Cinema {

    TreeMap<Days, Schedule> schedules = new TreeMap<>();
    private ArrayList<Days> day = new ArrayList<>();
    private ArrayList<Movie> moviesLibrary;
    private Time openTime;
    private Time closeTime;

    public void addMoviePreviously() {
        Movie movie1 = new Movie("Avatar", Time.timeFromString("2:36"));
        Movie movie2 = new Movie("Spider-Man", Time.timeFromString("2:13"));
        Movie movie3 = new Movie("Top Gun", Time.timeFromString("1:55"));
        Movie movie4 = new Movie("Jurassic World", Time.timeFromString("2:43"));
        Movie movie5 = new Movie("Hi, Mom", Time.timeFromString("1:52"));
        Movie movie6 = new Movie("No Time to Die", Time.timeFromString("2:46"));
        Movie movie7 = new Movie("Black Panther", Time.timeFromString("2:51"));
        Movie movie8 = new Movie("The Batman", Time.timeFromString("1:42"));
        Movie movie9 = new Movie("Black Panther revers", Time.timeFromString("1:57"));
        getMoviesLibrary().add(movie1);
        getMoviesLibrary().add(movie2);
        getMoviesLibrary().add(movie3);
        getMoviesLibrary().add(movie4);
        getMoviesLibrary().add(movie5);
        getMoviesLibrary().add(movie6);
        getMoviesLibrary().add(movie7);
        getMoviesLibrary().add(movie8);
        getMoviesLibrary().add(movie9);

        System.out.println("The movie added: ");
        if (!moviesLibrary.isEmpty()) {
            moviesLibrary.stream().forEach(System.out::println);
        } else {
            System.out.println("Movie library is empty yet...");
        }

    }

    public void addSeancePreviously() {
        int j = 0;
        String t;
        for (int i = 0; i < moviesLibrary.size(); i++) {
            j = (i < 7) ? i : i % 7;
            t = (i < 7) ? "11:00" : "14:00";
            Time time1 = Time.timeFromString(t);
            Seance seanceTemp = new Seance(moviesLibrary.get(i), time1);
            Schedule schedule = schedules.get(day.get(j));
            schedule.addSeance(seanceTemp);
            schedules.put(day.get(j), schedule);
        }
    }

// -----add movie to the library ---- 1
    public void addMovieToTheLibrary() {

        Scanner sc1 = new Scanner(System.in);
        System.out.println("Input Title of the Movie to add:");
        String title = "";
        if (sc1.hasNext())
            title = sc1.nextLine();
        System.out.println("Input duration of the Movie to add(hh:mm):");
        Time duration = Time.timeFromString("0:00");
        if (sc1.hasNext())
            duration = Time.timeFromString(sc1.next());

        Movie movie = new Movie(title, duration);
        getMoviesLibrary().add(movie);
        System.out.println("The movie added: " + movie.toString());
    }

// ----remove movie from the library----2
    public void removeMovieFromTheLibrary() {
        Scanner sc2 = new Scanner(System.in);
        moviesLibrary.stream().forEach(System.out::println);
        System.out.println("Input Title of the Movie to remove:");
        String title = "";
        if (sc2.hasNextLine())
            title = sc2.nextLine();
        Movie movie;
        Optional<Movie> movieOpt = findMovie(title);
        if (movieOpt.isPresent()) {
            movie = movieOpt.get();
            removeAllSeancesByMovie(title);
            removeMovie(movie);
            System.out.println(movie.toString() + " is removed from the movie library");
        } else
            System.out.println("We cannot remove. There is some problem...");
    }

// ---- remove movie from the library ----21
    public boolean removeMovie(Movie movie) {
        if (moviesLibrary.remove(movie)) {
            System.out.println("The movie [" + movie + "] was removed");
            return true;
        }
        System.out.println("There is not that movie in movie library");
        return false;
    }

    // ---- remove movie from the library ----22
    public boolean removeAllSeancesByMovie(String title) {
        for (Schedule schedule : schedules.values()) {
            Set<Seance> seances = schedule.getSeances();
            for (Seance seance : seances) {
                if (seance.getMovie().getTitle().toString().equals(title)) {
                    schedule.removeSeance(seance);
                }
            }
        }
        System.out.println("The seance was successfully removed");
        return true;
    }

// ----add seance ----3
    public void addSeanceBase() {
        moviesLibrary.stream().forEach(System.out::println);
        Movie movie = getMovieFromConsole();
        String day = getDayFromConsole();
        Seance seance = getCheckedSeanceFromConsole(movie);

        addSeance(seance, day);
    }

//------add seance ----31  
    private Movie getMovieFromConsole() {
        Optional<Movie> movieOpt;
        while (true) {
            Scanner sc3 = new Scanner(System.in);
            System.out.println("Input Title of the Movie from movie library");
            String title = "";
            if (sc3.hasNext())
                title = sc3.nextLine();
            movieOpt = findMovie(title);
            if (movieOpt.isPresent()) {
                System.out.println(movieOpt.get().toString() + " is in the movie library, next...");
                break;
            } else
                System.out.println("There is no movie in the library");
        }
        return movieOpt.get();
    }

//-----add seance ---32, 4, 5---
    private String getDayFromConsole() {
        String dayString = "";
        String stringOut = "";
        Scanner sc3 = new Scanner(System.in);
        while (true) {
            System.out.println("Input day of seanse");
            if (sc3.hasNext())
                dayString = sc3.next();
            for (Days day : Days.values()) {
                if (day.toString().equalsIgnoreCase(dayString))
                    stringOut = day.toString();
            }
            if (stringOut.isEmpty()) {
                System.out.println("Wrong day name, try again...");
            } else {
                System.out.println("Day " + stringOut + " choosen, next...");
                break;
            }
        }
        return stringOut;
    }

//----add seance---33     
    private Seance getCheckedSeanceFromConsole(Movie movie) {
        Time startTime = Time.timeFromString("0:00");
        Scanner sc3 = new Scanner(System.in);
        Seance seance;
        while (true) {
            System.out.println("Input start time of the new seance(hh:mm):");
            if (sc3.hasNext())
                startTime = Time.timeFromString(sc3.next());
            Seance seanceTemp = new Seance(movie, startTime);
            if (checkSeanceBounds(seanceTemp)) {
                seance = seanceTemp;
                break;
            }
        }
        return seance;
    }

//----add seance-----34
    private boolean addSeance(Seance seance, String day) {
        if (checkSeanceBounds(seance)) {
            for (Days currentDay : Days.values()) {
                if (currentDay.toString().equalsIgnoreCase(day)) {
                    Schedule schedule = schedules.get(currentDay);
                    schedule.addSeance(seance);
                    schedules.put(currentDay, schedule);
                    System.out.println("The seance added: " + schedule.toString());
                }
            }
            return true;
        }
        return false;
    }

//----add seance-----35   
    private boolean checkSeanceBounds(Seance seance) {
        Time openTime = getOpenTime();
        Time closeTime = getCloseTime();
        if ((seance.getStartTime().compareTo(openTime) < 0) || (seance.getEndTime().compareTo(closeTime) > 0)) {
            System.out.println("The Cinema is closed. Input time when Cinema is open (from " + getOpenTime().toString()
                    + " till " + getCloseTime().toString() + ")");
            return false;
        }
        return true;
    }

// -----removeSeance---4
    public void removeSeance() {
        showSchedule();
        String day = getDayFromConsole();
        Scanner sc3 = new Scanner(System.in);
        System.out.println("Input start time of the seance you want to remove");
        Time startTime = Time.timeFromString("0:00");
        if (sc3.hasNext())
            startTime = Time.timeFromString(sc3.next());
        Optional<Seance> findSeance = findSeanceByTime(startTime);
        if (findSeance.isPresent()) {
            removeSeanceByDay(findSeance.get(), day);
        } else
            System.out.println("There is not seance in this day and with this start time, try again");
    }

    // -----removeSeance---41
    private Optional<Seance> findSeanceByTime(Time startTime) {
        for (Schedule schedule : getSchedules().values()) {
            for (Seance seance : schedule.getSeances()) {
                if (seance.getStartTime().equals(startTime))
                    return Optional.of(seance);
            }
        }
        return Optional.empty();
    }

// -----removeSeance---42 
    private boolean removeSeanceByDay(Seance seance, String day) {
        for (Entry<Days, Schedule> scheduleEntry : schedules.entrySet()) {
            Days key = scheduleEntry.getKey();
            if (key.toString().equalsIgnoreCase(day)) {
                Schedule value = scheduleEntry.getValue();
                value.removeSeance(seance);
            }
        }
        System.out.println("The seance was successfully removed");
        return true;
    }

//----show schedule by day----5
    public void showScheduleByDay() {
        String day = getDayFromConsole();
        for (Entry<Days, Schedule> scheduleEntry : schedules.entrySet()) {
            Days key = scheduleEntry.getKey();
            if (key.toString().equalsIgnoreCase(day)) {
                Schedule value = scheduleEntry.getValue();
                value.getSeances().stream().forEach(System.out::println);

            }

        }
    }

// ----show movie library------6
    public void showMovieLibrary() {

        ArrayList<Movie> moviesLibrary = getMoviesLibrary();
        System.out.println("Movie library:");
        if (!moviesLibrary.isEmpty()) {
            moviesLibrary.stream().forEach(System.out::println);
        } else {
            System.out.println("Movie library is empty yet...");
        }
        System.out.println();
    }

    // ----show movie show schedule------7

    public void showSchedule() {
        schedules.entrySet().stream().map(hallScheduleToString()).collect(toList()).forEach(System.out::println);

    }

    public Function<Entry<Days, Schedule>, String> hallScheduleToString() {
        return entry -> "Schedule of seances in " + entry.getKey() + ":\n" + entry.getValue().toString();
    }

// ---set time Cinema-- 0

    public Cinema setTimeCinema() {
        Time openTime = Time.timeFromString("10:00");
        Time closeTime = Time.timeFromString("24:00");
        Scanner sc1;
        sc1 = new Scanner(System.in);
        System.out.println("Please, input open time of the Cinema (hh:mm)");
        if (sc1.hasNext())
            openTime = Time.timeFromString(sc1.next());
        System.out.println("Please, input close time of the Cinema (hh:mm)");
        if (sc1.hasNext())
            closeTime = Time.timeFromString(sc1.next());
        System.out.println("Well done! New time was created! \n  Open time = " + openTime + "\n Close time = "
                + closeTime + "\nNext chose the menu.");
        return new Cinema(openTime, closeTime);
    }

    private Optional<Movie> findMovie(String title) {
        for (Movie movie : getMoviesLibrary()) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return Optional.of(movie);
            }
        }
        return Optional.empty();
    }

    public Cinema(Time openTime, Time closeTime) {
        super();
        this.schedules = new TreeMap<>();
        Days[] daye = Days.values();
        for (Days days : daye) {
            schedules.put(days, new Schedule());
            day.add(days);

        }

        this.moviesLibrary = new ArrayList<>();
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public Cinema() {

    }

    public TreeMap<Days, Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(TreeMap<Days, Schedule> schedules) {
        this.schedules = schedules;
    }

    public ArrayList<Movie> getMoviesLibrary() {
        return moviesLibrary;
    }

    public void setMoviesLibrary(ArrayList<Movie> moviesLibrary) {
        this.moviesLibrary = moviesLibrary;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    @Override
    public String toString() {
        return "Cinema schedules=" + schedules + ", \nmoviesLibrary=" + moviesLibrary + ", \nopenTime=" + openTime
                + ", \ncloseTime=" + closeTime;
    }
}
