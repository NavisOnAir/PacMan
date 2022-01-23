package main;

public class utils {
    
    public int[][] stringTo2DArray(String input) {
        /*{{},
        {},
        {}}*/
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
}
