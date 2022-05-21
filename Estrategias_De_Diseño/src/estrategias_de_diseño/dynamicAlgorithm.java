package estrategias_de_diseño;

import java.io.*;

public class dynamicAlgorithm {
    
    static String combinations = "";
    static int [][] burned = { //
        {2,5,9}, //3
        {7,9,8,6,5}, //5
        {3,8,13,19,29,31}, //6
        {2,7,9,11,15,17,23,29,37} //9  
    };
    
    // Look for the minimum spend
    static void minWaste (int [] array){
       for (int i=1; i<=array.length; i++){
            combinationUtil(array, (new int[i]), 0, array.length-1, 0, i);
        }
        
        System.out.println(combinations);
        //2;5;9;2,5;2,9;5,9;
        String arrlol1[] = combinations.split(";");
        //{"2","5","9","2,5"...}
        ArrayList<ArrayList<int>> lol2  = new ArrayList();
        ArrayList<ArrayList<String>> temp;
        for(int j=0; j<arrlol1.length; j++){
            temp = arrlol2[j].split(","); //["2"],["5"],["9"],["2","5"], ["2","9"]
            for (int i=0; i<tmp.length; i++)
                lol2.add(temp[i].toInteger());
        }
    }

    public static void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r){
         // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++)
                combinations += (data[j]+",");
            combinations += (";");

            
            return;
        }
 
        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }
 
    
 
    /*Driver function to check for above function*/
    public static void main (String[] args) {
        minWaste (burned[0]);
    }
}

