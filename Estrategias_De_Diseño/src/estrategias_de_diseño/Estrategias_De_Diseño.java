/*
Integrantes:
    Ashley Samantha Acuña Montero  - 2021055077
    Raquel Arguedas Sánchez        - 2021032567
    Maria Fernanda Sanabria Solano - 2021005572
Fecha de Creacion: 05/17/2022 04:40pm
Fecha de Finalizacion: 06/06/2022 2:20pm
 */
package estrategias_de_diseño;


public class Estrategias_De_Diseño {
    static int [][] burned = { //
        {2,5,9}, //3
        {5,6,7,8,9}, //5
        {3,8,13,19,29,31}, //6
        {2,7,9,11,15,17,23,29,37} //9  
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int i = 0; i < burned.length; i++) {
            System.out.print("<<<<<<<<< Array [ "+burned[i][0]);
            for (int j = 1; j < burned[i].length; j++) {
                System.out.print(", "+burned[i][j]);
            }
            System.out.println(" ] >>>>>>>>>");
            (new Records()).menu(burned[i], 57);
        }
    }
    
}
