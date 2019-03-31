import java.util.ArrayList;
import java.util.PriorityQueue;
import javafx.util.Pair;
import java.util.Comparator;
import java.util.HashMap;

public class NaiveBayesModel {
  private ArrayList<Feature> features;
	private ArrayList<ArrayList<Double>> dataInstances;
  private static final double SMOOTHING_CONSTANT = 1.0;

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
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /* Determines probability for each potential target feature value for each instance
   * of the training dataset based on test dataset and uses that to determine predicted
   * target value. Then prints accuracy of those predictions to the screen.
   *
   * @param filename string containing name of the .arff file containing the training dataset
   */
  public void test(String filename) {
    try {
      // parse data from filename
      ArffParser parser = new ArffParser();
      parser.setInputFilename(filename);
      parser.parseDataFromArffFile();
      ArrayList<Feature> testFeatures = parser.getFeatures();
    	ArrayList<ArrayList<Double>> testDataInstances = parser.getDataInstances();
      int correctPredictionCount = 0;
      int numOfPossibleTargetFeatureValues = ((CategoricalFeature)features.get(features.size() - 1)).getNumberOfOptions();

      // for every instance in testing dataset
      for (ArrayList<Double> testInstance : testDataInstances) {
          double[] probabilities = new double[numOfPossibleTargetFeatureValues];

          // for each possible target feature value
          for (int targetIndex = 0; targetIndex < numOfPossibleTargetFeatureValues; targetIndex++) {
            probabilities[targetIndex] = getProbabilityTotalOf(testInstance, (double)targetIndex);
          }

          double predictedTargetValue = (double)getIndexOfLargest(probabilities);

          //System.out.println("predicted: " + predictedTargetValue + ", actual: " + testInstance.get(testInstance.size() - 1));
          if ((double)predictedTargetValue == (double)testInstance.get(testInstance.size() - 1)) {
            correctPredictionCount++;
          }
      }

      double accuracy = correctPredictionCount / (double)testDataInstances.size();
      System.out.println("\nAccuracy of naive Bayes on " + filename + " is " + (accuracy * 100) + "%\n");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /* Calculates the probability for a given instance having a given target feature value.
   *
   * @param instance ArrayList<Double> a list of the data values of the given instance
   * @param targetValue double the value of the target feature which probability needs to be calculated for
   * @return double the probablility of the instance having the particular given target feature value
   */
  private double getProbabilityTotalOf(ArrayList<Double> instance, double targetValue) {
    double probabilityTotal = 0;
    // for each attribute of test instance, calculate the probability of the given value (exclude target feature)
    for (int i = 0; i < instance.size() - 1; i++) {
      int totalNumOfInstancesWithTargetValue = 0;
      int positiveHits = 0;
      for (ArrayList<Double> trainingInstance : dataInstances) {
        if (trainingInstance.get(trainingInstance.size() - 1) == targetValue) { // if instance is of target value
          totalNumOfInstancesWithTargetValue++;
          if ((double)trainingInstance.get(i) == (double)instance.get(i)) {
            positiveHits++;
          }
        }
      }
      double prob = (positiveHits + SMOOTHING_CONSTANT)/(totalNumOfInstancesWithTargetValue + (((CategoricalFeature)features.get(i)).getNumberOfOptions() * SMOOTHING_CONSTANT));
      probabilityTotal += prob;
    }
    return probabilityTotal;
  }


  public int getIndexOfLargest(double[] array) {
    if (array == null || array.length == 0) return -1;

    int indexOfLargest = 0;
    for (int i = 1; i < array.length; i++) {
      if (array[i] > array[indexOfLargest]) {
        indexOfLargest = i;
      }
    }

    return indexOfLargest;
  }

}
