package main;

import java.awt.geom.Rectangle2D;

public class Utils {
    
    // transforms a string that was converted in getStringFrom2DArray pattern to int[][]
    public int[][] stringTo2DArray(String input) {
        // split input into an string array containing arrays as String so they had to be converted
        String[] stringAr = input.split("},");
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
}
