import java.util.Random;

public class GeradorId {
    private static final int MIN = 100000; 
    private static final int MAX = 999999; 
    private static final Random random = new Random();

    public static int gerarMatriculaUnica() {
        int matricula = random.nextInt((MAX - MIN) + 1) + MIN;

        return matricula;
    }
}
