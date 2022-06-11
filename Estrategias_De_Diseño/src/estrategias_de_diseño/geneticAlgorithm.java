package estrategias_de_diseño;

import java.util.Random;
import java.util.ArrayList;

public class geneticAlgorithm {

    private int geneticComparisons = 0; // Variable Assignment
    private int geneticAssignments = 3; // Variable Comparison
    private float memoryGenetic = 0; // Variable Memory
    private int geneticComparisonsUC = 0; // Variable Assignment
    private int geneticAssignmentsUC = 3; // Variable Comparison
    private float memoryGeneticUC = 0; // Variable Memory
    private int geneticComparisonsTPC = 0; // Variable Assignment
    private int geneticAssignmentsTPC = 3; // Variable Comparison
    private float memoryGeneticTPC = 0; // Variable Memory

    private Random r = new Random();
    
    private int populationUC[][]; //Array of population of the uniform crossing
    private int populationTPC[][]; //Array of population of the crossing of two points
    private ArrayList<int[]> arrayLpopulationUC = new ArrayList<int[]>(); // ArrayList of population of the uniform crossing
    private ArrayList<int[]> arrayLpopulationTPC = new ArrayList<int[]>(); //ArrayList of population of the crossing of two points
    private int mask[]; //Uniform Crossing Mask Array
    private int arrayPresentations[]; //Presentations Array
    private int n; //number that we are trying to reach
    private Records records; //Records where we store the info

    /**
     * 
     * @param records
     * @param array
     * @param num 
     */
    public geneticAlgorithm(Records records, int [] array, int num){
        this.records = records;
        arrayPresentations = array;
        n = num;
        geneticAssignments+=4;
        memoryGenetic+=array.length*4;
    }

    /**
     * 
     * @param size 
     */
    private void defPopSize(int size){
        geneticAssignments+=2; //Size and populationSize
        memoryGenetic+=4; //Size and populationSize
        int populationSize;
        geneticComparisons += 3; //switch comparisons
        switch (size) {
            case 3 -> {populationSize = 3; break;}
            case 5 -> {populationSize = 5; break;}
            case 6 -> {populationSize = 10; break;}
            default -> populationSize = 20; 
        }
        populationUC = new int[populationSize][size]; //Assign the matrix sizes according to the number of presentations
        populationTPC = new int[populationSize][size]; //Assign the matrix sizes according to the number of presentations
        memoryGenetic+=populationUC.length*populationUC[0].length*2; //populationUC
        memoryGenetic+=populationTPC.length*populationTPC[0].length*2; //populationTPC
        geneticAssignments+=3; //population size, populatioUC, populationTPC
    }
    
    /**
     * 
     * @param size
     * @return 
     */
    private int defCrossoverNum(int size){
        geneticAssignments+=2; //Size and return
        memoryGenetic+=2; //Size
        geneticComparisons += 3; //switch comparisons
        switch (size) {
            case 3 -> {return 10; }
            case 5 -> {return 10; }
            case 6 -> {return 20; }
            default -> {return 20; }
        } 
    }
    /**
     * 
     * @param size
     * @return 
     */
    private int defChildNum(int size){
        geneticAssignments+=2; //Size and return
        memoryGenetic+=2; //Size
        geneticComparisons += 3; //switch comparisons
        switch (size) {
            case 3 -> {return 6; }
            case 5 -> {return 10; }
            case 6 -> {return 20; }
            default -> {return 40; }
        } 
    }
    
    /**
     * 
     */
    public void generatePopulation(){
        defPopSize(arrayPresentations.length);
        int restmp; //temporary results
        int numTMP; //temporary Quantity 
        geneticAssignments++; //i
        memoryGenetic+=6; //restmp, numTMP, i
        for(int i = 0; i < populationUC.length; i++){
            geneticComparisons++; //for comparissons
            restmp = 0; //restart value to 0
            geneticAssignments+=3; //restmp, j, i
            memoryGenetic+=2; //i memory bytes
            for(int j = 0; j < arrayPresentations.length; j++){
                geneticComparisons++;//for comparissons
                numTMP = r.nextInt((n/arrayPresentations[j])+1); //quantity of the genes
                populationUC[i][j] = numTMP;
                populationTPC[i][j] = numTMP;
                restmp += numTMP*arrayPresentations[j]; 
                geneticAssignments+=5; //j,numTMP,populationUC,populationTPC,restmp
            }
            geneticComparisons++;//for false comparisson
            geneticComparisons++;//if comparisson
            if (restmp == n) { i--; geneticAssignments++;} //If get to 0 on the minWaste then do the proccess again
            else{
                insertOrdered(arrayLpopulationUC, populationTPC[i]); //Insert the person on the arrayList
                insertOrdered(arrayLpopulationTPC, populationTPC[i]);} //Insert the person on the arrayList
        }
        geneticComparisons ++; //for false comparisson 
    }
    /**
     * 
     */
    private void generateMask(){
        mask = new int[arrayPresentations.length]; //mark to make the crossover
        memoryGeneticUC+=mask.length*2; //mask
        memoryGeneticUC+=2; //i memory bytes
        geneticAssignmentsUC+=2; //mask, i
        for(int i = 0; i < arrayPresentations.length; i++){
            geneticComparisonsUC+=2;//for and if comparissons
            geneticAssignmentsUC++; //i
            
            if(((int)i/2)*2==i){
                mask[i]=1;
                geneticAssignmentsUC++; // mask
            }
            
        }
        geneticComparisonsUC++;//for false comparissons
    }
    /**
     * @param array
     * @return 
     */
    public int minimWaste(int[]array){
        int min = n; //minWaste
        memoryGenetic+=4; //mi, i
        memoryGenetic+=array.length*2;
        geneticAssignments+=3; //array, min, i
        for(int i = 0; i < arrayPresentations.length; i++){
            geneticComparisons++;//for comparissons
            min -= arrayPresentations[i]*array[i]; //Rest the presentation * quantity to min to get the minwaste
            geneticAssignments+=2; //min, i
        }
        geneticComparisons++;//for false comparissons
        geneticAssignments++;//return
        return -(min);
    }
    /**
     * 
     */
    private void aptitudeFunctionUC(){
        geneticAssignmentsUC++;//i
        memoryGeneticUC+=2; //i memory bytes
        //Get the first people in the arraylist to make the population
        for(int i = 0; i < populationUC.length; i++){
            geneticComparisonsUC++;//for comparissons
            populationUC[i] = arrayLpopulationUC.get(i); 
            geneticAssignmentsUC+=2;//populationUC, i
        }
        geneticComparisonsUC++;//for false comparissons
    }

    /**
     * 
     */
    private void aptitudeFunctionTPC(){
        geneticAssignmentsTPC++;//i
        memoryGeneticTPC+=2; //i memory bytes
        //Get the first people in the arraylist to make the population
        for(int i = 0; i < populationTPC.length; i++){
            geneticComparisonsTPC++;//for comparissons
            populationTPC[i] = arrayLpopulationTPC.get(i);
            geneticAssignmentsTPC+=2;//populationTPC, i
        }
        geneticComparisonsTPC++;//for false comparissons
    }

    /**
     * 
     * @param arrayL
     * @param array 
     */
    private void insertOrdered(ArrayList<int[]> arrayL, int[] array ){
        int [] tmp1; //Temporary array to sustitute in the arrayList
        int [] tmp2 = array; //Temporary array to sustitute in the arrayList
        boolean a = false; //Value to know when we get a place to insert array
        memoryGenetic+=2*array.length*2+0.125; //tmp1, tmp2, a
        memoryGenetic+=arrayL.size()*array.length*2; 
        memoryGenetic+=array.length*2;
        geneticAssignments+=6; //arrayL, array, tmp1, tmp2, a, i
        for(int i = 0; i < arrayL.size(); i++){
            geneticAssignments++; //i
            geneticComparisons+=3; //for, if, else comparissons
            if (a){ //Sustitute in arrayL tmp2
                tmp1 = arrayL.get(i); //Store arrayL.get(i) before replacing it
                arrayL.set(i, tmp2); //Replace arrayL.get(i) with tmp2
                tmp2 = tmp1; //Store tmp1 in tmp2 to replace the next one
                geneticAssignments+=3; //tmp1, tmp2, arraL
            }//Sustitute in arrayL array
            else if ((minimWaste(array)>0 && minimWaste(arrayL.get(i))>0 && minimWaste(arrayL.get(i))>minimWaste(array)) ||
                    (minimWaste(arrayL.get(i))<0 && minimWaste(arrayL.get(i))<minimWaste(array))){ //Sustitute in arrayL tmp2
                tmp2 = arrayL.get(i); //Store arrayL.get(i) before replacing it   
                arrayL.set(i, array); //Replace arrayL.get(i) with array
                a = true; //Set a true to keep replacing the next ones
                geneticAssignments+=3;
            }
        }
        geneticComparisons++;//for false comparissons
        arrayL.add(tmp2); //add the last one
        geneticAssignments++;
        
    }
    /**
     * 
     * @param parent1
     * @param parent2 
     */
    private void uniformCrossover(int[] parent1, int[] parent2){
        int children[][] = new int [2][parent1.length]; //child1 and child2
        memoryGeneticUC+=parent1.length*8; //parent1, parent2, children[0] and children[1]
        geneticAssignmentsUC+=4; //parent1, parent2, children, i
        memoryGeneticUC+=2; //i memory bytes
        for (int i = 0; i < parent1.length; i++) {
            geneticAssignmentsUC++;//i
            geneticComparisonsUC+=2;//for, if comparissons
            if (mask[i]==0){ //Add from parent1 to children[0] and parent2 to children[1]
                children[0][i] = parent1[i];
                children[1][i] = parent2[i];
                geneticAssignmentsUC+=2;//children[0], children[1]
            }
            else{//Add from parent1 to children[1] and parent2 to children[0]
                children[0][i] = parent2[i];
                children[1][i] = parent1[i];
                geneticAssignmentsUC+=2;//children[0], children[1]
            }
        }
        geneticComparisonsUC++;//for false comparissons
        
        insertOrdered(arrayLpopulationUC, children[0]); //Insert children[0]
        insertOrdered(arrayLpopulationUC, children[1]); //Insert children[1]
        String x; //Record the proccess 
        x="\n==========================\n";
        x+="\nPadre 1: ["+arrayToString(parent1)+"]";
        x+="\tPadre 2: ["+arrayToString(parent2)+"]";
        x+="\nHijo 1: ["+arrayToString(children[0])+"] Puntuacion: "+(arrayLpopulationUC.indexOf(children[0])+1);
        x+="\nHijo 2: ["+arrayToString(children[1])+"] Puntuacion: "+(arrayLpopulationUC.indexOf(children[1])+1);
        records.addCrossoverUC(x); //Add the string to the record
    }
    /**
     * 
     * @param parent1
     * @param parent2 
     */
    private void twoPointsCrossover(int[] parent1, int[] parent2){
        int crosspoint1 = r.nextInt((parent1.length/2)+1); //Decides the First point to cross
        int crosspoint2 = (parent1.length/2) + r.nextInt((parent1.length/2)+1); //Decides the second point to cross
        int[] child1 = new int[parent1.length]; //child 0
        int[] child2 = new int[parent1.length]; //child 1
        geneticAssignmentsTPC+=6; //parent1, parent2, crosspoint1, corsspoint2, child1, child2
        memoryGeneticTPC+=4;//crosspoint1, crosspoint2
        memoryGeneticTPC+=parent1.length*8; //parent1, parent2, child1, child2


        geneticComparisonsTPC+=4;//if comparissons
        if (crosspoint1==0){crosspoint1++; geneticAssignmentsTPC++; } //If the first point is 0 replace it for 1
        if (crosspoint2==parent1.length-1) {crosspoint2--; geneticAssignmentsTPC++;} //If the second point is the last index replace it for the one before
        if (crosspoint1==crosspoint2) {crosspoint2++; geneticAssignmentsTPC++;} //If the crosspoints are the same then increment crosspoint2

        geneticAssignmentsTPC++; //i
        memoryGeneticTPC+=2; //i memory bytes
        for (int i = 0; i < child1.length; i++) {
            geneticAssignmentsTPC++; //i
            geneticComparisonsTPC+=3;//for, if comparissons
            if (i < crosspoint1 || i > crosspoint2){ //if the index is outside the cross points put the parents values in their respective children
                child1[i] = parent1[i]; 
                child2[i] = parent2[i];
                geneticAssignmentsTPC+=2; //child[1] y child[2]
            }
            else{ //if the index is between the cross points, make the crossovers
                child1[i] = parent2[i];
                child2[i] = parent1[i];
                geneticAssignmentsTPC+=2; //child[1] y child[2]
            }
        }
        geneticComparisonsTPC++;//for false comparissons
        insertOrdered(arrayLpopulationTPC, child1); //insert child1 to the arrayLPopulationTPC
        insertOrdered(arrayLpopulationTPC, child2); //insert child1 to the arrayLPopulationTPC
        String x;
        x="\n==========================\n";
        x+="\nPadre 1: ["+arrayToString(parent1)+"]";
        x+="\tPadre 2: ["+arrayToString(parent2)+"]";
        x+="\nHijo 1: ["+arrayToString(child1)+"] Puntuacion: "+(arrayLpopulationTPC.indexOf(child1)+1);
        x+="\nHijo 2: ["+arrayToString(child2)+"] Puntuacion: "+(arrayLpopulationTPC.indexOf(child2)+1);
        records.addCrossoverTPC(x);
    }

    /**
     * 
     * @param arr
     * Clone the values of the arr into the global array solution
     * @return 
     */
    public int[] cloneArray(int arr[]){
        int[] arrClonned = new int[arr.length]; //clone of arr
        geneticAssignments+=3; //arr, arrClonned, i
        memoryGenetic+=arrClonned.length*4; //arr, arrClonned
        memoryGenetic+=2; //i memory bytes
        for(int i = 0; i<arr.length; i++){
            geneticComparisons++;//for comparissons
            arrClonned[i] = arr[i]; //Copy arr[i] value into arrClonned[i]
            geneticAssignments+=2;//arrClonned, i
        }
        geneticComparisons++;//for false comparissons
        geneticAssignments++; //return
        return arrClonned;
        
    }
    /**
     * 
     * @param x
     * @param arr
     * @return 
     */
    private int getPuntation(int x, int arr[]){
        int puntation;
        if (x==0){
            geneticAssignmentsUC+=2; //x y arr
            memoryGeneticUC+=4; //x, puntuation
            memoryGeneticUC+=arr.length*2; //arr
            geneticComparisonsUC++;//if comparissons
            insertOrdered(arrayLpopulationUC, arr); //Insert arr into arrayLpopulationUC to see it
            puntation = arrayLpopulationUC.indexOf(arr);
            arrayLpopulationUC.remove(arr); //Remove arr into arrayLpopulationUC to see it
            geneticAssignmentsUC+=3; //puntuation, remove, return
        }else{
            geneticAssignmentsTPC+=2; //x y arr
            memoryGeneticTPC+=4; //x, puntuation
            memoryGeneticTPC+=arr.length*2; //arr
            geneticComparisonsTPC++;//if comparissons
            insertOrdered(arrayLpopulationTPC, arr);  //Insert arr into arrayLpopulationTPC to see it
            puntation = arrayLpopulationTPC.indexOf(arr);
            arrayLpopulationTPC.remove(arr); //Remove arr into arrayLpopulationUC to see it
            geneticAssignmentsTPC+=3; //puntuation, remove, return
        }
        return puntation+1;
        
    }
     /**
      * 
      * @param x
      * @param populationTemp
      * @param j
      * @return 
      */
    private int checkDelete(int x, ArrayList<int[]> populationTemp, int j){
        int[] temp = cloneArray(populationTemp.get(j)); //Clone the array in populationTemp on index j into temp
        records.addToMutations("Individuo: " + arrayToString(temp) + 
                                ", puntuación: " + getPuntation(x, temp) + "\n"); //Record the proccess 
        temp[0]++; //mutation
        records.addToMutations("MutuaciÃ³n: " + arrayToString(temp) +  //Record the proccess 
                                ", puntuaciÃ³n: " + getPuntation(x, temp) + "\n\t======\n");
        
        int minWasteBeforeMutation = minimWaste(populationTemp.get(j)); //minimum waste of the array before mutuation
        int minWasteAfterMutation = minimWaste(temp); //minimum waste of the array after mutuation
        
        if (x==0){
            geneticAssignmentsUC+=7; //x, populationTemp, j, temp, temp, minWasteBeforeMutation, minWasteAfterMutation
            geneticComparisonsUC+=3;//ifs comparissons
            memoryGeneticUC+=8;//x, j, minWasteBeforeMutation, minWasteAfterMutation
            memoryGeneticUC+=temp.length*2;
            memoryGeneticUC+=populationTemp.size()*populationTemp.get(0).length*2;
            arrayLpopulationUC.remove(j); geneticAssignmentsUC++;
            }  //remove in arrayLpopulationUC array in index j
        else {
            geneticAssignmentsTPC+=7; //x, populationTemp, j, temp, temp, minWasteBeforeMutation, minWasteAfterMutation
            geneticComparisonsTPC+=3;//ifs comparissons
            memoryGeneticTPC+=8;//x, j, minWasteBeforeMutation, minWasteAfterMutation
            memoryGeneticTPC+=temp.length*2;
            memoryGeneticTPC+=populationTemp.size()*populationTemp.get(0).length*2;
            arrayLpopulationTPC.remove(j); geneticAssignmentsTPC++;
            } //remove in arrayLpopulationTPC array in index j

        if (minWasteAfterMutation>0 && minWasteBeforeMutation>minWasteAfterMutation){
            geneticComparisons++;//if comparissons
            if (x==0){
                geneticComparisonsUC+=2;//if comparissons
                insertOrdered(arrayLpopulationUC, temp);//insert in arrayLpopulationUC temp
                geneticAssignmentsUC++; //return
            } 
            else {
                geneticComparisonsTPC+=2;//if comparissons
                insertOrdered(arrayLpopulationTPC, temp); //insert in arrayLpopulationTPC temp
                geneticAssignmentsTPC++; //return
            }
            
            return -1;
        }
        if (x==0) geneticAssignmentsUC++; //return
        else geneticAssignmentsTPC++; //return
        return 0;
    }

    /**
     * 
     */
    private void mutationUC(){
        int flag; //flag that changes when two items of two diferent arrays are diferent
        memoryGeneticUC+=4; //flag, i
        geneticAssignmentsUC++; //i first assignment 
        for(int i = 0; i < arrayLpopulationUC.size()-1; i++){
            geneticComparisonsUC++;//for comparissons
            geneticAssignmentsUC+=2; //i, j first assignment 
            memoryGeneticUC+=2; //j memory bytes
            for(int j = i+1; j < arrayLpopulationUC.size(); j++){
                geneticComparisonsUC++;//for comparissons
                flag = 0; //flag starts at 0, assuming 
                geneticAssignmentsUC+=2; //j, k first assignments
                memoryGeneticUC+=2; //k memory bytes
                for(int k=0; k<arrayLpopulationUC.get(i).length; k++){
                    geneticComparisonsUC+=2;//if, for comparissons
                    geneticAssignmentsUC++; //k assignments
                    if (arrayLpopulationUC.get(i)[k] != arrayLpopulationUC.get(j)[k]) {flag = 1; geneticAssignmentsUC++;} //if it founds an element that is not equal the flag turns to one
                }
                geneticComparisonsUC+=2;//if, for false comparissons
                if (flag == 0){
                    j += checkDelete(0, arrayLpopulationUC, j);
                    i = 0;
                    geneticAssignmentsUC++; //i assignment
                    break;
                } //if an array is repeated, checks if it the mutation applies or if is deleated
            }
            geneticComparisonsUC++;//for false comparissons
        }
        geneticComparisonsUC++;//for false comparissons
        
    }

    /**
     * 
     */
    private void mutationTPC(){
        int flag; //flag that changes when two items of two diferent arrays are diferent
        memoryGeneticTPC+=4; //flag, i
        geneticAssignmentsTPC++; //i first assignment 
        for(int i = 0; i < arrayLpopulationTPC.size()-1; i++){
            geneticComparisonsTPC++;//for comparissons
            geneticAssignmentsTPC+=2; //i, j first assignment 
            memoryGeneticTPC+=2; //j memory bytes
            for(int j = i+1; j < arrayLpopulationTPC.size(); j++){
                geneticComparisonsTPC++;//for comparissons
                flag = 0; //flag starts at 0, assuming 
                geneticAssignmentsTPC+=2; //j, k first assignments
                memoryGeneticTPC+=2; //k memory bytes
                for(int k=0; k<arrayLpopulationTPC.get(i).length; k++){
                    geneticComparisonsTPC+=2;//if, for comparissons
                    geneticAssignmentsTPC++; //k assignments
                    if (arrayLpopulationTPC.get(i)[k] != arrayLpopulationTPC.get(j)[k]) {flag = 1; geneticAssignmentsTPC++;} //if it founds an element that is not equal the flag turns to one
                }
                geneticComparisonsTPC+=2;//if, for false comparissons
                if (flag == 0){
                    j += checkDelete(1, arrayLpopulationTPC, j);
                    i = 0;
                    geneticAssignmentsTPC++; //i assignment
                    break;
                } //if an array is repeated, checks if it the mutation applies or if is deleated
            }
            geneticComparisonsTPC++;//for false comparissons
        }
        geneticComparisonsTPC++;//for false comparissons
        
    }

    /**
     * 
     * @param numCrossover
     * @param numChild
     * function that does the crossovers
     */
    private void doCrossoversUC(int numCrossover, int numChild){
        int tmp;                //tmp used for saving the numChild
        geneticAssignmentsUC+=3; //numCrossover,numChild, i
        memoryGeneticUC+=8; //numCrossover, numChild, tmp, i memory bytes
        //for that does the quantity of crossovers needeed
        for (int i = 0; i < numCrossover; i++) {
            geneticComparisonsUC++;//for comparissons
            geneticAssignmentsUC+=3; //tmp, i, k
            memoryGeneticUC+=2; //k memory bytes
            tmp = numChild;   //give the tmp the value of numChild
            forMid:           //the next two fors do all the possible pairs of arrays 
            for(int k = 0; k<populationUC.length-1; k++){
                geneticComparisonsUC++;//for comparissons
                geneticAssignmentsUC+=2; //k,j assignments
                memoryGeneticUC+=2; //j memory bytes
                for(int j = k+1; j<populationUC.length; j++){
                    geneticComparisonsUC+=2;//for comparissons and if
                    geneticAssignmentsUC+=2;//j, tmp assignment
                    uniformCrossover(populationUC[k], populationUC[j]); //does the uniformCrossover
                    mutationUC();//does the required mutations of the uniform crossover
                    aptitudeFunctionUC();//does the aptitude fuction
                    tmp-=2;//substract the amount of childs created
                    if(tmp<=0) break forMid; //if all the childs that need to be done are ready, it stops
                }
                geneticComparisonsUC++;//for false comparissons
            }   
            geneticComparisonsUC++;//for false comparissons     
        }
        geneticComparisonsUC++;//for false comparissons
    }

    /**
     * 
     * @param numCrossover
     * @param numChild
     * function that does the crossovers
     */
    private void doCrossoversTPC(int numCrossover, int numChild){
        int tmp;                //tmp used for saving the numChild
        geneticAssignmentsTPC+=3; //numCrossover,numChild, i
        memoryGeneticTPC+=8; //numCrossover, numChild, tmp, i memory bytes
        
        //for that does the quantity of crossovers needeed
        for (int i = 0; i < numCrossover; i++) {
            geneticComparisonsTPC++;//for comparissons
            geneticAssignmentsTPC+=3; //tmp, i, k
            memoryGeneticTPC+=2; //k memory bytes
            tmp = numChild;   //give the tmp the value of numChild
            forMid:           //the next two fors do all the possible pairs of arrays 
            for(int k = 0; k<populationTPC.length-1; k++){
                geneticComparisonsTPC++;//for comparissons
                geneticAssignmentsTPC+=2; //k,j assignments
                memoryGeneticTPC+=2; //j memory bytes
                for(int j = k+1; j<populationTPC.length; j++){
                    geneticComparisonsTPC+=2;//for comparissons and if
                    geneticAssignmentsTPC+=2;//j, tmp assignment
                    twoPointsCrossover(populationTPC[k], populationTPC[j]);//does the twoPointsCrossover
                    mutationTPC();//does the required mutations of the twoPoints crossover
                    aptitudeFunctionTPC();//does the aptitude fuction
                    tmp-=2;//substract the amount of childs created
                    if(tmp<=0) break forMid; //if all the childs that need to be done are ready, it stops
                }
                geneticComparisonsTPC++;//for false comparissons
            }   
            geneticComparisonsTPC++;//for false comparissons     
        }
        geneticComparisonsTPC++;//for false comparissons
    }
    /**
     * 
     * @param array
     * @return 
     * function that converts arrays into string, used for records
     */
    public String arrayToString(int [] array){
        String x = ""+array[0];
        for(int i = 1; i<array.length; i++){
            x+=", "+array[i];
        }
        return x;
    }

    /**
     * 
     * @param array1
     * @param array2
     * @return 5 best combinations
     */
    private int[] getBest(int [] array1,int [] array2){
        int waste1 = minimWaste(array1); //get the lowest waste of each array
        int waste2 = minimWaste(array2);
        
        //get the waste that is closest to n
        if (waste1<0 || waste2<0){
            if (waste2 > waste1) {return array2;} 
        }else{
            if (waste2 < waste1) {return array2;} 
        }
        return array1; //return the array that has that waste
    }
    
    /**
     * 
     */
    public void searchMinWaste(){
        long time_start, time_end;
        long timeGeneral; // time for both crossovers
        //Genetic Proccess
        time_start = System.nanoTime(); 
        generatePopulation();
        generateMask();
        time_end = System.nanoTime();
        timeGeneral = ( time_end - time_start );
        int crossoverNum = defCrossoverNum(arrayPresentations.length);
        int childNum = defChildNum(arrayPresentations.length);
        memoryGenetic+=4; //crossoverNum, childNum

        long time; // time for each crossover individually
        time_start = System.nanoTime(); 
        doCrossoversUC(crossoverNum, childNum);
        time_end = System.nanoTime();
        time = ( time_end - time_start );
        records.setGeneticTimeUC((timeGeneral + time));

        time_start = System.nanoTime(); 
        doCrossoversTPC(crossoverNum, childNum);
        time_end = System.nanoTime();
        time = ( time_end - time_start );
        records.setGeneticTimeTPC((timeGeneral + time));

        //Records
        String xTPC="";
        String xUC="";
        //sets the best combination and the waste of each code
        records.addToWasteAndCombination("\nLa mejor combinaciÃ³n del cÃ³digo genÃ©tico es: " + 
                arrayToString(getBest(arrayLpopulationTPC.get(0),arrayLpopulationUC.get(0))) + 
                ", con un desperdicio de: " + minimWaste(getBest(arrayLpopulationTPC.get(0),arrayLpopulationUC.get(0))) + "\n");
        
        //for that sets the evaluation for the 5 bests in each array
        for(int i = 0; i<5; i++){
            xTPC += "\n"+arrayToString(arrayLpopulationTPC.get(i))+" Evaluacion "+(i+1)+" minWaste "+minimWaste(arrayLpopulationTPC.get(i)); //set xTPC
            xUC += "\n "+arrayToString(arrayLpopulationUC.get(i))+" Evaluacion "+(i+1)+" minWaste "+minimWaste(arrayLpopulationUC.get(i));  //set xUC
        }
        records.addBest5TPC(xTPC);                        //set variable best5Tpc in records
        records.addBest5UC(xUC);                          //set variable best5UC in records
        records.setGeneticAssignments(geneticAssignments);//set variable geneticAssigments in records
        records.setGeneticComparisons(geneticComparisons);//set variable geneticComparisson in records
        records.setGeneticMemory(memoryGenetic);          //set variable geneticMemory in records
        records.setGeneticAssignmentsUC(geneticAssignmentsUC);//set variable geneticAssigments in records
        records.setGeneticComparisonsUC(geneticComparisonsUC);//set variable geneticComparisson in records
        records.setGeneticMemoryUC(memoryGeneticUC);          //set variable geneticMemory in records
        records.setGeneticAssignmentsTPC(geneticAssignmentsTPC);//set variable geneticAssigments in records
        records.setGeneticComparisonsTPC(geneticComparisonsTPC);//set variable geneticComparisson in records
        records.setGeneticMemoryTPC(memoryGeneticTPC);          //set variable geneticMemory in records
        
    } 

    
}
