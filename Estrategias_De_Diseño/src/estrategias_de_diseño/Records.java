
package estrategias_de_diseño;

import java.util.Scanner;

public class Records {
    private String mutations = "";
    private String wasteAndCombination = "";
    private String crossoverUC = "======Cruce Uniforme======\n";
    private String crossoverTPC = "======Cruce Dos Puntos======\n";
    private String best5UC = "======Mejores 5 del Cruce Uniforme======";
    private String best5TPC = "======Mejores 5 del Cruce Dos Puntos======";
    private int dynamicAssignments;
    private int dynamicComparisons;
    private float dynamicMemory;
    private int geneticAssignments;
    private int geneticComparisons;
    private float geneticMemory;
    private long timeDynamic;
    private long timeGenetic;

    public void menu(int [] array, int num){
        int option = 0;
        Scanner ask = new Scanner(System.in);
        long time_start, time_end;
        time_start = System.nanoTime(); 
        (new geneticAlgorithm(this, array, num)).searchMinWaste();
        time_end = System.nanoTime();
        timeGenetic = ( time_end - time_start );
        time_start = System.nanoTime(); 
        (new dynamicAlgorithm(this)).printMinWaste(array, num);
        time_end = System.nanoTime();
        timeDynamic = ( time_end - time_start );
        
        while (true){
            System.out.print("\n\tConsultas\n\n");
            System.out.print("\n\nDigite \n\t1) Desperdicio y Mejor Combinación\n\t"+
            "2) Mediciones\n\t3) Cruces\n\t4) Mutaciones\n\t5) 5 Mejores Problaciones\n"+
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
                    System.out.println("\nTiempo: "+(timeDynamic/1000000)+" milisegundos");
                    System.out.println("\n\n===================Algoritmo Genético===================\n");
                    System.out.println("\nAsignaciones: "+geneticAssignments);
                    System.out.println("\nComparaciones: "+geneticComparisons);
                    System.out.println("\nMemoria: "+geneticMemory+" bytes");
                    System.out.println("\nTiempo: "+(timeGenetic/1000000)+" milisegundos");
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

    public void setDynamicAssignments(int n){ dynamicAssignments = n; }
    public void setDynamicComparisons(int n){ dynamicComparisons = n; }
    public void setDynamicMemory(float n){ dynamicMemory = n; }
    public void setGeneticAssignments(int n){ geneticAssignments = n; }
    public void setGeneticComparisons(int n){ geneticComparisons = n; }
    public void setGeneticMemory(float n){ geneticMemory = n; }
    
    public void addToMutations(String add){ mutations += add; }
    public void addToWasteAndCombination(String add){ wasteAndCombination += add; }

    public void addCrossoverUC(String add){ crossoverUC += add; }
    public void addCrossoverTPC(String add){ crossoverTPC += add; }

    public void addBest5UC(String add){ best5UC += add; }
    public void addBest5TPC(String add){ best5TPC += add; }

}
