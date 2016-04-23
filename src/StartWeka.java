import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.neural.NeuralNode;
import weka.classifiers.*;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import java.io.*;
import java.util.*;

public class StartWeka 
{
public static void main(String[] args) throws Exception
{
BufferedReader br= null;
br= new BufferedReader(new FileReader("C:\\Users\\Shakti\\Documents\\MLProject\\amazon_long_filtered.arff"));
Instances ins=new Instances(br);
ins.setClassIndex(ins.numAttributes()-1);
br.close();
NaiveBayes nb=new NaiveBayes();
//NaiveBayesMultinomial m=new NaiveBayesMultinomial();
//MultilayerPerceptron mp=new MultilayerPerceptron();
nb.buildClassifier(ins);
Evaluation eval=new Evaluation(ins);
eval.crossValidateModel(nb, ins, 10, new Random(1));
/*
String s1=mp.hiddenLayersTipText();
String s2=mp.getHiddenLayers();
double d3=mp.getLearningRate();
System.out.println("hidden layers= "+s1+"|||"+"# of hidden layers= "+s2+"|||"+"learning rate= "+d3);
*/
System.out.println(eval.toSummaryString("\n Results \n ================== \n",true));
System.out.println(eval.fMeasure(1)+" "+ eval.precision(1)+" "+eval.recall(1));

chat_server cs = new chat_server();
cs.startchat();
}
}
