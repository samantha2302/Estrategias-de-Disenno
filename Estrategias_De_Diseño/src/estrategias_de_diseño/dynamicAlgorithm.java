package estrategias_de_diseño;

import java.io.*;
import java.util.ArrayList;

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
        //Get all the columns combinations 
        for (int i=1; i<=array.length; i++){
            combinationUtil(array, (new int[i]), 0, array.length-1, 0, i);
        }
        // System.out.print(combinations);
        //Convert the combinations in ArrayList
        ArrayList<ArrayList> arrayL  = combinationToArrayList(combinations);

        System.out.println(arrayL.toString());

    }

    public static void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r){
         // Current combination is ready to be printed, print it
        if (index == r)
        {
            combinations+=data[0];
            for (int j=1; j<r; j++)
                combinations += (","+data[j]);
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
 
    public static ArrayList<ArrayList> combinationToArrayList (String combinations){
        ArrayList<ArrayList> array  = new ArrayList();
        array.add(new ArrayList());
        String tmp = "";
        for (int i = 0; i<combinations.length(); i++){
            switch (combinations.charAt(i)) {
                case ';':
                    array.get(array.size()-1).add(Integer.parseInt(tmp));
                    array.add(new ArrayList());
                    tmp = "";
                    break;
                case ',':
                    array.get(array.size()-1).add(Integer.parseInt(tmp));
                    tmp = "";                    
                    break;
                default:
                    tmp += combinations.charAt(i);
                    break;
            }
        }
        array.remove(array.size()-1);
        return array;
    }
 
    /*Driver function to check for above function*/
    public static void main (String[] args) {
        //String jeje = "2;5;9;2,5;2,9;5,9;2,5,9;";
        // int [] array = {2,5,9};
        // for (int i=1; i<=array.length; i++){
        //     combinationUtil(array, (new int[i]), 0, array.length-1, 0, i);
        // }
        // System.out.print(combinations);
        // ArrayList<ArrayList> arrayjj  = combinationToArrayList(jeje);
        // for (ArrayList arrayList1 : arrayjj) {
        //     System.out.println("Nueva mini lista");
        //     for (Object object : arrayList1) {
        //         System.out.print((Integer)object+" - ");
        //     }
        // }
        for(int i = 0; i<burned.length; i++){
            minWaste(burned[i]);
            System.out.println("\n\n===============================\n\n");
            combinations="";
        }
    }
}

