import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Name: Christopher Bain
 * Course: 202620-CEN-3024C-23585
 * Date: 04/06/2026
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

    /**
     * the constructor of the TvShow.java that sets the value of the attributes.
     *
     * @param id The id of the tv show
     * @param title the title of the tv show
     * @param isAnimated if the show is animated
     * @param completionMonth the month associated with the tv show
     * @param completionDate the date associated with the tv show
     * @param completionYear the year associated with the tv show
     * @param rating the user provided rating of the tv show
     * @param onWatchList if the show is on the watch list.
     */
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

    /**
     * the constructor of the TvShow.java that sets the value of the attributes if they enter a number for the is
     * animated an on watch list options.
     *
     * @param id The id of the tv show
     * @param title the title of the tv show
     * @param isAnimated if the show is animated
     * @param completionMonth the month associated with the tv show
     * @param completionDate the date associated with the tv show
     * @param completionYear the year associated with the tv show
     * @param rating the user provided rating of the tv show
     * @param onWatchList if the show is on the watch list.
     */
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

    /**
     *
     * @return returns the id of the tv show
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return returns the title of the tv show
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param input sets the argument to the value of the title for the tv show
     */
    public void setTitle(String input) {
        this.title = input;
    }

    /**
     *
     * @return returns the boolean if the show is animated.
     */
    public boolean isAnimated() {
        return isAnimated;
    }

    /**
     * sets the value of the boolean type representing the animation status of the tv show
     * @param input the use defined boolean to set the animation status of the tv show.
     */
    public void setAnimatedGui(boolean input){isAnimated = input;}

    /**
     * sets the animated value of the tv show if the user enters an integer
     * @param input the integer value the customer enters.
     * @return a boolean that represents the animated status is returned.
     * @throws InputMismatchException the error is thrown if the type that the user enters is not of type int.
     */
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

    /**
     *
     * @return returns the month associated with the tv show.
     */
    public int getCompletionMonth() {
        return completionMonth;
    }

    /**
     * sets the month associated with thee tv show as entered by the user. it verifies the information and creates
     * boundaries for it.
     *
     * @param input the value entered by the user for the associated date.
     * @return returns the int value that the user is trying to set the date to.
     * @throws InputMismatchException if the user doesn't enter a valid entry or an int type.
     */
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

    /**
     *
     * @return returns the date associated with the tv show
     */
    public int getCompletionDate() {
        return completionDate;
    }

    /**
     * takes in the input from the user and wht they would like ot set the date associated with the tv show. Includes
     * restricted boundaries for the date.
     * @param input the user defined input that they woul like to set to the tv show.
     * @return returns the set integer for the date associated with the tv show.
     * @throws InputMismatchException if the entry is not of the type that is expected (int)
     */
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

    /**
     *
     * @return returns the year that is associated with the tv show.
     */
    public int getCompletionYear() {
        return completionYear;
    }

    /**
     * the functio for the user to set the year associated with the tv show
     * @param input the user defined input that they are trying to set the year to
     * @return returns an int that represents the associated tv show year.
     */
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

    /**
     *
     * @return returns the rating associated with the tv show.
     */
    public double getRating() {
        return rating;
    }

    /**
     * function to set the rating associated with the tv show and enforces the boundary of the rating.
     * @param input the rating that the customer tries to enter.
     * @return returns a double representing the rating after the entry is validated.
     * @throws InputMismatchException if the expected type is not entered an error is thrown.
     */
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

    /**
     *
     * @return returns the watchlist status of the tvshow.
     */
    public boolean isOnWatchList() {
        return onWatchList;
    }

    /**
     *
     * @param input sets the watch list value of the tv show.
     */
    public void setOnWatchListGui(boolean input){onWatchList = input;}

    /**
     * sets the value of the watchlist and enforces the validation of the input.
     * @param input a integer submitted to the function is converted to a boolean by this function.
     * @return returns a boolean that represents the entry of the user.
     * @throws InputMismatchException is the entry is not of the expected type an error is thrown.
     */
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

    /**
     *
     * @return returns the id with trailing 0's
     */
    public String getIdFormated(){
        return String.format("%07d", id);
    }

    /**
     *
     * @return returns the line of data in a formated way to be outputted to the screen.
     */
    @Override
    public String toString() {
        return getIdFormated() + "-" + getTitle() + "-" + isAnimated() + "-" + getCompletionMonth() + "-" +
                getCompletionDate() + "-" + getCompletionYear() + "-" + getRating() + "-" + isOnWatchList() + "\n";
    }
}
