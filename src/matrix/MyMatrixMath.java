package matrix;

import exception.MatrixDimensionError;
import exception.MatrixInverseError;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ben Abbott
 * */
public abstract class MyMatrixMath {

    /**
     * @apiNote The order in which the matrices are multiplied matters.
     * @see <a href="https://en.wikipedia.org/wiki/Matrix_multiplication">Matrices Multiplication</a>
     * @param m1 MyMatrix
     * @param m2 MyMatrix
     * @return result of m1*m2
     * @throws MatrixDimensionError when the height of m1 isn't the same as the width of m2
     * */
    @Contract("_, _ -> new")
    public static @NotNull MyMatrix multiply(@NotNull MyMatrix m1, @NotNull MyMatrix m2) throws MatrixDimensionError {
        if(m1.getCols() != m2.getRows()){
            throw new MatrixDimensionError("m1 column count is not the same as m2 row count");
        } else{
            float[][] res = new float[m1.getRows()][m2.getCols()];
            float store;
            for(int k = 0; k < m2.getCols(); k ++){
                for(byte i = 0; i < m1.getRows(); i ++){
                    store = 0;
                    for(byte j = 0; j < m1.getMatrixDimen().width; j ++){
                        store += m1.getMatrix()[i][j] * m2.getMatrix()[i][k];
                    }
                    res[i][k] = store;
                }
            }

            return new MyMatrix(res);
        }
    }

    /**
     * @apiNote Matrices must be of the same dimension
     * @param m1 MyMatrix
     * @param m2 MyMatrix
     * @return result of m1*m2
     * @throws MatrixDimensionError when the matrices aren't the same size
     * */
    @Contract("_, _ -> new")
    public static @NotNull MyMatrix add(@NotNull MyMatrix m1, @NotNull MyMatrix m2) throws MatrixDimensionError {
        if(m1.getMatrixDimen() != m2.getMatrixDimen()){
            throw new MatrixDimensionError("matrices are not the same size. Ensure they have the same row and column size");
        } else{
            float[][] res = new float[m1.getRows()][m1.getCols()];

            for(byte i = 0; i < m1.getRows(); i ++){
                for(byte j = 0; j < m1.getCols(); j ++){
                    res[i][j] = m1.getMatrix()[i][j] + m2.getMatrix()[i][j];
                }
            }
            return new MyMatrix(res);
        }
    }

    /**
     * Method that multiplies all values in a matrix by a constant
     * @param m1 Matrix
     * @param multiplier Constant that multiplies all values in m1
     * @return New matrix with multiplied values
     * */
    @Contract("_, _ -> new")
    public static @NotNull MyMatrix constMultiply(@NotNull MyMatrix m1, @NotNull float multiplier){
        float[][] res = new float[m1.getRows()][m1.getCols()];
        for(byte j = 0; j < m1.getRows(); j ++){
            for(byte i = 0; i < m1.getCols(); i ++){
                res[j][i] = m1.getMatrix()[j][i] * multiplier;
            }
        }
        return new MyMatrix(res);
    }

    /**
     * Method finds the determinant of a 2x2 matrix
     * @param m1 Matrix
     * @return The determinant of the input matrix
     * */
    public static float determinant2(@NotNull MyMatrix m1){
        float det = (m1.getMatrix()[0][0] * m1.getMatrix()[1][1]) - (m1.getMatrix()[0][1] * m1.getMatrix()[1][0]);
        return (float) det;
    }

    /**
     * Method finds the determinant of a 2x2 matrix
     * @see <a href="https://www.cuemath.com/algebra/inverse-of-3x3-matrix/">See how to find the inverse of a 2x2 matrix</a>
     * @param m1 Matrix
     * @return The determinant of the input matrix
     * @throws MatrixDimensionError when matrix is strictly not a 2x2 matrix
     * */
    public static @NotNull MyMatrix inverse2(@NotNull MyMatrix m1) throws MatrixDimensionError {
        if(m1.getCols() == 2 && m1.getRows() == 2){
            float[][] new_mat = new float[2][2];

            new_mat[0][0] = m1.getMatrix()[1][1];
            new_mat[0][1] = m1.getMatrix()[0][1];
            new_mat[1][0] = m1.getMatrix()[1][0];
            new_mat[1][1] = m1.getMatrix()[0][0];

            MyMatrix mat = new MyMatrix(new_mat);
            float det = determinant2(mat);
            return constMultiply(mat, 1/det);
        } else{
            throw new MatrixDimensionError("Matrices must have row and column size of 2");
        }
    }

    /**
     * Method finds the inverse of a 3x3 matrix
     * @see <a href="https://www.mathcentre.ac.uk/resources/uploaded/sigma-matrices7-2009-1.pdf">How to find the inverse of a 3x3 matrix</a>
     * @param m1 Matrix
     * @return The inverse matrix of a 3x3 matrix
     * @throws MatrixDimensionError when matrix is strictly not a 3x3 matrix
     * @throws MatrixInverseError when matrix determinant is equal to 0 i.e., has no inverse
     * */
    public static @NotNull MyMatrix inverse3(@NotNull MyMatrix m1) throws MatrixDimensionError, MatrixInverseError {
        int[][] negatives = {
                {1,-1,1},
                {-1,1,-1},
                {1,-1, 1}
        };

        float[][] inverse = new float[3][3];

        if(m1.getCols() == 3 && m1.getRows() == 3){
            for(byte j = 0; j < 3; j ++){
                for(byte i = 0; i < 3; i ++){
                    int x1 = Math.floorMod(i+1, 3);
                    int x2 = Math.floorMod(i+2, 3);
                    int y1 = Math.floorMod(j+1, 3);
                    int y2 = Math.floorMod(j+2, 3);

                    float[][] tmp = extract2DMatrix(m1, x1, y1, x2, y2);

                    int neg = negatives[j][i];
                    float det = determinant2(new MyMatrix(tmp)) * neg;
                    inverse[j][i] = det;
                }
            }

            MyMatrix transposed = transpose(new MyMatrix(inverse));
            float det = determinant3(m1);
            if(det != 0){
                return constMultiply(transposed, 1/det);
            } else{
                throw new MatrixDimensionError("NoMatrixInverseException");
            }
        }
        throw new MatrixInverseError("Matrix has no inverse");
    }

    /**
     * Private method for extracting smaller matrices from a 3x3 matrix when finding its inverse
     * @return 2x2 matrix
     * */
    private static float[] @NotNull [] extract2DMatrix(@NotNull MyMatrix m1, int x1, int y1, int x2, int y2){
        float[][] matrix = m1.getMatrix();

        if(x1 > x2){
            int tmp = x2;
            x2 = x1;
            x1 = tmp;

        }

        if(y1 > y2){
            int tmp = y2;
            y2 = y1;
            y1 = tmp;
        }

        float[][] tmp = new float[2][2];
        tmp[0][0] = matrix[y1][x1];
        tmp[0][1] = matrix[y1][x2];
        tmp[1][0] = matrix[y2][x1];
        tmp[1][1] = matrix[y2][x2];

        return tmp;
    }

    /**
     * Returns the determinant of a 3x3 matrix
     * @return determinant
     * */
    public static float determinant3(MyMatrix m1) {
        float det = 0;
        int[] neg = {1,-1,1};

        for(byte i = 0; i < 3; i ++){
            float val = m1.getMatrix()[0][i];

            int x1 = Math.floorMod(i+1, 3);
            int x2 = Math.floorMod(i+2, 3);
            int y1 = 1;
            int y2 = 2;

            float[][] tmp = extract2DMatrix(m1, x1, y1, x2, y2);

            det += val * determinant2(new MyMatrix(tmp)) * neg[i];
        }

        return det;
    }

    /**
     * Private method called when inverting a 3x3 matrix
     * @return Newly transposed matrix
     * */
    @Contract("_ -> new")
    public static @NotNull MyMatrix transpose(@NotNull MyMatrix m1){
        float[][] temp = new float[m1.getMatrixDimen().height][m1.getMatrixDimen().width];

        for(byte y = 0; y < m1.getMatrixDimen().height; y ++){
            for(byte x = 0; x < m1.getMatrixDimen().width; x ++){
                temp[y][x] = m1.getMatrix()[x][y];
            }
        }

        return new MyMatrix(temp);
    }
}
