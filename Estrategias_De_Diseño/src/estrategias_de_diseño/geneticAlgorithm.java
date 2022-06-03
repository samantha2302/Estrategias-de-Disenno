package estrategias_de_diseño;

import java.util.Random;
import java.util.ArrayList;

public class geneticAlgorithm {

    static int geneticComparisons = 0; // Variable Assignment
    static int geneticAssignments = 3; // Variable Comparison
    static float memoryGenetic = 0; // Variable Memory

    static Random r = new Random();
    static int [][] burned = { //
        {2,5,9}, //3
        {7,9,8,6,5}, //5
        {3,8,13,19,29,31}, //6
        {2,7,9,11,15,17,23,29,37} //9  
    }; //TODO: QUITAR ESTO!!!

    static int populationUC[][]; //Array of population of the uniform crossing
    static int populationTPC[][]; //Array of population of the crossing of two points
    static ArrayList<int[]> arrayLpopulationUC = new ArrayList<int[]>(); // ArrayList of population of the uniform crossing
    static ArrayList<int[]> arrayLpopulationTPC = new ArrayList<int[]>(); //ArrayList of population of the crossing of two points
    static int mask[]; //Uniform Crossing Mask Array
    static int arrayPresentations[]; //Presentations Array
    static int n; //
    static Records records;


    private static void defPopSize(int size){
        geneticAssignments+=2;
        memoryGenetic+=2;
        int populationSize;
        geneticComparisons += 3; //switch comparisons
        switch (size) {
            case 3 -> {populationSize = 3; break;}
            case 5 -> {populationSize = 5; break;}
            case 6 -> {populationSize = 10; break;}
            default -> populationSize = 20;
        }
        populationUC = new int[populationSize][size];
        populationTPC = new int[populationSize][size];
        memoryGenetic+=populationUC.length*populationUC[0].length*2; 
        memoryGenetic+=populationTPC.length*populationTPC[0].length*2; 
        geneticAssignments+=2;
    }

    public static void generatePopulation(){
        defPopSize(arrayPresentations.length);
        int restmp;
        int numTMP;
        geneticAssignments++;
        memoryGenetic+=6;
        for(int i = 0; i < populationUC.length; i++){
            geneticComparisons++; //for comparissons
            restmp = 0;
            geneticAssignments+=3;
            memoryGenetic+=2; //i memory bytes
            for(int j = 0; j < arrayPresentations.length; j++){
                geneticComparisons++;//for comparissons
                numTMP = r.nextInt((n/arrayPresentations[j])+1);
                populationUC[i][j] = numTMP;
                populationTPC[i][j] = numTMP;
                restmp += numTMP*arrayPresentations[j];
                geneticAssignments+=5;
            }
            geneticComparisons++;//for false comparisson
            geneticComparisons++;//if comparisson
            if (restmp == n) { i--; geneticAssignments++;}
        }
        geneticComparisons ++; //for false comparisson
        
    }
    
    private static void generateMask(){
        mask = new int[arrayPresentations.length];
        memoryGenetic+=mask.length*2;
        memoryGenetic+=2; //i memory bytes
        geneticAssignments+=2;
        for(int i = 0; i < arrayPresentations.length; i++){
            geneticComparisons++;//for comparissons
            mask[i]=r.nextInt(2);
            geneticAssignments+=2;
        }
        geneticComparisons++;//for false comparissons
    }

    public static int minimWaste(int[]array){
        int result = 0;
        int min = n;
        memoryGenetic+=6;
        memoryGenetic+=array.length*2;
        geneticAssignments+=4;
        for(int i = 0; i < arrayPresentations.length; i++){
            geneticComparisons++;//for comparissons
            min -= arrayPresentations[i]*array[i];
            geneticAssignments+=2;
        }
        geneticComparisons++;//for false comparissons
        geneticAssignments++;
        return -(min);
    }
    
    private static void aptitudeFunction(){
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

    private static void insertOrdered(ArrayList<int[]> arrayL, int[] array ){
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
                arrayL.set(i, tmp1);
                tmp2 = tmp1;
                geneticAssignments+=3;
                memoryGenetic+=tmp1.length*2;
                memoryGenetic+=tmp2.length*2;
            }
            else if(minimWaste(arrayL.get(i))<minimWaste(array)){
                arrayL.set(i, array);
                tmp2 = arrayL.get(i);
                geneticAssignments+=2;
            }
        }
        geneticComparisons++;//for false comparissons
        arrayL.add(tmp2);
        geneticAssignments++;
    }

    private static void uniformCrossover(int[] parent1, int[] parent2){
        int children[][] = new int [2][parent1.length];
        memoryGenetic+=parent1.length*8;
        geneticAssignments+=4;
        memoryGenetic+=2; //i memory bytes
        for (int i = 0; i < children.length; i++) {
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
        x+="\nPadre 1: "+arrayToString(parent1);
        x+="\tPadre 2: "+arrayToString(parent2);
        x+="\nHijo 1: "+arrayToString(children[0])+" Puntuacion: "+(arrayLpopulationUC.indexOf(children[0])+1);
        x+="\nHijo 2: "+arrayToString(children[1])+" Puntuacion: "+(arrayLpopulationUC.indexOf(children[1])+1);
        records.addCrossoverUC(x);
    }

    private static void twoPointsCrossover(int[] parent1, int[] parent2){
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

        // System.out.println("Puntos: " + crosspoint1 + ", " + crosspoint2);
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
        x+="\nPadre 1: "+arrayToString(parent1);
        x+="\tPadre 2: "+arrayToString(parent2);
        x+="\nHijo 1: "+arrayToString(child1)+" Puntuacion: "+(arrayLpopulationTPC.indexOf(child1)+1);
        x+="\nHijo 2: "+arrayToString(child2)+" Puntuacion: "+(arrayLpopulationTPC.indexOf(child2)+1);
        records.addCrossoverTPC(x);
    }

    private static boolean mutationNedeed(ArrayList<int[]> populationTemp){
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
    public static int[] cloneArray(int arr[]){
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

    private static int getPuntation(int x, int arr[]){
        int puntation = 0;
        geneticAssignments+=3;
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

    private static void checkDelete(int x, ArrayList<int[]> populationTemp, int j){
        int[] temp = cloneArray(populationTemp.get(j));
        records.addToMutations("Individuo: " + arrayToString(temp) + 
                                ", puntuación: " + getPuntation(x, temp) + "\n");
        temp[0]++; //mutation
        records.addToMutations("Mutuación: " + arrayToString(temp) + 
                                ", puntuación: " + getPuntation(x, temp) + "\n");
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
        }
    }

    private static void mutation(){
        int flag = 0;
        ArrayList<int[]> populationTemp = arrayLpopulationUC;
        geneticAssignments+=3;
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
                        if (flag == 0) checkDelete(x, populationTemp, j);
                    }
                    geneticComparisons++;//for false comparissons
                }
                geneticComparisons++;//for false comparissons
            }
            geneticComparisons++;//while false comparissons
        }
        geneticComparisons++;//for false comparissons
    }

    private static void doCrossovers(){
        geneticAssignments++;
        memoryGenetic+=2; //i memory bytes
        for(int i = 0; i<populationUC.length-1; i++){
            geneticComparisons+=2;//for comparissons
            geneticAssignments++;
            memoryGenetic+=2; //i memory bytes
            for(int j = i+1; j<populationUC.length; j++){
                geneticComparisons++;//for comparissons
                geneticAssignments++;
                uniformCrossover(populationUC[i], populationUC[j]);
                twoPointsCrossover(populationTPC[i], populationTPC[j]);
                mutation();
                aptitudeFunction();
            }
            geneticComparisons++;//for false comparissons
        }   
        geneticComparisons++;//for false comparissons     
    }
    public static String arrayToString(int [] array){
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

    private static int[] getBest(int [] array1,int [] array2){
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

    public static void searchMinWaste(int [] array, int num, Records r){
        records = r;
        arrayPresentations = array;
        n = num;
        String xTPC="";
        String xUC="";
        geneticAssignments+=6;
        memoryGenetic+=array.length*4;
        memoryGenetic+=8;
        generatePopulation();
        generateMask();
        doCrossovers();
        records.addToWasteAndCombination("\nLa mejor combinación del código genético es: " + 
                arrayToString(getBest(arrayLpopulationTPC.get(0),arrayLpopulationUC.get(0))) + 
                ", con un desperdicio de: " + minimWaste(getBest(arrayLpopulationTPC.get(0),arrayLpopulationUC.get(0))) + "\n");
               
        for(int i = 0; i<5; i++){
            xTPC += "\n"+arrayToString(arrayLpopulationTPC.get(0))+" Evaluacion "+(i+1);
            xUC += "\n "+arrayToString(arrayLpopulationUC.get(0))+" Evaluacion "+(i+1);
           
        }
        records.addBest5TPC(xTPC);
        records.addBest5UC(xUC);
    } 

    

    //public void printCrossover{}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // generatePopulation(burned[2], 92);
        // System.out.println("POBLACION INICIAL :DDDDD\n\n");
        // for(int i = 0; i < population.length; i++){
        //     System.out.println("Nuevo individuo :OOOO\n\n");
        //     for(int j = 0; j < burned[2].length; j++){
        //         System.out.print(burned[2][j]+" -> ");
        //         System.out.println(population[i][j]);
        //     }
        // }
        // twoPointsCrossover(population[0], population[1]);
        // generateMask(burned[2].length);
        // uniformCrossover(population[0], population[1]);
        
        //         System.out.println("=============\n\nmascara\n\n============");

        // for(int i = 0; i < mask.length; i++){
        //     System.out.println(mask[i]+",");
        // }
        
        
        // System.out.println("=============\n\nPOBLACION UNIFORME :DDDDD\n\n============");
        // for(int i = 0; i < populationUC.size(); i++){
        //     System.out.println("Nuevo individuo :OOOO\n\n");
        //     for(int j = 0; j < burned[2].length; j++){
        //         System.out.print(burned[2][j]+" -> ");
        //         System.out.println(populationUC.get(i)[j]);
        //     }
        // }
        // System.out.println("=============\n\nPOBLACION TPC :DDDDD\n\n============");
        // for(int i = 0; i < populationTPC.size(); i++){
        //     System.out.println("Nuevo individuo :OOOO\n\n");
        //     for(int j = 0; j < burned[2].length; j++){
        //         System.out.print(burned[2][j]+" -> ");
        //         System.out.println(populationTPC.get(i)[j]);
        //     }
        // }
        
    }
}
