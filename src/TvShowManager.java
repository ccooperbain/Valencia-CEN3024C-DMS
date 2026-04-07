import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Name: Christopher Bain
 * Course: 202620-CEN-3024C-23585
 * Date: 04/06/2026
 * Class Name: TvShowManager
 *
 * This class represents the business logic layer of the TV Show Database
 * Management System. It manages a collection of TvShow objects and provides
 * functionality to perform CRUD operations.
 *
 * This Manager is used my all displaying classes an entry points of the program. It has methods and functionality
 * used by Main.java, MainWindow.java, & DatabaseGui.java to maintain their functionality.
 *
 * The class is responsible for loading data from a file, validating
 * and managing TV show records, and calculating the average rating.
 *
 * This classes manages/stores/accesses data from a arraylist text file and sqlite database.
 */
public class TvShowManager {
    private ArrayList<TvShow> tvShows;
    private final String SQLPREFIX = "jdbc:sqlite:";
    private String url ="";
    private final String tabelName = "tv_shows";
    private Connection databaseConnection;

    /**
     * Constructor: TvShowManager()
     * this constructor is used for the Main.java and MainWindow.java classes. these classes rely on the text document
     * that is either created by the program or the text document that is added by the customer.
     *
     * It starts the method loadApp() to prepare the needed files.
     */
    public TvShowManager() {
        tvShows = new ArrayList<TvShow>();
        loadApp();
    }


    /**
     * Method Name: loadFromFile()
     *
     * Purpose:
     * Reads TV show data from a user-specified text file, parses each line,
     * and adds valid TV show records to the internal array list of the program.
     *
     * Arguments:
     *
     * Return Value:
     *
     */
    public void loadFromFile(){
        System.out.println("Loading Tv Shows...");
        Scanner fileLocation = new Scanner(System.in);
        System.out.println("add from file remember to remove the quotes");
        System.out.print(">");
        try{
            BufferedReader fileSource = new BufferedReader(new FileReader(fileLocation.nextLine()));
            //The file expects a path that does not include the quotation marks.

            while(fileSource.ready()){

                try{
                    String tvShowEntry  = fileSource.readLine();
                    System.out.println("The string being processed is: " + tvShowEntry);
                    String[] parts = tvShowEntry.split("-");
                    int id =  Integer.parseInt(parts[0]);
                    if(takenId(id)){
                        throw new Exception("Tv Show id is already taken :" + id);
                    }

                    tvShows.add(new TvShow(id,parts[1],Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Double.parseDouble(parts[6]),Boolean.parseBoolean(parts[7])));

                }catch(Exception e){
                    System.out.println("Invalid tv show entry from file text file the line of text was not expected or a entry has an existing id.");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("The referenced file location was not found ");
        }catch (IOException f){
            System.out.println("Could not read line from file.");
        }

    }

    /**
     * Method Name: loadFromFileGui
     *
     * Purpose:
     *Reads TV show data from a user-specified text file, parses each line,
     *and adds valid TV show records to the internal array list of the program. This method is used by the
     * MainWindow.java class which provided the argument which is excepted to be the file location that the user
     * submits through the GUI.
     *
     * @param fileLocation provided by the gui to the function.
     *
     * Return Value:
     *
     */
    public void loadFromFileGui(String fileLocation){
        System.out.println("Loading Tv Shows...");
        System.out.println("add from file remember to remove the quotes");
        System.out.print(">");
        try{
            BufferedReader fileSource = new BufferedReader(new FileReader(fileLocation));
            //The file expects a path that does not include the quotation marks.

            while(fileSource.ready()){

                try{
                    String tvShowEntry  = fileSource.readLine();
                    System.out.println("The string being processed is: " + tvShowEntry);
                    String[] parts = tvShowEntry.split("-");
                    int id =  Integer.parseInt(parts[0]);
                    if(takenId(id)){
                        throw new Exception("Tv Show id is already taken :" + id);
                    }

                    tvShows.add(new TvShow(id,parts[1],Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Double.parseDouble(parts[6]),Boolean.parseBoolean(parts[7])));

                }catch(Exception e){
                    System.out.println("Invalid tv show entry from file text file the line of text was not expected or a entry has an existing id.");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("The referenced file location was not found ");
        }catch (IOException f){
            System.out.println("Could not read line from file.");
        }
    }

    /**
     * Method Name: loadDatabase
     *
     * Purpose:
     * creates a connection to the SQLite database using the provided file path
     * and returns all records from the tv_shows table.
     *
     * @param filepath the location that is submitted to the function from the gui form.
     *
     * @return Resultset this method returned the full data set of the database that was processed.
     */
    public ResultSet loadDatabase(String filepath){
        try{
            databaseConnection = DriverManager.getConnection(getSQLPREFIX()+getUrl());
            if(databaseConnection != null){
                System.out.println("Connected to the Database successfully");
                Statement statement = databaseConnection.createStatement();
                return statement.executeQuery("SELECT * FROM tv_shows");
            }
        }catch(SQLException e){
            System.out.println("Error in the manager loading of the database");

        }
        return null;
    }


    /**
     * Method Name: addShow
     *
     * Purpose:
     * Collects input from the user to create a new TV show record,
     * assigns a unique ID, and adds the new TvShow object this function is used by the Main.java class.
     *
     * Arguments:
     *
     * Return Value:
     *
     */

    public void addShow(){
        System.out.println("Adding show...");
        Scanner input = new Scanner(System.in);

        int id = setId();

        System.out.print("\nEnter the title of the tv show. \n >");
        String title = input.nextLine();

        System.out.print("\nIs this animated? (enter 1 for yes and 2 for no)\n [1] YES \n [2] NO \n>");
        int animated = input.nextInt();

        System.out.print("\nPlease enter the month (as a number) that you completed the tv show.\n>");
        int month = input.nextInt();

        System.out.print("\nPlease enter the date that you completed the tv show.\n>");
        int date = input.nextInt();

        System.out.print("\nPlease enter the year that you completed the tv show.\n");
        System.out.print("(Enter a value between 1928 - 3005) \n>");
        int year = input.nextInt();

        System.out.print("\nDid you complete this show? (enter 1 for yes and 2 for no)\n");
        System.out.print("[1] YES\n[2] NO\n>");
        int watchList = input.nextInt();

        System.out.print("\nPlease enter the rating of the tv show from 0-10. \n>");
        double rating = input.nextDouble();

        TvShow show = new TvShow(id,title,animated,month,date,year,rating,watchList);

        tvShows.add(show);

    }

    /**
     * Method Name: addShowToDatabase
     *
     * Purpose:
     * Inserts a new TV show record into the database using the provided parameters. This method is used by the
     * DatabaseGui.java class
     *
     * @param title the title that the user entered on the form
     * @param is_anime the true or false value representing if the show is animated.
     * @param month the month associated with the tv show
     * @param day the day associated with the tv show
     * @param year the year associated with the tv show
     * @param rating the rating of the tv show given by the user
     * @param is_finished the boolean value that represents if the show is on the watchlist
     *
     * @return int this method returns a -1 if the information was not added successfully to the program.
     */
    public int addShowToDatabase(String title,boolean is_anime,int month,int day,int year,int rating, boolean is_finished){
        try{
            Statement statement = databaseConnection.createStatement();
            return statement.executeUpdate("INSERT INTO tv_shows (title, animated, month,day, year, rating, watchlist) VALUES ('"+title+"',"+is_anime+","+month+","+day+","+year+","+rating+","+is_finished+")");
        }catch(Exception e){
            System.out.println("Error in the manager adding of the tv show");
        }
        return -1;
    }

    /**
     * Method Name: getAllShows
     *
     * Purpose:
     * Displays all TV show records currently stored in the system. This displays the tv show records using the console.
     * This method is mainly used by the Main.java Class.
     *
     * Arguments:
     *
     * Return Value:
     *
     */
    public void getAllShows(){
        System.out.println("Getting all shows...");
        for(TvShow p : tvShows){
            System.out.println(p.toString());
        }
    }


    /**
     * Method Name: updateShow
     *
     * Purpose:
     * Allows the user to modify specific attributes of an existing TV show
     * record by selecting the record's ID and choosing which field to update.This is used by the Main.java Class.
     *
     * Arguments:
     *
     * Return Value:
     *
     */
    public void updateShow(){
        Scanner input = new Scanner(System.in);
        System.out.println("Updating show...");
        //TODO case statement regarding which attribute to update and which object

        System.out.println("Please enter the id you would like to update");
        System.out.print(">");
        try{
            int id = input.nextInt();

            for(TvShow p : tvShows){
                if(p.getId() == id){

                    System.out.println("Which attribute do you want to modify?");
                    System.out.println("[1] The title.");
                    System.out.println("[2] If its animated.");
                    System.out.println("[3] Completion month.");
                    System.out.println("[4] Completion date.");
                    System.out.println("[5] Completion year.");
                    System.out.println("[6] If its on your to watch list.");
                    System.out.println("[7] Update the tv show rating. ");
                    System.out.print(">");

                    switch(input.nextInt()){
                        case 1:
                            System.out.print("\nEnter the title of the tv show. \n >");
                            input.nextLine();
                            p.setTitle(input.nextLine());
                            break;
                        case 2:
                            System.out.print("\nIs this animated? (enter 1 for yes and 2 for no)\n [1] YES \n [2] NO \n >");
                            p.setAnimated(input.nextInt());
                            break;
                        case 3:
                            System.out.print("\nPlease enter the month (as a number) that you completed the tv show.\n >");
                            p.setCompletionMonth(input.nextInt());
                            break;
                        case 4:
                            System.out.print("\nPlease enter the date that you completed the tv show.\n>");
                            p.setCompletionDate(input.nextInt());
                            break;
                        case 5:
                            System.out.print("\nPlease enter the year that you completed the tv show.\n");
                            System.out.print("(Enter a value between 1928 - 3005) \n>");
                            p.setCompletionYear(input.nextInt());
                            break;
                        case 6:
                            System.out.print("\nDid you complete this show? (enter 1 for yes and 2 for no)\n");
                            System.out.print("[1] YES\n[2] NO\n>");
                            p.setOnWatchList(input.nextInt());
                            break;
                        case 7:
                            System.out.print("\nPlease enter the rating of the tv show from 0-10. \n>");
                            p.setRating(input.nextDouble());
                            break;
                            default:
                                System.out.println("Invalid input");
                                throw new InputMismatchException("Invalid input");
                    }
                    return;
                }

            }
            System.out.println("Invalid input the id entered doesn't exist");

        }catch (InputMismatchException e){
            System.out.println("Incorrect entry try again");
        }
    }

    /**
     * Method Name: updateDatabaseShowEntry
     *
     * Purpose:
     * Updates an existing TV show record in the database using its ID and a sql statement. The information
     * arguments are collected from the GUI form that has the information populated. This method is used with
     * the DatabaseGui.java Class.
     *
     * @param id the id that the user is looking to edit.
     * @param title the updated title that the custoemer would like to change it to
     * @param is_anime the updated animation status of the tv show
     * @param month the updated month associated with the tv show
     * @param day the updated day associated with the tv show
     * @param year the updated year associated with the tv show.
     * @param rating the updated rating associated with the tv show
     * @param is_finished the updated status of the completion of the tv show
     *
     * @return int the function returns an int of -1 if the update of the record fails.
     */
    public int updateDatabaseShowEntry(int id,String title,boolean is_anime,int month,int day,int year,int rating,boolean is_finished){
        try{
            Statement statement = databaseConnection.createStatement();
            statement.executeUpdate("UPDATE tv_shows SET title='"+title+"', animated="+is_anime+",month="+month+",day="+day+", year="+year+",rating="+rating+",watchlist="+is_finished+" WHERE id="+id);
        }catch(SQLException e){
            System.out.println("Error in the update database manager");
        }
        return -1;
    }

    /**
     * Method Name: deleteShow
     *
     * Purpose:
     * Removes a TV show record from the collection based on the ID
     * provided by the user.This is used by the Main.java Class
     *
     * Arguments:
     *
     * Return Value:
     *
     */
    public void deleteShow(){
        System.out.println("Deleting show...");
        System.out.println("Enter the id of the show that you need to delete.");
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        Iterator<TvShow> iterator = tvShows.iterator();
        int id;
        try{
            id = input.nextInt();
            while(iterator.hasNext()){
                if(iterator.next().getId() == id){
                    System.out.println("The show has been deleted.");
                    iterator.remove();
                    return;
                }
            }

        }catch(InputMismatchException e){
            System.out.println("Invalid input try again");
        }
        System.out.println("The reference id has not been found no show has been deleted.");

    }

    /**
     * Method Name: deleteShowGui
     *
     * Purpose:
     * Removes a TV show record from the collection based on the ID
     * provided by the user and collected from the DatabaseGui.java class
     *
     * @param id the id of the record that needs to be removed from the table and the arraylist of the program.
     *
     * Return Value:
     *
     */
    public void deleteShowGui(int id){
        System.out.println("Deleting show...");
        Iterator<TvShow> iterator = tvShows.iterator();

        try{
            while(iterator.hasNext()){
                if(iterator.next().getId() == id){
                    System.out.println("The show has been deleted.");
                    iterator.remove();
                    return;
                }
            }

        }catch(InputMismatchException e){
            System.out.println("Invalid input try again");
        }
        System.out.println("The reference id has not been found no show has been deleted.");

    }

    /**
     * Method Name: deleteShowFromDatabase
     *
     * Purpose:
     * Removes a TV show record from the database using its ID. This id is provided by the DatabaseGui.java. It is
     * selected when the user makes a selection on the table.
     *
     * @param index the index of the option selected from the table.
     *
     * @return int number of rows deleted or -1 if an error occurs
     */
    public int deleteShowFromDatabase(int index){
        try{
            Statement statement = databaseConnection.createStatement();
            return statement.executeUpdate("DELETE FROM tv_shows WHERE id = " + index);
        }catch(SQLException e){
            System.out.println("Database error when deleting from database");
        }
        return -1;
    }

    /**
     * Method Name: calculateAverageRating
     *
     * Purpose:
     * Calculates and returns the average rating of all TV shows
     * currently stored in the system. This is the special function requirement. This is used by the
     * Main.java and the MainWindow.java
     *
     * Arguments:
     *
     * @return double The calculated average rating of all TV shows in the arraylist.
     */
    public double calculateAverageRating(){
        System.out.println("Calculating average rating for all shows...");
        int numberOfTvShows = tvShows.size();
        double sum = 0;
        if(tvShows.isEmpty()){
            System.out.println("There are no Tv Shows in the database.");
            return 0;
        }

        for(TvShow p : tvShows){
            sum += p.getRating();
        }
        System.out.print("The average rating of the entered tv shows are:");
        return sum / numberOfTvShows;
    }

    /**
     * Method Name: calculateAvgRatingDatabase
     *
     * Purpose:
     * Calculates the average rating of all TV shows stored in the database. THis is used by the DatabaseGui.java calss.
     * To calculate the average it used the average statement inside sql.
     *
     * Arguments:
     * None
     *
     * @return double The average of the table ratings, or -1 if an error occurs
     */
    public double calculateAvgRatingDatabase(){
        try{
         Statement statement = databaseConnection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT AVG(rating) FROM tv_shows;");
         if(resultSet.next()){
             return resultSet.getDouble(1);
         }
        }catch(SQLException e){
            System.out.println("Error in calculating the average");
        }
        return -1;
    }


    /**
     * Method Name: takenId
     *
     * Purpose:
     * Determines whether a given ID is already assigned to an existing
     * TV show record in the collection. This is used by the Main.java and the MainWindow.java classes to determine
     * if an id is already used.
     *
     * @param id The ID value to check for.
     *
     * @return boolean - Returns true if the ID is already in use, otherwise false.
     */
    public boolean takenId(int id){
        for(TvShow p : tvShows){
            if(p.getId() == id){
                return true;
            }
        }
        return false;
    }

    /**
     * Method Name: setId
     *
     * Purpose:
     * Generates and returns the next available unique ID that is not
     * currently assigned to any existing TV show record. This is used by the Main.java Class and the MainWindow.java
     * class
     *
     * Arguments:
     *
     * @return int - A unique ID value.
     */
    public int setId() {
        for (int i = 1; i <= 9999999; i++) {
            boolean taken = false;
            for (TvShow p : tvShows) {
                if (p.getId() == i) {
                    taken = true;
                    break;
                }
            }//parses through all objects in the array
            if (!taken) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Method Name: saveToFile
     *
     * Purpose:
     * Writes all current TV show records from the arrayList collection
     * to the tvShows.txt file. This is used by the Main.java and the MainWindow.java class which relies on the
     * information to ebe saved in a text file for the progress to be saved.
     *
     * Arguments:
     *
     * Return Value:
     *
     */
    public void saveToFile(){
        System.out.println("Saving Tv Shows...");
        try{
            Path path = Paths.get("tvShows.txt");
            Files.writeString(path,"", StandardOpenOption.TRUNCATE_EXISTING);
            for(TvShow p:  tvShows){
                Files.writeString(path,p.toString(),StandardOpenOption.APPEND);
            }
        }catch(IOException e){
            System.out.println("Error saving file try again");
        }
    }

    /**
     * Method Name: loadApp
     *
     * Purpose:
     * Checks whether the main data file exists in the program
     * directory. If the file does not exist, it creates a new file. This is used by the Main.java and the
     * MainWindow.java class.
     *
     * Arguments:
     *
     * Return Value:
     *
     */
    public boolean loadApp(){
        String fileName = "tvShows.txt"; //setting the file name
        try{
            Path path = Path.of(fileName);//the file should exist in the current directory of the running application.
            if(Files.exists(path)){

                //TODO Place the refresh for the table in this section

                System.out.println("The text file tvShows.txt exists");
                BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
                String line;
                while((line = fileReader.readLine()) != null){
                    String[] parts = line.split("-");

                    tvShows.add(new TvShow(Integer.parseInt(parts[0]),parts[1],Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Double.parseDouble(parts[6]),Boolean.parseBoolean(parts[7])));

                }
                return true;

            }else{
                System.out.println("The text file tvShows.txt does not exist... creating file ");

                //TODO Place the refresh for the table in this section

                Files.createFile(path);// creates the file
                return true;
            }
        } catch (Exception e){
            System.out.println("Error loading app process");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return returns the array list
     */
    public ArrayList<TvShow> getTvShows(){
        return tvShows;
    }

    /**
     * adds a tv show of type TvShow to the arraylist
     *
     * @param show type tvShow that is being added to the array.
     */
    public void addShow(TvShow show){
        tvShows.add(show);
    }

    /**
     *
     * @return Connection - returns type connection of the database that the user enters.
     */
    public Connection getConnection(){
        return this.databaseConnection;
    }

    /**
     * @param url sets the URL that contains the location of the database
     */
    public void setUrl(String url){this.url = url;}

    /**
     *
     * @return returns the prefix that is needed to connect to the sqlite database
     */
    public String getSQLPREFIX(){return SQLPREFIX;}

    /**
     *
     * @return returns the location of the user entered sql database
     */
    public String getUrl(){return url;}
}
