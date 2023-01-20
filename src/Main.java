import matrix.MatrixException;
import matrix.MyMatrix;
import matrix.MyMatrixMath;

public class Main {
    public static void main(String[] args) throws MatrixException {
        float[][] m1 = {
                {10023000000f, 2f, -1f},
                {2f, 1f, 2f},
                {-1f, 2f, 1f}
        };
        MyMatrix matrix = new MyMatrix(m1);
        MyMatrix inverse = MyMatrixMath.inverse3(matrix);
        inverse.displayInfo();
        matrix.displayInfo();
    }
}