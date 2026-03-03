/**
 * Name: Christopher Bain
 * Course: 202620-CEN-3024C-23585
 * Date: 03/03/2026
 * Class Name: TvShowManager
 *
 * This class represents the business logic layer of the TV Show Database
 * Management System. It manages a collection of TvShow objects and provides
 * functionality to perform CRUD.
 *
 * The class is responsible for loading data from a file, validating
 * and managing TV show records, and calculating the average rating.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class TvShowManager {

    private ArrayList<TvShow> tvShows;
    public TvShowManager() {
        tvShows = new ArrayList<TvShow>();
        loadApp();
    }


    /**
     * Method Name: loadFromFile
     *
     * Purpose:
     * Reads TV show data from a user-specified text file, parses each line,
     * and adds valid TV show records to the internal
     *
     * Arguments:
     *
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
     * Method Name: addShow
     *
     * Purpose:
     * Collects input from the user to create a new TV show record,
     * assigns a unique ID, and adds the new TvShow object
     *
     * Arguments:
     *
     *
     * Return Value:
     *
     */

    public void addShow(){
        System.out.println("Adding show...");
        tvShows.add(new TvShow(setId()));

    }

    /**
     * Method Name: getAllShows
     *
     * Purpose:
     * Displays all TV show records currently stored in the system.
     *
     * Arguments:
     *
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
     * record by selecting the record's ID and choosing which field to update.
     *
     * Arguments:
     *
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
                            p.setTitle();
                            break;
                        case 2:
                            p.setAnimated();
                            break;
                        case 3:
                            p.setCompletionMonth();
                            break;
                        case 4:
                            p.setCompletionDate();
                            break;
                        case 5:
                            p.setCompletionYear();
                            break;
                        case 6:
                            p.setOnWatchList();
                            break;
                        case 7:
                            p.setRating();
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
     * Method Name: deleteShow
     *
     * Purpose:
     * Removes a TV show record from the collection based on the ID
     * provided by the user.
     *
     * Arguments:
     *
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
        //hopefully the return inside of the if statement means that if it is found this output gets skipped over
        //TODO Remember to delete this comment after testing.

    }

    /**
     * Method Name: calculateAverageRating
     *
     * Purpose:
     * Calculates and returns the average rating of all TV shows
     * currently stored in the system.
     *
     * Arguments:
     *
     *
     * Return Value:
     * double - The calculated average rating of all TV shows.
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
     * Method Name: takenId
     *
     * Purpose:
     * Determines whether a given ID is already assigned to an existing
     * TV show record in the collection.
     *
     * Arguments:
     * int id - The ID value to check for.
     *
     * Return Value:
     * boolean - Returns true if the ID is already in use, otherwise false.
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
     * currently assigned to any existing TV show record.
     *
     * Arguments:
     *
     *
     * Return Value:
     * int - A unique ID value.
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
     * to the tvShows.txt file
     *
     * Arguments:
     *
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
     * directory. If the file does not exist, it creates a new file.
     *
     * Arguments:
     *
     *
     * Return Value:
     *
     */

    public void loadApp(){
        String fileName = "tvShows.txt"; //setting the file name
        try{
            Path path = Path.of(fileName);//the file should exist in the current directory of the running application.
            if(Files.exists(path)){
                System.out.println("The text file tvShows.txt exists");
                BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
                String line;
                while((line = fileReader.readLine()) != null){
                    String[] parts = line.split("-");

                    tvShows.add(new TvShow(Integer.parseInt(parts[0]),parts[1],Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Double.parseDouble(parts[6]),Boolean.parseBoolean(parts[7])));

                }


            }else{
                System.out.println("The text file tvShows.txt does not exist... creating file ");
                Files.createFile(path);// creates the file
            }
        } catch (Exception e){
            System.out.println("Error loading app process");
            e.printStackTrace();
        }
    }

}
