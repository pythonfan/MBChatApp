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
	int predictedClass = 0;
	Predictor()
	{}
	int predict()
	{
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
						inst.setValue(ar.getStructure().attribute(i), 0);
					}
				inst.setDataset(testinstances);
		
		//Deserialize

			ObjectInputStream ois;
				ois = new ObjectInputStream(new FileInputStream("C:\\Users\\Shakti\\Documents\\MLProject\\probabilities.model"));
				Classifier cls = (Classifier) ois.readObject();
				ois.close();
				double prediction = cls.classifyInstance(inst);
				System.out.println("Predicted class :"+prediction);
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
}
