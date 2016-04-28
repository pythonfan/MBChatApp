import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class Predictor {
	int predictedClass;
	String predictionText ="";
	public static Predictor instance = null;
	Predictor()
	{}
	 public static Predictor getInstance() {
	      if(instance == null) {
	         instance = new Predictor();
	      }
	      return instance;
	   }
	synchronized int predict(String serverTexts)
	{
		if((predictionText.length()+serverTexts.length())<10)
			predictionText+=serverTexts;
		else
			predictionText=serverTexts;
		predictionText.replaceAll("[^A-Za-z]", "");
		System.out.println("Making prediction on: "+ predictionText);

		try {
			BufferedReader br= null;

			br= new BufferedReader(new FileReader("C:\\Users\\Shakti\\Documents\\MLProject\\amazon_long_filtered.arff"));
			Instances testinstances = new Instances(br);
			br.close();
			testinstances.setClassIndex(testinstances.numAttributes()-1);

			 ArrayList<Attribute> attrlist = new ArrayList<Attribute>();
				br= new BufferedReader(new FileReader("C:\\Users\\Shakti\\Documents\\MLProject\\amazon_long_filtered.arff"));

				ArffReader ar = new ArffReader(br);
				br.close();
				 Instance inst = new DenseInstance(ar.getStructure().numAttributes());

				//List of attributes
				for(int i=0; i<ar.getStructure().numAttributes()-1; i++ )
					{
						attrlist.add(ar.getStructure().attribute(i));
						//System.out.println(ar.getStructure().attribute(i).name());
						if(ar.getStructure().attribute(i).name().length() > 1 && predictionText.contains(ar.getStructure().attribute(i).name()))
						{
							inst.setValue(ar.getStructure().attribute(i), countMatches(ar.getStructure().attribute(i).name(),predictionText));
						}
						else
						{
							inst.setValue(ar.getStructure().attribute(i), 0);
						}
					}
				inst.setDataset(testinstances);
		
		//Deserialize

			ObjectInputStream ois;
				ois = new ObjectInputStream(new FileInputStream("C:\\Users\\Shakti\\Documents\\MLProject\\probabilities.model"));
				Classifier cls = (Classifier) ois.readObject();
				ois.close();
				double prediction = cls.classifyInstance(inst);
				double[] probabilities = cls.distributionForInstance(inst);
				
				System.out.println("Predicted probabilities :"+probabilities[0]+ " "+ probabilities[1]);

				System.out.println("Predicted class :"+prediction);
				predictedClass= (int) prediction;
				if(Math.abs(probabilities[0] - probabilities[1])<0.3)
				{
					predictedClass = 3;
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		return predictedClass;
	}
	private int countMatches(String name, String predictionText2) {
		// TODO Auto-generated method stub
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		    lastIndex = predictionText2.indexOf(name,lastIndex);

		    if(lastIndex != -1){
		        count ++;
		        lastIndex += name.length();
		    }
		}
		System.out.println(name+ " " +count);
		return count;
	}
	int getPrediction()
	{
		return predictedClass;
	}
	
}
