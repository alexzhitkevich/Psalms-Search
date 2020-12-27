/**
 ** Alexandra Zhitkevich *
 * Psalms Search: User inputs a psalm number that corresponds with the title of a psalm, the program will search for the title and if existent, will print it out for the user
 */
package Zhitkevich_Alexandra_SearchingAssignment;

import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Alexnik
 */
public class Zhitkevich_Alexandra_SearchingAssignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int x = 0;
        int indexOfNum = 0;
        int searchNum = 0;
        int highest = 0;
        int lowest = 0;
        boolean checkValidInput = false;
        boolean validInput2 = false;
        boolean program = true;
        String continueProgram = "";

        //Array for the existing Psalm numbers from the file
        int[] psalmNumbers = new int[24];
        //Array for the corresponding Psalm titles from the file
        String[] psalmName = new String[24];

        try {
            FileReader fr = new FileReader("Psalms.txt");
            Scanner t = new Scanner(fr);

            while (t.hasNextLine()) {
                psalmNumbers[x] = t.nextInt();
                t.nextLine();
                psalmName[x] = t.nextLine();
                x++;
            }

            t.close();

        } catch (IOException e) {
            System.out.println("Error reading from file");
        }

        //initializing of the variables left and right for the method binarySearch
        int left = 0;
        int right = psalmNumbers.length - 1;

        while (program == true) {
            Scanner m = new Scanner(System.in);

            do {
                try {
                    System.out.println("Please enter the number of the Psalm title you would like (1 - 99) ");
                    searchNum = s.nextInt();
                    checkValidInput = true;

                    //if value inputted is not in the range of 1 - 99, the program will let the user know that it is outside of the range
                    if (searchNum <= 0 || searchNum > 99) {
                        System.out.println("The value chosen is out of bounds, please enter a number between 1 and 99");
                        System.out.println("---------------------------");
                        checkValidInput = false;
                    }
                } catch (InputMismatchException e) {
                    //if the value inputted is not an integer value, the program will let the user know to input a number
                    System.out.println("Invalid input, please enter a number");
                    System.out.println("---------------------------");
                    checkValidInput = false;
                    s.nextLine();
                }
            } while (checkValidInput == false);

            indexOfNum = binarySearch(psalmNumbers, left, right, searchNum);

            //if the integer returned by the method is equal to -1, the Psalm number does not exist in the array. Else, it will print the Psalm title
            if (indexOfNum == -1) {
                System.out.println("Sorry, this Psalm number does not exist in the array");

                if (searchNum > 94) {
                    System.out.println("The nearest Psalm title found below the number you search for is: Psalm " + psalmNumbers[23] + "- " + psalmName[23]);
                } else {
                    //will check for the two nearest Psalm numbers to the number chosen and will print their titles
                    for (int i = 0; i < psalmNumbers.length; i++) {
                        if (psalmNumbers[i] < searchNum && psalmNumbers[i + 1] > searchNum) {
                            highest = i + 1;
                            lowest = i;
                        }
                    }

                    System.out.println("The nearest Psalm title found below the number you searched for is: Psalm " + psalmNumbers[lowest] + "- " + psalmName[lowest]);
                    System.out.println("The nearest Psalm title found above the number you searched for is: Psalm " + psalmNumbers[highest] + "- " + psalmName[highest]);
                }
                System.out.println("---------------------------");

            } else {
                System.out.println("The Psalm Title is: " + psalmName[indexOfNum]);
            }

            //Prompts if the user would like to run the program again to search for another Psalm title
            do {
                System.out.println("Would you like to run the program again? Press Y - Yes or N - No");
                continueProgram = m.nextLine();

                if (continueProgram.equalsIgnoreCase("Y")) {
                    validInput2 = true;
                    program = true;
                } else if (continueProgram.equalsIgnoreCase("N")) {
                    validInput2 = true;
                    program = false;
                } else {
                    validInput2 = false;
                    System.out.println("Invalid input, please enter either Y - Yes or N - No");
                }
                System.out.println("----------------------------");
            } while (validInput2 == false);

        }
    }

    /**
     * Description: Method 'binarySearch' goes through the array 'psalmNumbers'
     * and based on the left and right values, checks if the variable
     * 'searchNum' is found in the array.
     *
     * Precondition: For the method to run, the array 'psalmNumbers' must be
     * declared and initialized with integer values. 'Left' and 'right'
     * variables must also be declared and initialized. There must be a valid
     * input for variable 'searchNum'.
     *
     * Post condition: When the method is complete, the user will know whether
     * the Psalm number that they would like to see the title for exists or not.
     *
     * @param psalmNumbers - This parameter is the array that has all of the
     * existing Psalm numbers in it.
     * @param left - This parameter is the value of the most left element in the
     * array
     * @param right - This parameter is the value of the most right element in
     * the array
     * @param searchNum - This parameter is the value of the Psalm number that
     * the user would like to search for
     * @return integer value is returned.
     */
    public static int binarySearch(int psalmNumbers[], int left, int right, int searchNum) {
        //Printing out the array and the number being searched for after each recursive call
        System.out.print("Searching array: ");
        for (int i = left; i <= right; i++) {
            System.out.print("[" + psalmNumbers[i] + "]");
        }
        System.out.println(" for " + searchNum);

        int middle;

        //base case: if left > right, the number does not exist in the array psalmNumbers
        if (left > right) {
            return -1;
        }

        //find the middle index of the array
        middle = (left + right) / 2;

        if (psalmNumbers[middle] == searchNum) {
            return middle;
        }

        if (searchNum < psalmNumbers[middle]) {
            return binarySearch(psalmNumbers, left, middle - 1, searchNum);
        } else {
            return binarySearch(psalmNumbers, middle + 1, right, searchNum);
        }
    }

}
