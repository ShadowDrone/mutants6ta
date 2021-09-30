package ar.com.ada.api.mutants.entities;

import org.apache.catalina.startup.Tomcat;

public class DNASample {

    private String[] sequence;

    public DNASample(String[] sequence) {
        this.sequence = sequence;

    }

    // Hacemo una matrix
    public char[][] toMatrix() {
        char[][] matrix = new char[sequence.length][sequence.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = this.sequence[i].charAt(j);
            }
        }
        return matrix;
    }

    public char[][] toMatrixMirror() {
        char[][] matrix = toMatrix();
        char[][] mirror = new char[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                mirror[i][matrix.length-j-1]= matrix[i][j];
            }
        }
        return matrix;
    }

    // Tiene que tener solo N filas x N columnas
    public boolean isNxN() {
        int rows = sequence.length;
        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i].length() != rows)
                return false;
        }
        if (rows > 0)
            return true;

        return false;

    }

    // en este caso chequea que al menos sea de 4x4
    public boolean isAtLeast4x4() {
        return isNxN() && sequence.length >= 4;
    }

    // chequeo si tiene
    public boolean hasValidDimensions() {
        return isAtLeast4x4();
    }

    public boolean isValid() {
        // dimension este Ok
        boolean dimensionesOk = hasValidDimensions();
        if (!dimensionesOk)
            return false;

        // que solo acepte letras A, C, G, T
        // que solo acepte mayusculas
        if (!onlyDNALetters())
            return false;
        return true;
    }

    public boolean onlyDNALetters() {
        char[][] matrix = this.toMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                // ACA RECORREMOS POR CELDA.
                // matrix[i][j] = es una celda
                char caracter = matrix[i][j];
                if (caracter != 'A' && caracter != 'C' && caracter != 'G' && caracter != 'T')
                    return false;
            }
        }
        return true;// si llega aca, es que eran todas A, C, G, T
    }

    public void printMatrix() {
        char[][] matrix = toMatrix();
        System.out.println();
        for (int i = 0; i < matrix.length; i++) { // recorre por filas
            for (int j = 0; j < matrix.length; j++) { // x cada fila, recorre las columnas
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }

    }

    public enum DNASampleType {
        MUTANT, HUMAN
    }
}
