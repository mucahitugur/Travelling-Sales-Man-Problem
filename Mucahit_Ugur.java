/* This program calculates minimum distance of Migros Car
@author Mucahit Ugur 2021719246
@since date 19.04.2022
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Mucahit_Ugur {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "input04.txt";
        int initialPoint; //declaring initial closest point
        // file object is required to open the file
        File file = new File(fileName);
        // if the file is not found, issue an error message and quit
        if (!file.exists()) {
            System.out.printf("%s can not be found.", fileName);
            System.exit(1); // exit the program
        }
        // scanner object is required to read the contents of the file
        Scanner inputFile = new Scanner(file);
        int i = 0;//temporary variable which will be used for assigning house coordinates to coordinates array
        int k = 0;// temporary variable which will be used for finding length of coordinates except migros

        // this loop find line number of input in order to find size of coordinate two dimensional array
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            k = k + 1;
        }
        file = new File(fileName);
        inputFile = new Scanner(file);
        double[][] coordinates = new double[k][2]; // declaring coordinates two dimensional array
        int migrosPoint = -1;  //initializing migros point
        // continue reading file contents if there is a line to be read
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine(); // get the current line as a string
            String[] strParts = line.split(",");
            int lent; //for checking array length
            lent = strParts.length;
            // when program read input file and length of array is 3 this point has to be migros
            if (lent == 3) {
                migrosPoint = i;
            }
            //when program read input file and length of array is 2 these points have to be houses
            coordinates[i][0] = Double.parseDouble(strParts[0]); //assigning x coordinates of houses
            coordinates[i][1] = Double.parseDouble(strParts[1]);//assigning y coordinates of houses
            i = i + 1;
        }
        // a determines the dimension of the array that contains all possible routes
        int a = 1;
        for (int x = 1; x <= i; x++) {
            a = a * x;
        }
        a = a * (i + 1);
        double[][] num_route_dis = new double[a + 1][i + 1]; //this array will store all possible routes and its distance, 0: the distance of route, 1: first point, 2:second point so on..
        //these three variables will help us to create and store the possible routes
        int num = 0;
        int num1 = 0;
        int num2 = 0;
        //first, we determine the second point after the migros point in this for loop (migros point + the second point is determined after the loop)
        for (int x = 0; x < i; x++) {
            if (x != migrosPoint) {
                num_route_dis[num][1] = migrosPoint;
                num_route_dis[num][2] = x;
                num++;
            }
        }
        num--;
        int y = 3; //
        boolean cont = true;
        // in this while loop, we add 3rd, 4th ... i(th) point to the routes, so at the end of this loop, we get the all possible routes
        while (cont) {
            num2 = num;
            boolean ok = true;
            for (int j = num1; j <= num2; j++) { //we add y(th) point to routes, we ensure there is no dublicate points in the routes
                for (int x = 0; x < i; x++) {
                    ok = true;
                    for (int e = 1; e < y; e++) {
                        if (num_route_dis[j][e] == x) { // checking point x is exist in the array in previous points or not, if yes we can add it, if not, we do not add it
                            ok = false;
                        }
                    }
                    if (ok) {
                        num++;
                        for (int r = 0; r < y; r++) {
                            num_route_dis[num][r] = num_route_dis[j][r];
                        }
                        num_route_dis[num][y] = x;
                    }
                }
            }
            y++;
            if (y == i + 1) {//if the all route length is equal to the total point number, we terminate the while loop
                cont = false;
            }
            num1 = num2 + 1;
        }

        num2 = num;
        double minDis = Double.MAX_VALUE;
        int min = 0;
        //we calculate the distance of all routes generated and choose one that has the minimum distance
        for (int j = num1; j <= num2; j++) {
            for (int x=2;x<=i;x++){ //calculate distances between points
                num_route_dis[j][0] = num_route_dis[j][0] + Math.pow(Math.pow(coordinates[(int) num_route_dis[j][x-1]][0] - coordinates[(int) num_route_dis[j][x]][0], 2) + Math.pow(coordinates[(int) num_route_dis[j][x-1]][1] - coordinates[(int) num_route_dis[j][x]][1], 2), 0.5);
            }
            num_route_dis[j][0] = num_route_dis[j][0] + Math.pow(Math.pow(coordinates[(int) num_route_dis[j][i]][0] - coordinates[migrosPoint][0], 2) + Math.pow(coordinates[(int) num_route_dis[j][i]][1] - coordinates[migrosPoint][1], 2), 0.5);
            if (num_route_dis[j][0] < minDis) {//choosing the the route that has the minimum distance
                minDis = num_route_dis[j][0];
                min = j;
            }
        }
        //print the route that has the minimum distance
        System.out.println("Distance: " + num_route_dis[min][0]);
        System.out.print("Route: ");
        for (int x= 1;x<=i;x++){
            System.out.print( ((int)num_route_dis[min][x]+1) + " - ");
        }
        System.out.print(migrosPoint+1);

    }
}
