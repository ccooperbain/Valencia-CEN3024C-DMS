

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * Name: Christopher Bain
 * Course: 202620-CEN-3024C-23585
 * Date: 04/06/2026
 * Class Name: Main
 *
 * Program Objective:
 * This program was used as a hands-on learning experience of developing software. This program emphasizes the Use
 * of CRUD (Create Read Update Delete) functionality.
 * This program is a Command Line Interface Database Management System that allows the user to use CRUD
 * methods and shows us a hands-on example of the development of software.
 *
 * This Main class serves as the entry point of the application and is responsible
 * for controlling the program flow, displaying the menu options, and directing user
 * input to the appropriate business logic methods in the TvShowManager class.
 * This project has many entry points and this Main class serves as the entry point of this application for the console
 * version of the application.
 */
public class Main {

    /**
     * Method Name: main
     *
     * Purpose:
     * Serves as the entry point of the program. It initializes the TvShowManager,
     * and displays the menu using the menu() function.
     *
     * @param args takes in any arguments during the launch of the program none are needed for this application.
     *
     */
    public static void main(String[] args) {
        System.out.println("..............Program Start................");
        TvShowManager tvShowManager = new TvShowManager();
        Scanner inputInt = new Scanner(System.in);
        boolean loop = true;

        do{ //looping menu option to take input and execute the function
            try {
                menu();

                switch (inputInt.nextInt()) {
                    case 1:
                        tvShowManager.loadFromFile();
                        break;
                    case 2:
                        tvShowManager.addShow();
                        tvShowManager.getAllShows();
                        break;
                    case 3:
                        tvShowManager.deleteShow();
                        break;
                    case 4:
                        tvShowManager.getAllShows();
                        tvShowManager.updateShow();
                        break;
                    case 5:
                        tvShowManager.getAllShows();
                        break;
                    case 6:
                        //System.out.println(tvShowManager.calculateAverageRating());
                        System.out.printf("%.2f%n", tvShowManager.calculateAverageRating());
                        break;

                    case 0:
                        tvShowManager.saveToFile();
                        loop = false;
                        break;
                    default:
                        System.out.println("Invalid input try again");
                        break;
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid input try again");
                inputInt.nextLine();
            }

        }while(loop);



    }

    /**
     * Method Name: menu
     *
     * Purpose:
     * Displays the available menu options to the user. These options allow
     * the user to perform CRUD operations and other program functions.
     *
     * Arguments:
     *
     * Return Value:
     *
     */
    public static void menu(){
        System.out.println("<.........Menu...........>");
        System.out.println("Enter the corresponding number to make your selection");
        System.out.println("[1] Add TvShows From File ");
        System.out.println("[2] Add Individual Tv Show");
        System.out.println("[3] Remove Tv Show Entry");
        System.out.println("[4] Update Tv Show Entry");
        System.out.println("[5] Show All Shows");
        System.out.println("[6] Calculate Average Rating");
        System.out.println("[0] Exit Application");
        System.out.print("Please enter your choice >");
    }// end of menu function

}
