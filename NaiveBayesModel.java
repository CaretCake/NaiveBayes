import java.util.ArrayList;
import java.util.PriorityQueue;
import javafx.util.Pair;
import java.util.Comparator;
import java.util.HashMap;

public class NaiveBayesModel {
  private ArrayList<Feature> features;
	private ArrayList<ArrayList<Double>> dataInstances;

  /* Parses and stores feature names and data instances from training dataset .arff file for model
   *
   * @param filename string containing name of the .arff file containing the training dataset
   */
  public void train(String filename) {
    try {
      // parse data from filename
      ArffParser parser = new ArffParser();
      parser.setInputFilename(filename);
      parser.parseDataFromArffFile();
      // store it
      features = parser.getFeatures();
      dataInstances = parser.getDataInstances();
      System.out.println(features);
      System.out.println(dataInstances);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /*
   *
   * @param filename string containing name of the .arff file containing the training dataset
   */
  public void test(String filename) {
    // parse data from filename
    try {
      ArffParser parser = new ArffParser();
      parser.setInputFilename(filename);
      parser.parseDataFromArffFile();
      ArrayList<Feature> testFeatures = parser.getFeatures();
    	ArrayList<ArrayList<Double>> testDataInstances = parser.getDataInstances();

      // for every instance in testing dataset
      for (ArrayList<Double> testInstance : testDataInstances) {
          // for each possible target feature value, calculate the probability of the given value of the test instances
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
