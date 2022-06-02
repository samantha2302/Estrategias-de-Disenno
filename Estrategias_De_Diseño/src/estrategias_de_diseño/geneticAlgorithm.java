package estrategias_de_diseño;

import java.util.Random;
import java.util.ArrayList;

public class geneticAlgorithm {
    static Random r = new Random();
    static int [][] burned = { //
        {2,5,9}, //3
        {7,9,8,6,5}, //5
        {3,8,13,19,29,31}, //6
        {2,7,9,11,15,17,23,29,37} //9  
    }; //TODO: QUITAR ESTO!!!

    static int population[][];
    static ArrayList<int[]> populationUC = new ArrayList<int[]>();
    static ArrayList<int[]> populationTPC = new ArrayList<int[]>();
    static int mask[];
    static int array;
    static int n;


    private static void defPopSize(int size){
        int populationSize;
        switch (size) {
            case 3 -> {populationSize = 3; break;}
            case 5 -> {populationSize = 5; break;}
            case 6 -> {populationSize = 10; break;}
            default -> populationSize = 20;
        }
        population = new int[populationSize][size];
    }

    private static void defMaskSize(int size){
        int maskSize;
        switch (size) {
            case 3 -> {maskSize = 3; break;}
            case 5 -> {maskSize = 5; break;}
            case 6 -> {maskSize = 10; break;}
            default -> maskSize = 20;
        }
        mask = new int[maskSize];
    }

    public static void generatePopulation(int[] x, int n){
        defPopSize(x.length);
        int tmp;
        for(int i = 0; i < population.length; i++){
            tmp = 0;
            for(int j = 0; j < x.length; j++){
                population[i][j] = r.nextInt((n/x[j])+1);
                tmp += population[i][j]*x[j];
            }
            if (tmp == n) { i--; System.out.println("Paso lo inimaginable :OOO");}
        }
    }
    
    private static void generateMask(int size){
        defMaskSize(size);
        for(int i = 0; i < size; i++){
            mask[i]=r.nextInt(2);
        }
    }

    public static int minimalWaste (int[]){
        
        

    }

    
    
    private static void aptitudeFunction(arrayL ArrayList<int[]>, index int){
        
        
    }

    private void shiftRight(int[]){
        for (int i=9; i>0; i--){
            minimalWaste
        }
    }

    private static void uniformCrossover(int[] parent1, int[] parent2){
        int children[][] = new int [2][parent1.length];
        for (int i = 0; i < children.length; i++) {
            System.out.println("i -> "+ i);
            if (mask[i]==0){
                // System.out.println("Padre 1 -> "+parent1[i]+" Padre 2 -> "+  parent2[i]);
                children[0][i] = parent1[i];
                children[1][i] = parent2[i];
            }
            else{
                // System.out.println("en el else: Papa1[i]" + parent1[i] + ", Papa2[i]" + parent2[i]);
                children[0][i] = parent2[i];
                children[1][i] = parent1[i];
            }
        }
        populationUC.add(children[0]);
        populationUC.add(children[1]);
    }

    private static void twoPointsCrossover(int[] parent1, int[] parent2){
        int crosspoint1 = r.nextInt((parent1.length/2)+1);
        int crosspoint2 = (parent1.length/2) + r.nextInt((parent1.length/2)+1);
        int[] child1 = new int[parent1.length];
        int[] child2 = new int[parent1.length];

        if (crosspoint1==0) crosspoint1++;
        if (crosspoint2==parent1.length-1) crosspoint2--;
        if (crosspoint1==parent1.length/2 && crosspoint2==0) crosspoint2--;

        System.out.println("Puntos: " + crosspoint1 + ", " + crosspoint2);
        for (int i = 0; i < child1.length; i++) {
            if (i < crosspoint1 || i > crosspoint2){
                child1[i] = parent1[i];
                child2[i] = parent2[i];
            }
            else{
                child1[i] = parent2[i];
                child2[i] = parent1[i];
            }
        }
        populationTPC.add(child1);
        populationTPC.add(child2);
    }

    private static void mutationNedeed(ArrayList<int[]> populationTemp){
        for(int i = 0; i < populationTemp.size()-1; i++){
            for(int j = i+1; j < populationTemp.size(); j++){
                flag = 0;
                for(int k=0; k<populationTemp.get(i).length; k++){
                    if (populationTemp.get(i)[k] != populationTemp.get(j)[k]) flag = 1;
                }
                if (flag == 0) return true;
            }
        }
        return false;
    }

    private static void checkDelete(){

    }
        
        

    private static void mutation(){
        int flag = 0;
        ArrayList<int[]> populationTemp = populationUC;
        for(int i = 0; i < 2; i++){
            if (i==1) populationTemp = populationTPC;
            for(int i = 0; i < populationTemp.size()-1; i++){
                for(int j = i+1; j < populationTemp.size(); j++){
                    flag = 0;
                    for(int k=0; k<populationTemp.get(i).length; k++){
                        if (populationTemp.get(i)[k] != populationTemp.get(j)[k]) flag = 1;
                    }
                    if (flag == 0) checkDelete(i,j);
                }
            }
        }
    }

    private static void doCrossover(){
        int k;
        for(int i = 0; i<population.length-1; i++){
            for(int j = i+1; j<population.length; j++){
                k++;
                uniformCrossover(population[i], population[j]);
                twoPointsCrossover(population[i], population[j]);
            }
        }
        
        // aptitudeFunction(populationUC, k*2);
        // aptitudeFunction(populationTPC, k*2);
    }

    //public void printCrossover{}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        generatePopulation(burned[2], 92);
        System.out.println("POBLACION INICIAL :DDDDD\n\n");
        for(int i = 0; i < population.length; i++){
            System.out.println("Nuevo individuo :OOOO\n\n");
            for(int j = 0; j < burned[2].length; j++){
                System.out.print(burned[2][j]+" -> ");
                System.out.println(population[i][j]);
            }
        }
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
