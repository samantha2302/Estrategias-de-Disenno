package estrategias_de_diseño;

import java.util.ArrayList;

public class dynamicAlgorithm {
    
    private int dynamicComparisons = 0; //comparasions variable
    private int dynamicAssignments = 3; //assignments variable, min, solution, combination assignments
    private float dynamicMemory = 4;      //memory variable, min, solution 2, combination memory bytes

    private int min = 10000000;          //variable minimum total waste
    private int solution[];              //contains the best package and amounts combination
    private String combinations = "";    //save all possible combinations
    private Records records;
    
    public dynamicAlgorithm(Records records){
        this.records = records;
    }
    
    /**
     * 
     * @param array
     * @param n 
     */
    // Look for the minimum waste
    private void minWaste (int [] array, int n){
        solution = new int[array.length];//assign the correct length of the array solution
        int k; //Helps to increment every column
        int tmp; //Temporary minimun waste
        int tmpArr[] = new int[array.length]; //Temporary combination for the minimun waste
        

        dynamicMemory=+8;                   //n, k, tmp and i memory bytes
        dynamicMemory=+array.length*2;      //array memory bytes
        dynamicMemory=+tmpArr.length*2;     //tmpArr memory bytes
        dynamicMemory=+solution.length*2;   //solution memory bytes
        dynamicAssignments=+4;              //array, n, solution, tmpArr assignments

        dynamicAssignments++;               //i assignment
        for (int i=1; i<=array.length; i++){
            dynamicAssignments++;           //i assignment
            dynamicComparisons++;           //for comparisons
            combinationUtil(array, (new int[i]), 0, array.length-1, 0, i); //Get all the columns combinations
        }
        dynamicComparisons ++; //for false comparisson
        ArrayList<ArrayList> arrayL  = stringToArrayList(combinations); //converts the string from combination util to a array
        dynamicAssignments++; //arrayL assignment 

        //We explore all the combinations
        dynamicAssignments++;            //i assignment
        dynamicMemory+=2;                //i memory bytes
        dynamicMemory+=arrayL.size()*2;  //arrayL memory bytes
        for(int i = 0; i<arrayL.size(); i++){
            dynamicAssignments+=2;       //tmp, i assignment
            dynamicComparisons++;        //for comparisons
            tmp = 0;                    //temporary minimun waste
            whileA:
            while (true){
                dynamicComparisons++;   //while comparisons
                k = 0;                  //goes through the indexes
                dynamicAssignments++;   //k assingment 
                whileB:                
                while(true){
                    dynamicComparisons++;   //while comparisons
                    tmp+=array[(int)arrayL.get(i).get(k)]; //Increment the temporary minimun waste
                    tmpArr[(int)arrayL.get(i).get(k)]+=1; //Increment the temporary combination
                    dynamicAssignments+=2;  //tmp, tmpArr assignments
                    dynamicComparisons++;  //if comparison
                    if (tmp >= n){ //Enter if the tmp is equal or bigger than the number we're looking for
                        dynamicComparisons++;  //if comparison
                        if(tmp<min){ //Enter if we find a better temporary minimun waste
                            cloneArray(tmpArr);     //clones tmpArr
                            min = tmp; //Change our minimun waste
                            dynamicAssignments++;   //min assignment
                            dynamicComparisons++;  //if comparison
                            if (tmp == n){ //Enter if we find the best minimun waste (0), then return
                                return;
                            }
                        }
                        k = 0;                      //set k to 0
                        dynamicAssignments++;       //k assignment
                        while(tmp > n){ //Restart all the columns that already reach their maximun
                            dynamicComparisons++;   //while comparisons
                            tmp-=array[(int)arrayL.get(i).get(k)]*tmpArr[(int)arrayL.get(i).get(k)]; //Substract the columns that are being restarted
                            tmpArr[(int)arrayL.get(i).get(k)]=0; //Restart the column
                            k+=1;
                            dynamicAssignments+=3;  //k assignment
                            dynamicComparisons++;   //if comparison
                            if(k>=arrayL.get(i).size()){ //Enter if we exceed the last column of the combination 
                                break whileA; //and we try with another combination
                            }       
                        }
                        dynamicComparisons ++; //While false comparisson
                        tmp+=array[(int)arrayL.get(i).get(k)]; //Increment the temporary minimun waste with the next column
                        tmpArr[(int)arrayL.get(i).get(k)]+=1; //Increment the temporary combination in the next column
                        dynamicAssignments+=2;
                        break whileB;
                    }
                }
                dynamicComparisons ++; //While false comparisson
            }
            dynamicComparisons ++; //for false comparisson
        }
        dynamicComparisons ++; //for false comparisson
    }

    /**
     * 
     * @param arr 
     */
    //Clone the values of the arr into the global array solution
    public void cloneArray(int arr[]){
        dynamicMemory+=2; //i memory bytes
        dynamicMemory+=arr.length*2;
        dynamicAssignments+=2;
        for(int i = 0; i<arr.length; i++){
            dynamicComparisons++;
            solution[i] = arr[i];
            dynamicAssignments+=2;
        }
        dynamicComparisons ++; //For false comparisson
    }
    
    /**
     * 
     * @param combinations
     * @return 
     */
    //Convert the string combinations into a matrix of arrayList
    public ArrayList<ArrayList> stringToArrayList (String combinations){
        ArrayList<ArrayList> array  = new ArrayList();
        String tmp = ""; //tmp number
        dynamicMemory+=4;
        
        
        
        array.add(new ArrayList()); //Add the first arrayList into the outer arrayList
        dynamicAssignments+=4;
        dynamicComparisons++;
        dynamicMemory+=2; //i memory bytes
        for (int i = 0; i<combinations.length(); i++){ // Go trough the whole string
        dynamicAssignments++;
        dynamicComparisons+=3;
            switch (combinations.charAt(i)) {
                case ';' -> {
                    //If the char is a ';' then add the tmp number into the
                    //last arrayList and add another arrayList to the outer arrayList
                    array.get(array.size()-1).add(Integer.parseInt(tmp));
                    array.add(new ArrayList());
                    dynamicMemory+=2;
                    tmp = "";
                    dynamicAssignments+=2;
                }
                case ',' -> {
                    //If the char is a ',' then add the tmp number into the last arrayList
                    array.get(array.size()-1).add(Integer.parseInt(tmp));
                    dynamicMemory+=2;
                    tmp = "";
                    dynamicAssignments+=2;
                }
                default -> {tmp += combinations.charAt(i); dynamicAssignments++;} //Add the digit to the tmp number
                
            }
        }
        dynamicComparisons ++; //For false comparisson

        array.remove(array.size()-1); // Substract the last arrayList that was added and doesn't contain any number
        dynamicAssignments++;
        return array;
        
    }
 
    /**
     * 
     * @param arr
     * @param data
     * @param start
     * @param end
     * @param index
     * @param r 
     */
    public void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r){
         // Current combination is ready to be printed, print it
        dynamicAssignments+=6;
        dynamicMemory+=8;
        dynamicMemory+=arr.length*2;
        dynamicMemory+=data.length*2;
        
        dynamicComparisons++; 
        if (index == r){
            combinations+=data[0];
            dynamicAssignments+=2;
            dynamicComparisons++;
            for (int j=1; j<r; j++){
                dynamicComparisons++;
                combinations += (","+data[j]);
                dynamicAssignments+=2;
            }
            dynamicComparisons ++; //For false comparisson

            combinations += (";");
            dynamicAssignments++;
            return;
        }
        
         // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        dynamicAssignments++;
        dynamicMemory+=2; //i memory bytes
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            dynamicComparisons++;
            dynamicAssignments+=2;
            data[index] = i;
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
        dynamicComparisons ++; //For false comparisson
    }

    /**
     * 
     * @param array
     * @return 
     */
    public String arrayToString(int [] array){
        String x = ""+array[0];
        dynamicAssignments+=3;
        dynamicMemory+=array.length*2;
        dynamicMemory+=2; //i memory bytes
        for(int i = 1; i<array.length; i++){
            dynamicComparisons++;
            dynamicAssignments+=2;
            x+=", "+array[i];
        }
        dynamicComparisons++; //For false comparisson
        dynamicAssignments++;
        return x;
    }
    
    /**
     * 
     * @param arr
     * @param n 
     */
    //Print the minimun waste of a number with the combinations in the array
    public void printMinWaste (int arr[], int n) {
        minWaste(arr, n);
        dynamicAssignments+=2;
        dynamicMemory++;
        dynamicMemory+=arr.length*2;

        records.addToWasteAndCombination("\nLa mejor combinación del código dinámico es: " + 
        arrayToString(solution) + 
        ", con un desperdicio de: " + (min-n));
        records.setDynamicAssignments(dynamicAssignments);
        records.setDynamicComparisons(dynamicComparisons);
        records.setDynamicMemory(dynamicMemory);
        
    }
    
    
}

