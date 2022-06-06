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
            geneticAssignments+=2; //restmp, j
            memoryGenetic+=2; //i memory bytes
            for(int j = 0; j < arrayPresentations.length; j++){
                geneticComparisons++;//for comparissons
                numTMP = r.nextInt((n/arrayPresentations[j])+1); //quantity of the genes
                populationUC[i][j] = numTMP;
                populationTPC[i][j] = numTMP;
                restmp += numTMP*arrayPresentations[j];
                geneticAssignments+=5;
            }
            geneticComparisons++;//for false comparisson
            geneticComparisons++;//if comparisson
            if (restmp == n) { i--; geneticAssignments++;}
            else{
                insertOrdered(arrayLpopulationUC, populationTPC[i]);
                insertOrdered(arrayLpopulationTPC, populationTPC[i]);}
        }
        geneticComparisons ++; //for false comparisson 
    }
    
    private void generateMask(){
        mask = new int[arrayPresentations.length];
        memoryGenetic+=mask.length*2;
        memoryGenetic+=2; //i memory bytes
        geneticAssignments+=2;
        for(int i = 0; i < arrayPresentations.length; i++){
            geneticComparisons+=2;//for comparissons
            geneticAssignments++;
            
            if(((int)i/2)*2==i){
                mask[i]=1;
                geneticAssignments++;
            }
            
        }
        geneticComparisons++;//for false comparissons
    }

    public int minimWaste(int[]array){
        int min = n;
        memoryGenetic+=4;
        memoryGenetic+=array.length*2;
        geneticAssignments+=3;
        for(int i = 0; i < arrayPresentations.length; i++){
            geneticComparisons++;//for comparissons
            min -= arrayPresentations[i]*array[i];
            geneticAssignments+=2;
        }
        geneticComparisons++;//for false comparissons
        geneticAssignments++;
        return -(min);
    }
    
    private void aptitudeFunction(){
        geneticAssignments++;
        memoryGenetic+=2; //i memory bytes
        for(int i = 0; i < populationUC.length; i++){
            geneticComparisons++;//for comparissons
            populationUC[i] = arrayLpopulationUC.get(i);
            populationTPC[i] = arrayLpopulationTPC.get(i);
            geneticAssignments+=3;
        }
        geneticComparisons++;//for false comparissons
    }

    private void insertOrdered(ArrayList<int[]> arrayL, int[] array ){
        int [] tmp1;
        int [] tmp2 = array;
        boolean a = false;
        memoryGenetic+=2.125;
        memoryGenetic+=arrayL.size()*2;
        geneticAssignments+=6;
        for(int i = 0; i < arrayL.size(); i++){
            geneticAssignments++;
            geneticComparisons+=3;//for, if, else comparissons
            if (a){
                tmp1 = arrayL.get(i);
                arrayL.set(i, tmp2);
                tmp2 = tmp1;
                geneticAssignments+=3;
                memoryGenetic+=tmp1.length*2;
                memoryGenetic+=tmp2.length*2;
            }//2 < 3 || -2 > -3 || 2 > -3
            else if ((minimWaste(array)>0 && minimWaste(arrayL.get(i))>0 && minimWaste(arrayL.get(i))>minimWaste(array)) ||
                    (minimWaste(arrayL.get(i))<0 && minimWaste(arrayL.get(i))<minimWaste(array))){
                tmp2 = arrayL.get(i);              
                arrayL.set(i, array);
                a = true;
                geneticAssignments+=3;
            }
        }
        geneticComparisons++;//for false comparissons
        arrayL.add(tmp2);
        geneticAssignments++;
        
    }

    private void uniformCrossover(int[] parent1, int[] parent2){
        int children[][] = new int [2][parent1.length];
        memoryGenetic+=parent1.length*8;
        geneticAssignments+=4;
        memoryGenetic+=2; //i memory bytes
        for (int i = 0; i < parent1.length; i++) {
            geneticAssignments++;
            geneticComparisons+=2;//for, if comparissons
            if (mask[i]==0){
                children[0][i] = parent1[i];
                children[1][i] = parent2[i];
                geneticAssignments+=2;
            }
            else{
                children[0][i] = parent2[i];
                children[1][i] = parent1[i];
                geneticAssignments+=2;
            }
        }
        geneticComparisons++;//for false comparissons
        
        insertOrdered(arrayLpopulationUC, children[0]);
        insertOrdered(arrayLpopulationUC, children[1]);
        String x;
        x="\n==========================\n";
        x+="\nPadre 1: ["+arrayToString(parent1)+"]";
        x+="\tPadre 2: ["+arrayToString(parent2)+"]";
        x+="\nHijo 1: ["+arrayToString(children[0])+"] Puntuacion: "+(arrayLpopulationUC.indexOf(children[0])+1);
        x+="\nHijo 2: ["+arrayToString(children[1])+"] Puntuacion: "+(arrayLpopulationUC.indexOf(children[1])+1);
        records.addCrossoverUC(x);
    }

    private void twoPointsCrossover(int[] parent1, int[] parent2){
        int crosspoint1 = r.nextInt((parent1.length/2)+1);
        int crosspoint2 = (parent1.length/2) + r.nextInt((parent1.length/2)+1);
        int[] child1 = new int[parent1.length];
        int[] child2 = new int[parent1.length];
        geneticAssignments+=6;
        memoryGenetic+=4;
        memoryGenetic+=parent1.length*8;


        geneticComparisons+=4;//if comparissons
        if (crosspoint1==0){crosspoint1++; geneticAssignments++; } 
        if (crosspoint2==parent1.length-1) {crosspoint2--; geneticAssignments++;}
        if (crosspoint1==parent1.length/2 && crosspoint2==0) {crosspoint2--; geneticAssignments++;}

        geneticAssignments++;
        memoryGenetic+=2; //i memory bytes
        for (int i = 0; i < child1.length; i++) {
            geneticAssignments++;
            geneticComparisons+=3;//for, if comparissons
            if (i < crosspoint1 || i > crosspoint2){
                child1[i] = parent1[i];
                child2[i] = parent2[i];
                geneticAssignments+=2;
            }
            else{
                child1[i] = parent2[i];
                child2[i] = parent1[i];
                geneticAssignments+=2;
            }
        }
        geneticComparisons++;//for false comparissons
        insertOrdered(arrayLpopulationTPC, child1);
        insertOrdered(arrayLpopulationTPC, child2);
        String x;
        x="\n==========================\n";
        x+="\nPadre 1: ["+arrayToString(parent1)+"]";
        x+="\tPadre 2: ["+arrayToString(parent2)+"]";
        x+="\nHijo 1: ["+arrayToString(child1)+"] Puntuacion: "+(arrayLpopulationTPC.indexOf(child1)+1);
        x+="\nHijo 2: ["+arrayToString(child2)+"] Puntuacion: "+(arrayLpopulationTPC.indexOf(child2)+1);
        records.addCrossoverTPC(x);
    }

    private boolean mutationNedeed(ArrayList<int[]> populationTemp){
        int flag;
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
                                ", puntuación: " + getPuntation(x, temp) + "\n");
        temp[0]++; //mutation
        records.addToMutations("Mutuación: " + arrayToString(temp) + 
                                ", puntuación: " + getPuntation(x, temp) + "\n\t======\n");
        
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
            while(mutationNedeed(populationTemp)){
                geneticComparisons++;//while comparissons
                geneticAssignments++;
                memoryGenetic+=2; //i memory bytes
                for(int i = 0; i < populationTemp.size()-1; i++){
                    geneticComparisons++;//for comparissons
                    geneticAssignments+=2;
                    memoryGenetic+=2; //i memory bytes
                    for(int j = i+1; j < populationTemp.size(); j++){
                        geneticComparisons++;//for comparissons
                        flag = 0;
                        geneticAssignments+=2;
                        memoryGenetic+=2; //i memory bytes
                        for(int k=0; k<populationTemp.get(i).length; k++){
                            geneticComparisons+=2;//if, for comparissons
                            geneticAssignments++;
                            if (populationTemp.get(i)[k] != populationTemp.get(j)[k]) {flag = 1; geneticAssignments++;}
                        }
                        geneticComparisons+=2;//if, for false comparissons
                        if (flag == 0) j += checkDelete(x, populationTemp, j); 
                    }
                    geneticComparisons++;//for false comparissons
                }
                geneticComparisons++;//for false comparissons
            }
            geneticComparisons++;//while false comparissons
        }
        geneticComparisons++;//for false comparissons
    }

    private void doCrossovers(int numCrossover, int numChild){
        int tmp;
        geneticAssignments+=2; //num, i
        memoryGenetic+=8; //numCrossover, numChild, tmp, i memory bytes
        
        for (int i = 0; i < numCrossover; i++) {
            geneticAssignments+=1; //num, k
            memoryGenetic+=2; //num, k memory bytes
            tmp = numChild;
            forMid:
            for(int k = 0; k<populationUC.length-1; k++){
                geneticComparisons+=2;//for comparissons
                geneticAssignments++;
                memoryGenetic+=2; //i memory bytes
                for(int j = k+1; j<populationUC.length; j++){
                    geneticComparisons+=2;//for comparissons and if
                    geneticAssignments+=2;
                    uniformCrossover(populationUC[k], populationUC[j]);
                    twoPointsCrossover(populationTPC[k], populationTPC[j]);
                    mutation();
                    aptitudeFunction();
                    tmp-=2;
                    if(tmp<=0) break forMid;
                }
                geneticComparisons++;//for false comparissons
            }   
            geneticComparisons++;//for false comparissons     
        }
        geneticComparisons++;//for false comparissons   
    }
    public String arrayToString(int [] array){
        String x = ""+array[0];
        geneticAssignments+=3;
        memoryGenetic+=4;
        memoryGenetic+=array.length*2;
        for(int i = 1; i<array.length; i++){
            geneticComparisons++;//for comparissons
            geneticAssignments+=2;
            x+=", "+array[i];
        }
        geneticComparisons++;//for false comparissons
        geneticAssignments++;
        return x;
    }

    private int[] getBest(int [] array1,int [] array2){
        int waste1 = minimWaste(array1);
        int waste2 = minimWaste(array2);
        geneticAssignments+=4;
        geneticComparisons+=2;//if comparissons
        memoryGenetic+=4;
        memoryGenetic+=array1.length*4;
        
        if (waste1<0 || waste2<0){
            geneticComparisons++;//if comparissons
            if (waste2 > waste1) {geneticAssignments++; return array2;}
        }else{
            geneticComparisons++;//if comparissons
            if (waste2 < waste1) {geneticAssignments++; return array2;} 
        }
        geneticAssignments++;
        return array1;
    }

    public void searchMinWaste(){
        String xTPC="";
        String xUC="";
        
        generatePopulation();
        generateMask();
        doCrossovers(defCrossoverNum(arrayPresentations.length), defChildNum(arrayPresentations.length));
        records.addToWasteAndCombination("\nLa mejor combinación del código genético es: " + 
                arrayToString(getBest(arrayLpopulationTPC.get(0),arrayLpopulationUC.get(0))) + 
                ", con un desperdicio de: " + minimWaste(getBest(arrayLpopulationTPC.get(0),arrayLpopulationUC.get(0))) + "\n");
               
        for(int i = 0; i<5; i++){
            xTPC += "\n"+arrayToString(arrayLpopulationTPC.get(i))+" Evaluacion "+(i+1);
            xUC += "\n "+arrayToString(arrayLpopulationUC.get(i))+" Evaluacion "+(i+1);
           
        }
        records.addBest5TPC(xTPC);
        records.addBest5UC(xUC);
        records.setGeneticAssignments(geneticAssignments);
        records.setGeneticComparisons(geneticComparisons);
        records.setGeneticMemory(memoryGenetic);
        
    } 

    
}
