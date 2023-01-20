package matrix;
/**
 * @author Ben Abbott
 * */

import java.awt.*;
import java.text.DecimalFormat;

public class MyMatrix {
    private float[][] matrix;
    private int width;
    private int height;
    private Dimension matrixDimens;
    @SafeVarargs
    public MyMatrix(float[]... map){
        this.matrix = map;

        this.width = this.matrix[0].length;
        this.height = this.matrix.length;
        this.matrixDimens = new Dimension(this.width, this.height);
    }

    /**
     * @return 2-Dimensional list with matrix values
     * */
    public float[][] getMatrix(){
        return this.matrix;
    }

    /**
     * @return Matrix Dimensions (width, height)
     * */
    public Dimension getMatrixDimen(){
        return this.matrixDimens;
    }

    /**
     * @return Matrix width - the number of columns
     * */
    public int getCols(){
        return this.width;
    }

    /**
     * @return Matrix height - the number of rows
     * */
    public int getRows(){
        return this.height;
    }

    /**
     * Displays the matrix list
     * */
    public void displayMatrix(){
        for(float[] row: this.matrix){
            String output = "";
            for(float val: row){
                String disVal = String.valueOf(val) + "    ";
                output += disVal;
            }
            System.out.println(output);
        }
    }

    /**
     * Displays current matrix data
     * */
    public void displayInfo(){
        System.out.println("DIMENSION .. ");
        System.out.println(String.format("\tWidth: %d", this.width));
        System.out.println(String.format("\tHeight: %d", this.height));

        System.out.println("\nMATRIX .. ");
        this.displayMatrix();
    }
}