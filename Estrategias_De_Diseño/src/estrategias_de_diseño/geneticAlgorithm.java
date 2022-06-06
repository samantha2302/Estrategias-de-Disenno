package estrategias_de_diseño;

import java.util.Random;
import java.util.ArrayList;

public class geneticAlgorithm {

    private int geneticComparisons = 0; // Variable Assignment
    private int geneticAssignments = 3; // Variable Comparison
    private float memoryGenetic = 0; // Variable Memory

    private Random r = new Random();
    
    private int populationUC[][]; //Array of population of the uniform crossing
    private int populationTPC[][]; //Array of population of the crossing of two points
    private ArrayList<int[]> arrayLpopulationUC = new ArrayList<int[]>(); // ArrayList of population of the uniform crossing
    private ArrayList<int[]> arrayLpopulationTPC = new ArrayList<int[]>(); //ArrayList of population of the crossing of two points
    private int mask[]; //Uniform Crossing Mask Array
    private int arrayPresentations[]; //Presentations Array
    private int n; //number that we are trying to reach
    private Records records; //Records where we store the info

    public geneticAlgorithm(Records records, int [] array, int num){
        this.records = records;
        arrayPresentations = array;
        n = num;
        geneticAssignments+=4;
        memoryGenetic+=array.length*4;
    }


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
    
    private void generateMask(){
        mask = new int[arrayPresentations.length]; //mark to make the crossover
        memoryGenetic+=mask.length*2; //mask
        memoryGenetic+=2; //i memory bytes
        geneticAssignments+=2; //mask, i
        for(int i = 0; i < arrayPresentations.length; i++){
            geneticComparisons+=2;//for and if comparissons
            geneticAssignments++; //i
            
            if(((int)i/2)*2==i){
                mask[i]=1;
                geneticAssignments++; // mask
            }
            
        }
        geneticComparisons++;//for false comparissons
    }

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
    
    private void aptitudeFunction(){
        geneticAssignments++;//i
        memoryGenetic+=2; //i memory bytes
        //Get the first people in the arraylist to make the population
        for(int i = 0; i < populationUC.length; i++){
            geneticComparisons++;//for comparissons
            populationUC[i] = arrayLpopulationUC.get(i); 
            populationTPC[i] = arrayLpopulationTPC.get(i);
            geneticAssignments+=3;//populationUC, populationTPC, i
        }
        geneticComparisons++;//for false comparissons
    }

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

    private void uniformCrossover(int[] parent1, int[] parent2){
        int children[][] = new int [2][parent1.length]; //child1 and child2
        memoryGenetic+=parent1.length*8; //parent1, parent2, children[0] and children[1]
        geneticAssignments+=4; //parent1, parent2, children, i
        memoryGenetic+=2; //i memory bytes
        for (int i = 0; i < parent1.length; i++) {
            geneticAssignments++;//i
            geneticComparisons+=2;//for, if comparissons
            if (mask[i]==0){ //Add from parent1 to children[0] and parent2 to children[1]
                children[0][i] = parent1[i];
                children[1][i] = parent2[i];
                geneticAssignments+=2;//children[0], children[1]
            }
            else{//Add from parent1 to children[1] and parent2 to children[0]
                children[0][i] = parent2[i];
                children[1][i] = parent1[i];
                geneticAssignments+=2;//children[0], children[1]
            }
        }
        geneticComparisons++;//for false comparissons
        
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

    private void twoPointsCrossover(int[] parent1, int[] parent2){
        int crosspoint1 = r.nextInt((parent1.length/2)+1); //Decides the First point to cross
        int crosspoint2 = (parent1.length/2) + r.nextInt((parent1.length/2)+1); //Decides the second point to cross
        int[] child1 = new int[parent1.length]; //child 0
        int[] child2 = new int[parent1.length]; //child 1
        geneticAssignments+=6; //parent1, parent2, crosspoint1, corsspoint2, child1, child2
        memoryGenetic+=4;//crosspoint1, crosspoint2
        memoryGenetic+=parent1.length*8; //parent1, parent2, child1, child2


        geneticComparisons+=4;//if comparissons
        if (crosspoint1==0){crosspoint1++; geneticAssignments++; } //If the first point is 0 replace it for 1
        if (crosspoint2==parent1.length-1) {crosspoint2--; geneticAssignments++;} //If the second point is the last index replace it for the one before
        if (crosspoint1==crosspoint2) {crosspoint2++; geneticAssignments++;} //If the crosspoints are the same then increment crosspoint2

        geneticAssignments++; //i
        memoryGenetic+=2; //i memory bytes
        for (int i = 0; i < child1.length; i++) {
            geneticAssignments++; //i
            geneticComparisons+=3;//for, if comparissons
            if (i < crosspoint1 || i > crosspoint2){ //if the index is outside the cross points put the parents values in their respective children
                child1[i] = parent1[i]; 
                child2[i] = parent2[i];
                geneticAssignments+=2; //child[1] y child[2]
            }
            else{ //if the index is between the cross points, make the crossovers
                child1[i] = parent2[i];
                child2[i] = parent1[i];
                geneticAssignments+=2; //child[1] y child[2]
            }
        }
        geneticComparisons++;//for false comparissons
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

    private boolean mutationNedeed(ArrayList<int[]> populationTemp){
        int flag; //To see if a 
        geneticAssignments+=2;
        memoryGenetic+=4;
        memoryGenetic+=populationTemp.size()*populationTemp.get(0).length*2;
        for(int i = 0; i < populationTemp.size()-1; i++){
            geneticAssignments+=2;
            geneticComparisons++;//for comparissons
            memoryGenetic+=2; //i memory bytes
            for(int j = i+1; j < populationTemp.size(); j++){
                geneticComparisons++;//for comparissons
                geneticAssignments+=3;
                flag = 0;
                memoryGenetic+=2; //i memory bytes
                for(int k=0; k<populationTemp.get(i).length; k++){
                    geneticComparisons+=2;//for, if comparissons
                    geneticAssignments++;
                    if (populationTemp.get(i)[k] != populationTemp.get(j)[k]) {flag = 1; geneticAssignments++;}
                }
                geneticComparisons+=2;//if and for false comparissons
                if (flag == 0) {geneticAssignments++; return true; }
            }
            geneticComparisons++;//for false comparissons
        }
        geneticComparisons++;//for false comparissons
        geneticAssignments++;
        return false;
        
    }

    //Clone the values of the arr into the global array solution
    public int[] cloneArray(int arr[]){
        int[] solution = new int[arr.length];
        geneticAssignments+=3;
        memoryGenetic+=solution.length*4;
        memoryGenetic+=2; //i memory bytes
        for(int i = 0; i<arr.length; i++){
            geneticComparisons++;//for comparissons
            solution[i] = arr[i];
            geneticAssignments+=2;
        }
        geneticComparisons++;//for false comparissons
        geneticAssignments++;
        return solution;
        
    }

    private int getPuntation(int x, int arr[]){
        int puntation;
        geneticAssignments+=2;
        memoryGenetic+=4;
        memoryGenetic+=arr.length*2;
        geneticComparisons++;//if comparissons
        if (x==0){
            insertOrdered(arrayLpopulationUC, arr);
            puntation = arrayLpopulationUC.indexOf(arr);
            arrayLpopulationUC.remove(arr);
            geneticAssignments+=2;
        }else{
            insertOrdered(arrayLpopulationTPC, arr);
            puntation = arrayLpopulationTPC.indexOf(arr);
            arrayLpopulationTPC.remove(arr);
            geneticAssignments+=2;
        }
        geneticAssignments++;
        return puntation+1;
        
    }

    private int checkDelete(int x, ArrayList<int[]> populationTemp, int j){
        int[] temp = cloneArray(populationTemp.get(j));
        records.addToMutations("Individuo: " + arrayToString(temp) + 
                                ", puntuaciÃ³n: " + getPuntation(x, temp) + "\n");
        temp[0]++; //mutation
        records.addToMutations("MutuaciÃ³n: " + arrayToString(temp) + 
                                ", puntuaciÃ³n: " + getPuntation(x, temp) + "\n\t======\n");
        
        int minWasteBeforeMutation = minimWaste(populationTemp.get(j));
        int minWasteAfterMutation = minimWaste(temp);
        geneticAssignments+=7;
        geneticComparisons+=3;//ifs comparissons
        memoryGenetic+=8;
        memoryGenetic+=temp.length*2;
        memoryGenetic+=populationTemp.size()*populationTemp.get(0).length*2;
        
        if (x==0){ arrayLpopulationUC.remove(j); geneticAssignments++;} 
        else {arrayLpopulationTPC.remove(j); geneticAssignments++;}

        if (minWasteAfterMutation>0 && minWasteBeforeMutation>minWasteAfterMutation){
            geneticComparisons++;//if comparissons
            if (x==0) insertOrdered(arrayLpopulationUC, temp);
            else insertOrdered(arrayLpopulationTPC, temp);
            geneticAssignments++; //return
            return -1;
        }
        geneticAssignments++; //return
        return 0;
    }

    private void mutation(){
        int flag;
        ArrayList<int[]> populationTemp = arrayLpopulationUC;
        geneticAssignments+=2;
        memoryGenetic+=4;
        memoryGenetic+=populationTemp.size()*populationTemp.get(0).length;
        for(int x = 0; x < 2; x++){
            geneticComparisons+=2;//if, for comparissons
            if (x==1) populationTemp = arrayLpopulationTPC;
            geneticAssignments+=2;
            geneticComparisons++;//while comparissons
            geneticAssignments++;//i first assignment
            memoryGenetic+=2; //i memory bytes
            for(int i = 0; i < populationTemp.size()-1; i++){
                geneticComparisons++;//for comparissons
                geneticAssignments+=2; //i, j first assignment 
                memoryGenetic+=2; //i memory bytes
                for(int j = i+1; j < populationTemp.size(); j++){
                    geneticComparisons++;//for comparissons
                    flag = 0; //flag starts at 0, assuming 
                    geneticAssignments+=2; //j, k first assignments
                    memoryGenetic+=2; //i memory bytes
                    for(int k=0; k<populationTemp.get(i).length; k++){
                        geneticComparisons+=2;//if, for comparissons
                        geneticAssignments++; //k assignments
                        if (populationTemp.get(i)[k] != populationTemp.get(j)[k]) {flag = 1; geneticAssignments++;} //if it founds an element that is not equal the flag turns to one
                    }
                    geneticComparisons+=2;//if, for false comparissons
                    if (flag == 0){
                        j += checkDelete(x, populationTemp, j);
                        i = 0;
                        break;
                    } //if an array is repeated, checks if it the mutation applies or if is deleated
                }
                geneticComparisons++;//for false comparissons
            }
            geneticComparisons++;//for false comparissons
        }
        geneticComparisons++;//for false comparissons
    }

    //function that does the crossovers
    private void doCrossovers(int numCrossover, int numChild){
        int tmp;                //tmp used for saving the numChild
        geneticAssignments+=3; //numCrossover,numChild, i
        memoryGenetic+=8; //numCrossover, numChild, tmp, i memory bytes
        
        //for that does the quantity of crossovers needeed
        for (int i = 0; i < numCrossover; i++) {
            geneticComparisons++;//for comparissons
            geneticAssignments+=3; //tmp, i, k
            memoryGenetic+=2; //k memory bytes
            tmp = numChild;   //give the tmp the value of numChild
            forMid:           //the next two fors do all the possible pairs of arrays 
            for(int k = 0; k<populationUC.length-1; k++){
                geneticComparisons++;//for comparissons
                geneticAssignments+=2; //k,j assignments
                memoryGenetic+=2; //j memory bytes
                for(int j = k+1; j<populationUC.length; j++){
                    geneticComparisons+=2;//for comparissons and if
                    geneticAssignments+=2;//j, tmp assignment
                    uniformCrossover(populationUC[k], populationUC[j]); //does the uniformCrossover
                    twoPointsCrossover(populationTPC[k], populationTPC[j]);//does the twoPointsCrossover
                    mutation();//does the required mutations
                    aptitudeFunction();//does the aptitude fuction
                    tmp-=2;//substract the amount of childs created
                    if(tmp<=0) break forMid; //if all the childs that need to be done are ready, it stops
                }
                geneticComparisons++;//for false comparissons
            }   
            geneticComparisons++;//for false comparissons     
        }
        geneticComparisons++;//for false comparissons   
    }

    //function that converts arrays into string, used for records
    public String arrayToString(int [] array){
        String x = ""+array[0];
        for(int i = 1; i<array.length; i++){
            x+=", "+array[i];
        }
        return x;
    }

    //return the 5 best combinations
    private int[] getBest(int [] array1,int [] array2){
        int waste1 = minimWaste(array1); //get the lowest waste of each array
        int waste2 = minimWaste(array2);
        geneticAssignments+=4;           //array1, array2, waste1, waste2 assignments
        geneticComparisons+=2;           //if comparissons
        memoryGenetic+=4;                //waste1, waste2 memory
        memoryGenetic+=array1.length*4;  //array1, array2 memory
        
        //get the waste that is closest to n
        if (waste1<0 || waste2<0){
            if (waste2 > waste1) {geneticAssignments++; return array2;} 
        }else{
            if (waste2 < waste1) {geneticAssignments++; return array2;} 
        }
        geneticAssignments++;
        return array1; //return the array that has that waste
    }

    public void searchMinWaste(){
        
        //Genetic Proccess
        generatePopulation();
        generateMask();
        doCrossovers(defCrossoverNum(arrayPresentations.length), defChildNum(arrayPresentations.length));

        //Records
        String xTPC="";
        String xUC="";
        //sets the best combination and the waste of each code
        records.addToWasteAndCombination("\nLa mejor combinaciÃ³n del cÃ³digo genÃ©tico es: " + 
                arrayToString(getBest(arrayLpopulationTPC.get(0),arrayLpopulationUC.get(0))) + 
                ", con un desperdicio de: " + minimWaste(getBest(arrayLpopulationTPC.get(0),arrayLpopulationUC.get(0))) + "\n");
        
        //for that sets the evaluation for the 5 bests in each array
        for(int i = 0; i<5; i++){
            xTPC += "\n"+arrayToString(arrayLpopulationTPC.get(i))+" Evaluacion "+(i+1); //set xTPC
            xUC += "\n "+arrayToString(arrayLpopulationUC.get(i))+" Evaluacion "+(i+1);  //set xUC
        }
        records.addBest5TPC(xTPC);                        //set variable best5Tpc in records
        records.addBest5UC(xUC);                          //set variable best5UC in records
        records.setGeneticAssignments(geneticAssignments);//set variable geneticAssigments in records
        records.setGeneticComparisons(geneticComparisons);//set variable geneticComparisson in records
        records.setGeneticMemory(memoryGenetic);          //set variable geneticMemory in records
        
    } 

    
}
