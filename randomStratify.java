import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;

public class randomStratify {
  public static void main (String[] args) throws Exception {
    Scanner in = new Scanner(new File("car.txt"), "UTF-8");
    Random rand = new Random();
    PrintWriter trainOut = new PrintWriter("carTrain.arff");
    PrintWriter testOut = new PrintWriter("carTest.arff");

		while(in.hasNextLine()) {
      int n = 1 + rand.nextInt(10);
      if (n > 6) {
        testOut.println(in.nextLine().trim());
      } else {
        trainOut.println(in.nextLine().trim());
      }
    }

		trainOut.close();
    testOut.close();
  }
}
