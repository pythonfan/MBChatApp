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
import weka.core.Instances;

public class Predictor {
	int predictedClass = 0;
	Predictor()
	{}
	int predict()
	{
		//Deserialize
		BufferedReader br= null;
		
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream("C:\\Users\\Shakti\\Documents\\MLProject\\probabilities.model"));
				Classifier cls = (Classifier) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
		
		return predictedClass;
	}
}
