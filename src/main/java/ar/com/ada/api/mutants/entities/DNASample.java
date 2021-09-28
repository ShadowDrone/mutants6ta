package ar.com.ada.api.mutants.entities;

import org.apache.catalina.startup.Tomcat;

public class DNASample {
    
    private String[] sequence;

    public DNASample(String[] sequence) {
        this.sequence = sequence;
    
    }
    //Hacemo una matrix
    public char[][] toMatrix(){
        char[][] matrix = new char[sequence.length][sequence.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = this.sequence[i].charAt(j);
            }
        }
        return matrix;
    }

    //Tiene que tener solo N filas x N columnas
    public boolean isNxN(){
        int rows = sequence.length;
        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i].length() != rows)
                return false;
        }
        if (rows > 0)
            return true;

        return false;
        
    }
    //en este caso chequea que al menos sea de 4x4
    public boolean isAtLeast4x4(){
        return isNxN() && sequence.length >= 4;
    }

    //chequeo si tiene 
    public boolean hasValidDimensions(){
        return isAtLeast4x4();
    }

    public boolean isValid(){
        //dimension este Ok
        //que solo acepte letras A, C, G, T
        //que solo acepte mayusculas
        return true;
    }

    public void printMatrix(){
        char[][] matrix = toMatrix();
        System.out.println();
        for (int i = 0; i < matrix.length; i++) { //recorre por filas
            for (int j = 0; j < matrix.length; j++) { //x cada fila, recorre las columnas
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        
    }

    public enum DNASampleType {
        MUTANT,
        HUMAN
    }
}
