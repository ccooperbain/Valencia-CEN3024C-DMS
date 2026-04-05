import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Name: Christopher Bain
 * Course: 202620-CEN-3024C-23585
 * Date: 04/04/2026
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

    public TvShow(int id, String title, int isAnimated, int completionMonth, int completionDate, int completionYear,
                  double rating, int onWatchList) {
        this.id = id;
        this.title = title;
        this.isAnimated = setAnimated(isAnimated);
        this.completionMonth = setCompletionMonth(completionMonth);
        this.completionDate = setCompletionDate(completionDate);
        this.completionYear = setCompletionYear(completionYear);
        this.rating = setRating(rating);
        this.onWatchList = setOnWatchList(onWatchList);
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

    public void setTitle(String input) {
        this.title = input;
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public void setAnimatedGui(boolean input){isAnimated = input;}
    public boolean setAnimated(int input) throws InputMismatchException {

            switch (input) {
                case 1:
                    isAnimated = true;
                    return true;

                case 2:
                    isAnimated = false;
                    return false;

                default:
                    System.out.println("Invalid input you entered a number that was not an animation option ");
                    throw new InputMismatchException();

            }
    }

    public int getCompletionMonth() {
        return completionMonth;
    }

    public int setCompletionMonth(int input) throws InputMismatchException {
        int month = input;
        if(month < 1 || month > 12) {
            System.out.println("Invalid input for the month the tv show was completed.");
            throw new InputMismatchException();
        }else{
            this.completionMonth = month;
            return month;
        }
    }

    public int getCompletionDate() {
        return completionDate;
    }

    public int setCompletionDate(int input) throws InputMismatchException {
        int date = input;
        if(date < 1 || date>31) {
            System.out.println("Invalid input for the date that the tv show was completed.");
            throw new InputMismatchException();
        }else{
            this.completionDate = date;
            return date;
        }
    }

    public int getCompletionYear() {
        return completionYear;
    }

    public int setCompletionYear(int input) {
        int year = input;
        if(year < 1928 || year>3005) {
            System.out.println("Invalid input for the year that you completed the tv show.");
            throw new InputMismatchException();
        }else{
            this.completionYear = year;
            return year;
        }
    }

    public double getRating() {
        return rating;
    }

    public double setRating(double input) throws InputMismatchException {
        double rating = input;
        if(rating < 0 || rating>10) {
            System.out.println("Invalid input for the rating");
            throw  new InputMismatchException();
        }else{
            this.rating = rating;
            return rating;
        }

    }

    public boolean isOnWatchList() {
        return onWatchList;
    }

    public void setOnWatchListGui(boolean input){onWatchList = input;}
    public boolean setOnWatchList(int input) throws InputMismatchException {
        switch (input) {
            case 1:
                this.onWatchList = true;
                return true;
            case 2:
                this.onWatchList = false;
                return true;
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
