import matrix.MyMatrix;
import matrix.MyMatrixMath;

public class Main {
    public static void main(String[] args) {
//        Window window = new Window();
//        window.render();
        float[][] m1 = {
                {1.023233424f, 2f, -1f},
                {2f, 1f, 2f},
                {-1f, 2f, 1f}
        };
        MyMatrix matrix = new MyMatrix(m1);
        matrix.displayInfo();
    }
}