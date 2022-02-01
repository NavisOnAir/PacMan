package main;

import java.awt.geom.Rectangle2D;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import object.collision.Box;

public class Utils {

    // debug states
    public final String errorState = "ERRORSTATE";
    
    // transforms a string that was converted in getStringFrom2DArray pattern to int[][]
    public int[][] stringTo2DArray(String input) {
        // replaces all symbols that are not needed
        String in = input.replace("\n", "");
        // split input into an string array containing arrays as String so they had to be converted
        String[] stringAr = in.split("},");
        String[] countAr = stringAr[0].split(", ");
        int[][] outputIntAr = new int[stringAr.length][countAr.length];
        for (int i = 0; i < stringAr.length; i++) {
            String[] innerStringAr = stringAr[i].split(", ");
            for (int j = 0; j < innerStringAr.length; j++) {
                // stransforming every string into int number ans assinging to corosponding index
                innerStringAr[j] = innerStringAr[j].replace("}", "");
                innerStringAr[j] = innerStringAr[j].replace(" ", "");
                outputIntAr[i][j] = Integer.parseInt(innerStringAr[j].replace("{", ""));
            }
        }

        return outputIntAr;
    }

    // transforms a int[][] into a String
    public String getStringFrom2DArray(int[][] twoDArray) {
        String output = "{";
        for (int i = 0; i < twoDArray.length; i++) {
            output += "{";
            for (int j = 0; j < twoDArray[0].length - 1; j++) {
                output += Integer.toString(twoDArray[i][j]) + ", ";
            }
            output += Integer.toString(twoDArray[i][twoDArray[i].length - 1]) + "}";
            if (i != twoDArray.length - 1) {
                output += ",";
            }
        }
        output += "}";
        return output;
    }

    // return true if x and y are in rectangle
    public boolean checkRectangle(int x, int y, Rectangle2D reg2D, int regX, int regY) {
        int rWidth = (int) Math.round(reg2D.getWidth());
        int rHight = (int) Math.round(reg2D.getHeight());

        // comparing logic
        boolean boolX = true;
        boolean boolY = true;
        if (y > regY) boolY = false;
        if (y < regY - rHight) boolY = false;
        if (x < regX) boolX = false;
        if (x > regX + rWidth) boolX = false;

        if (boolX && boolY) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBox(Box boxOne, Box boxTwo) {
        return false;
    }

    // get files in directory
    public String[] getFilesInDirectory(String path) {
        File dirFiles = new File(path);
        File[] fileAr = dirFiles.listFiles();
        String[] output = new String[fileAr.length];
        for (int i = 0;i < fileAr.length; i++) {
            output[i] = fileAr[i].getName();
        }
        return output;
    }

    // get file content as string
    public String getFileAsString(String path) {
        try {
            String line;
            String r = "";
            BufferedReader f = new BufferedReader(
                new FileReader(path));
            while ((line = f.readLine()) != null) {
                r += line;
            }
            f.close();
            return r;

        } catch(IOException e) {
            return this.errorState;
        }
    }

    // get x and y of int[][]
    public int[][] getXAndYIn2DIntArray(int[][] intArr, int testObj) {
        int[][] output;
        int count = 0;
        for (int i = 0; i < intArr.length; i++) {
            for (int j = 0; j < intArr[0].length; j++) {
                if (intArr[i][j] == testObj) {
                    count++;
                }
            }
        }
        output = new int[count][2];
        count = 0;
        for (int i = 0; i < intArr.length; i++) {
            for (int j = 0; j < intArr[0].length; j++) {
                if (intArr[i][j] == testObj) {
                    output[count][0] = j;
                    output[count][1] = i;
                    count++;
                }
                if (count == output.length) {
                    return output;
                }
            }
        }
        output = new int[1][2];
        output[0][0] = 1;
        output[0][1] = 1;
        return output;
    }
}
