public class NaiveBayes {
  public static void main (String[] args) {

    String trainFilename = args[0];
    String testFilename = args[1];

    NaiveBayesModel nbModel = new NaiveBayesModel();

    nbModel.train(trainFilename);
    nbModel.test(testFilename);

  }
}
