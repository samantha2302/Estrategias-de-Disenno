package estrategias_de_diseño;

import java.util.ArrayList;

public class dynamicAlgorithm {
    static int min = 10000000;
    static int solution[];
    static String combinations = "";
    static int [][] burned = {
        {2,5,9}, //length 3
        {5,6,7,9,8,}, //length 5
        {3,8,13,19,29,31}, //length 6
        {2,7,9,11,15,17,23,29,37} //length 9  
    };
    
    // Look for the minimum waste
    static void minWaste (int [] array, int n){
        solution = new int[array.length];
        int k; //Helps to increment every column
        int tmp; //Temporary minimun waste
        int tmpArr[] = new int[array.length]; //Temporary combination for the minimun waste
        
        //Get all the columns combinations 
        for (int i=1; i<=array.length; i++){
            combinationUtil(array, (new int[i]), 0, array.length-1, 0, i);
        }
        ArrayList<ArrayList> arrayL  = stringToArrayList(combinations);

        //We explore all the combinations
        for(int i = 0; i<arrayL.size(); i++){
            tmp = 0;
            whileA:
            while (true){
                k = 0;
                whileB:
                while(true){
                    tmp+=array[(int)arrayL.get(i).get(k)]; //Increment the temporary minimun waste
                    tmpArr[(int)arrayL.get(i).get(k)]+=1; //Increment the temporary combination
                    if (tmp >= n){ //Enter if the tmp is equal or bigger than the number we're looking for
                        if(tmp<min){ //Enter if we find a better temporary minimun waste
                            cloneArray(tmpArr); 
                            min = tmp; //Change our minimun waste
                            if (tmp == n){ //Enter if we find the best minimun waste (0), then return
                                return;
                            }
                        }
                        k = 0;
                        while(tmp > n){ //Restart all the columns that already reach their maximun
                            tmp-=array[(int)arrayL.get(i).get(k)]*tmpArr[(int)arrayL.get(i).get(k)]; //Substract the columns that are being restarted
                            tmpArr[(int)arrayL.get(i).get(k)]=0; //Restart the column
                            k+=1;
                            if(k>=arrayL.get(i).size()){ //Enter if we exceed the last column of the combination 
                                break whileA; //and we try with another combination
                            }       
                        }
                        tmp+=array[(int)arrayL.get(i).get(k)]; //Increment the temporary minimun waste with the next column
                        tmpArr[(int)arrayL.get(i).get(k)]+=1; //Increment the temporary combination in the next column
                        
                        break whileB;
                    }
                }
            }
        }
    }

    //Clone the values of the arr into the global array solution
    public static void cloneArray(int arr[]){
        for(int i = 0; i<arr.length; i++){
            solution[i] = arr[i];
        }
    }
    
    //Convert the string combinations into a matrix of arrayList
    public static ArrayList<ArrayList> stringToArrayList (String combinations){
        ArrayList<ArrayList> array  = new ArrayList();
        String tmp = ""; //tmp number
        
        array.add(new ArrayList()); //Add the first arrayList into the outer arrayList
        
        for (int i = 0; i<combinations.length(); i++){ // Go trough the whole string
            switch (combinations.charAt(i)) {
                case ';' -> {
                    //If the char is a ';' then add the tmp number into the
                    //last arrayList and add another arrayList to the outer arrayList
                    array.get(array.size()-1).add(Integer.parseInt(tmp));
                    array.add(new ArrayList());
                    tmp = "";
                }
                case ',' -> {
                    //If the char is a ',' then add the tmp number into the last arrayList
                    array.get(array.size()-1).add(Integer.parseInt(tmp));
                    tmp = "";
                }
                default -> tmp += combinations.charAt(i); //Add the digit to the tmp number
            }
        }
        array.remove(array.size()-1); // Substract the last arrayList that was added and doesn't contain any number
        return array;
    }
 
    public static void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r){
         // Current combination is ready to be printed, print it
        if (index == r){
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
            data[index] = i;
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }
    
    //Print the minimun waste of a number with the combinations in the array
    public static void printMinWaste (int arr[], int n) {
        
        minWaste(arr, n);
        System.out.println("Se llego hasta el gasto minimo -> "+(min-n)+" con solucion ->");
        for(int i = 0; i<solution.length; i++){
            System.out.println("\t"+arr[i]+" -> "+solution[i]);
        }

    }
    
    public static void main (String[] args) {        
        printMinWaste(burned[1], 4);
    }
}

