
package estrategias_de_diseño;

import java.util.Scanner;

public class Records {
    static String mutations = "";
    static String wasteAndCombination = "";
    static String crossoverUC = "======Cruce Uniforme======\n";
    static String crossoverTPC = "======Cruce Dos Puntos======\n";
    static String best5UC = "======Mejores 5 del Cruce Uniforme======";
    static String best5TPC = "======Mejores 5 del Cruce Dos Puntos======";
    static int dynamicAssignments;
    static int dynamicComparisons;
    static float dynamicMemory;
    static int geneticAssignments;
    static int geneticComparisons;
    static float geneticMemory;

    public void menu(int [] array, int num){
        int option = 0;
        Scanner ask = new Scanner(System.in);
        (new geneticAlgorithm()).searchMinWaste(array, num, this);
        while (true){
            System.out.print("\n\nConsultas\n\n");
            System.out.print("\n\nDigite \n\t1) Desperdicio y Mejor Combinación\n\t"+
            "2) Mediciones\n\t3) Cruces\n\t4) Mutaciones\n\t5) 5 Mejores Problaciones\n\n"+
            "\t6) Salir\n\n");
            System.out.println("Ingrese la opción: ");
            option = ask.nextInt();
            switch(option){
                case 1:
                    System.out.println("\n Desperdicio y Mejor Combinación:");
                    System.out.println(wasteAndCombination);
                    System.out.println("\n");
                    break;
                case 2:
                    System.out.println("\n===================Algoritmo Dinámico===================\n");
                    System.out.println("\nAsignaciones: "+dynamicAssignments);
                    System.out.println("\nComparaciones: "+dynamicComparisons);
                    System.out.println("\nMemoria: "+dynamicMemory+" bytes");
                    System.out.println("\n\n===================Algoritmo Genético===================\n");
                    System.out.println("\nAsignaciones: "+geneticAssignments);
                    System.out.println("\nComparaciones: "+geneticComparisons);
                    System.out.println("\nMemoria: "+geneticMemory+" bytes");
                    break;
                case 3:
                    System.out.println(crossoverUC);
                    System.out.println("\n======================================\n");
                    System.out.println(crossoverTPC);
                    System.out.println("\n");
                    break;
                case 4:
                    System.out.println("\n Mutaciones:");
                    System.out.println(mutations);
                    System.out.println("\n");
                    break;
                case 5:
                    System.out.println(best5UC);
                    System.out.println("\n======================================\n");
                    System.out.println(best5TPC);
                    System.out.println("\n");
                    break;
                case 6: System.out.println("Hasta Luego :D");return;
                default:
                    System.out.println("\nLa opción digitada no se encuentra entre las opciones.\n");
                    System.out.println("Inténtelo nuevamente\n");
            }
        }
    }

    public static void setDynamicAssignments(int n){ dynamicAssignments = n; }
    public static void setDynamicComparisons(int n){ dynamicComparisons = n; }
    public static void setDynamicMemory(int n){ dynamicMemory = n; }
    public static void setGeneticAssignments(int n){ geneticAssignments = n; }
    public static void setGeneticComparisons(int n){ geneticComparisons = n; }
    public static void setGeneticMemory(int n){ geneticMemory = n; }
    
    public static void addToMutations(String add){ mutations += add; }
    public static void addToWasteAndCombination(String add){ wasteAndCombination += add; }

    public static void addCrossoverUC(String add){ mutations += add; }
    public static void addCrossoverTPC(String add){ mutations += add; }

    public static void addBest5UC(String add){ best5UC += add; }
    public static void addBest5TPC(String add){ best5TPC += add; }

}
