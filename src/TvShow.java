import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Name: Christopher Bain
 * Course: 202620-CEN-3024C-23585
 * Date: 03/03/2026
 * Class Name: TvShow
 *
 * This class represents a single TV show record within the Database
 * Management System. It serves as the data model and stores attributes
 * such as ID, title, animation status, completion date, rating, and
 * watch list status.
 */
public class TvShow {

    private int id;
    private String title;
    private boolean isAnimated;
    private int completionMonth;
    private int completionDate;
    private int completionYear;
    private double rating;
    private boolean onWatchList;

    public TvShow(int id, String title, boolean isAnimated, int completionMonth, int completionDate, int completionYear,
                  double rating, boolean onWatchList) {
        this.id = id;
        this.title = title;
        this.isAnimated = isAnimated;
        this.completionMonth = completionMonth;
        this.completionDate = completionDate;
        this.completionYear = completionYear;
        this.rating = rating;
        this.onWatchList = onWatchList;
    }
    public TvShow(int id) throws InputMismatchException {
        this.id = id;
        setTitle();
        setAnimated();
        setCompletionMonth();
        setCompletionDate();
        setCompletionYear();
        setOnWatchList();
        setRating();

    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        System.out.println("Enter the title of the tv show.");
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        this.title = input.nextLine();
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public void setAnimated() throws InputMismatchException {
        Scanner input = new Scanner(System.in);
        System.out.println("Is this animated? (enter 1 for yes and 2 for no)");
        System.out.println("[1] YES");
        System.out.println("[2] NO");
        System.out.print(">");
            switch (input.nextInt()) {
                case 1:
                    isAnimated = true;
                    break;
                case 2:
                    isAnimated = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    throw new InputMismatchException();

            }

    }

    public int getCompletionMonth() {
        return completionMonth;
    }

    public void setCompletionMonth() throws InputMismatchException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the month (as a number) that you completed the tv show.");
        System.out.print(">");
        int month = input.nextInt();
        if(month < 1 || month > 12) {
            System.out.println("Invalid input for the month the tv show was completed.");
            throw new InputMismatchException();
        }else{
            this.completionMonth = month;
        }
    }

    public int getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate() throws InputMismatchException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the date that you completed the tv show.");
        System.out.print(">");
        int date = input.nextInt();
        if(date < 0 || date>31) {
            System.out.println("Invalid input for the date that the tv show was completed.");
            throw new InputMismatchException();
        }else{
            this.completionDate = date;
        }
    }

    public int getCompletionYear() {
        return completionYear;
    }

    public void setCompletionYear() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the year that you completed the tv show.");
        System.out.print("(Enter a value between 1928 - 3005)");
        System.out.print(">");
        int year = input.nextInt();
        if(year < 1928 || year>3005) {
            System.out.println("Invalid input for the year that you completed the tv show.");
            throw new InputMismatchException();
        }else{
            this.completionYear = year;
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating() throws InputMismatchException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the rating of the tv show from 0-10.");
        System.out.print(">");
        double rating = input.nextDouble();
        if(rating < 0 || rating>10) {
            System.out.println("Invalid input for the rating");
            throw  new InputMismatchException();
        }else{
            this.rating = rating;
        }

    }

    public boolean isOnWatchList() {
        return onWatchList;
    }

    public void setOnWatchList() throws InputMismatchException {
        Scanner input = new Scanner(System.in);
        System.out.println("Did you complete this show? (enter 1 for yes and 2 for no)");
        System.out.println("[1] YES");
        System.out.println("[2] NO");
        System.out.print(">");

        switch (input.nextInt()) {
            case 1:
                this.onWatchList = true;
                break;
            case 2:
                this.onWatchList = false;
                break;
            default:
                System.out.println("Invalid input");
                throw new InputMismatchException();
        }
    }
    public String getIdFormated(){
        return String.format("%07d", id);
    }

    @Override
    public String toString() {
        return getIdFormated() + "-" + getTitle() + "-" + isAnimated() + "-" + getCompletionMonth() + "-" +
                getCompletionDate() + "-" + getCompletionYear() + "-" + getRating() + "-" + isOnWatchList() + "\n";
    }
}
