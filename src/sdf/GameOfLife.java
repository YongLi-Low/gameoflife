package sdf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class GameOfLife {

    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        String fileName = args[0];

        if (args[0].isEmpty()) {
            System.out.println("Please key in the file name at args[0]");
        }
        
        Reader r = new FileReader(fileName);
        BufferedReader br = new BufferedReader(r);  // reading the file

        String input;
        String[] textInfo;

        // Declaring an array for the current grid and next grid
        int row = 0;
        int col = 0;
        String[][] currentGrid = new String[row][col];
        String[][] nextGrid = new String[row][col];

        // Storing the initial starting positions X and Y
        int x = 0;  // column
        int y = 0;  // row

        boolean isData = false;

        // Reading the text
        while (null != (input = br.readLine())) {
            // input = input.trim();  // remove white spaces
            // if line is empty, discard it
            if (input.length() == 0) {
                continue;
            }

            if (!isData) {
                // split the line by space
                textInfo = input.split(" ");
                
                // if first character is "#", ignore/discard it
                if (textInfo[0].equals("#")) {
                    continue;
                }

                if (textInfo[0].equalsIgnoreCase("grid")) {
                    row = Integer.parseInt(textInfo[1]);  // These will be the size of the grid
                    col = Integer.parseInt(textInfo[2]);

                    currentGrid = new String[row][col];  // set the size of the grid, inside is null values
                    nextGrid = new String[row][col];

                    // change the null values to spaces
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            currentGrid[i][j] = " ";
                            nextGrid[i][j] = " ";
                        }
                    }
                }

                if (textInfo[0].equalsIgnoreCase("start")) {
                    x = Integer.parseInt(textInfo[1]);
                    y = Integer.parseInt(textInfo[2]);
                }

                if (textInfo[0].equalsIgnoreCase("data")) {
                    isData = true;
                    continue;  // no need to print out "DATA"
                }

                System.out.println(Arrays.toString(textInfo));
            }

            if (isData) {
                //  split out the data
                textInfo = input.split("");

                // System.out.println(Arrays.toString(textInfo));
                // System.out.println(textInfo.length);

                int columnCount = x;

                for (int i = 0; i < textInfo.length; i++) {
                    // System.out.println(textInfo[i]);
                    currentGrid[y][columnCount] = textInfo[i];  // y = row, x = col
                    columnCount += 1;  // move on to next column
                }
                columnCount = x;
                y += 1;  // after finish filling the row, move on to next row.
            }
        }

        br.close();
        r.close();
        

        for (int k = 0; k < 5; k++) {  // playing 5 rounds of game of life
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (checkNeighbours(currentGrid, j, i) == 1) {
                        nextGrid[i][j] = " ";
                    }
                    else if (checkNeighbours(currentGrid, j, i) == 2 && currentGrid[i][j].equals("*")) {
                        nextGrid[i][j] = "*";
                    }
                    else if (checkNeighbours(currentGrid, j, i) == 2) {
                        nextGrid[i][j] = " ";
                    }
                    else if (checkNeighbours(currentGrid, j, i) == 3) {
                        nextGrid[i][j] = "*";
                    }
                    else if (checkNeighbours(currentGrid, j, i) >= 4) {
                        nextGrid[i][j] = " ";
                    }
                }
            }
            // Print out next grid
            for (int i = 0; i < row; i++) {
                System.out.println(Arrays.toString(nextGrid[i]));
            }
            System.out.println();
            // next grid will become the current grid. need to use for loop to copy...
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    currentGrid[i][j] = nextGrid[i][j];
                }
            } 
            
            // Initialise next grid to empty spaces again
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    nextGrid[i][j] = " ";
                }
            }
        }
    }

    // Method to check number of neighbours for a point in the grid
    public static int checkNeighbours(String[][] grid, int x, int y) {  // take in number of col (x) and row (y) as argument
        int neighbourCount = 0;
        int numRow = grid.length;
        int numCol = grid[0].length;
        
        if ((x - 1 >= 0) && (y - 1 >= 0)) {  // top left
            if (grid[y-1][x-1].equals("*")) {
                neighbourCount += 1;
            }
        }
        if (y - 1 >= 0) {  // top
            if (grid[y-1][x].equals("*")) {
                neighbourCount += 1;
            }
        }
        if ((x + 1 < numCol) && (y - 1 >= 0)) {  // top right
            if (grid[y-1][x + 1].equals("*")) {
                neighbourCount += 1;
            }
        }
        if (x - 1 >= 0) {  // left
            if (grid[y][x-1].equals("*")) {
                neighbourCount += 1;
            }
        }
        if (x + 1 < numCol) {  // right
            if (grid[y][x+1].equals("*")) {
                neighbourCount += 1;
            }
        }
        if ((x - 1 >= 0) && (y + 1 < numRow)) {  // bottom left
            if (grid[y+1][x-1].equals("*")) {
                neighbourCount += 1;
            }
        }
        if (y + 1 < numRow) {  // bottom
            if (grid[y+1][x].equals("*")) {
                neighbourCount += 1;
            }
        }
        if ((x + 1 < numCol) && (y + 1 < numRow)) {  // bottom right
            if (grid[y+1][x + 1].equals("*")) {
                neighbourCount += 1;
            }
        }

        return neighbourCount;
    }

}