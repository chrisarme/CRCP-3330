// Chris Arme
// The bread and butter of this project
// This reads all the data that is pass into it and creates a list of possible data to choose from
// It then generates a random data set based off of the possible data to choose from and their probablility

package aNewPackage_test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

public class ProbabilityListGenerator <E>
{
	ArrayList<E> dataset;
	
	ArrayList<E> dataList = new ArrayList<E>(); 
	ArrayList<E> repeatedDataList = new ArrayList<E>();
	ArrayList<Integer> dataTimesRepeated = new ArrayList<Integer>();
	ArrayList<Double> dataChanceToAppear = new ArrayList<Double>();
	ArrayList<Double> dataSumToAppear = new ArrayList<Double>();
	
	ArrayList<E> generatedData = new ArrayList<E>();
	
	public void clearData()
	{
		dataList = new ArrayList<E>(); 
		repeatedDataList = new ArrayList<E>();
		dataTimesRepeated = new ArrayList<Integer>();
		dataChanceToAppear = new ArrayList<Double>();
		dataSumToAppear = new ArrayList<Double>();
		
		generatedData = new ArrayList<E>();
	}
	
	public void train(ArrayList<E> newDataSet)
	{
		dataset = newDataSet;
		
		//System.out.println(dataset.size());
		
		for (E data : dataset)
		{
			String dataString = data.toString();
			
			if (Double.parseDouble(dataString) > 0)
			{
				repeatedDataList.add(data);
				
				int index = dataList.indexOf(data);
				
				//System.out.println(index);
				
				if (index == -1)
				{
					dataList.add(data);
					dataTimesRepeated.add(1);
					
					//System.out.println("halp");
				}
				else
				{
					dataTimesRepeated.set(index, dataTimesRepeated.get(index) + 1);
				}
			}
			
			
			//System.out.println(String.valueOf(data));
		}
		
		//System.out.println(dataTimesRepeated.size());
		
		// START this is just for test data
		
		int timesRepeated = 0;
		
		//System.out.println(timesRepeated);
		
		for (int i = 0; i < dataTimesRepeated.size(); i++)
		{
			timesRepeated += dataTimesRepeated.get(i);
		}

		for (int i = 0; i < dataTimesRepeated.size(); i++)
		{
			dataChanceToAppear.add((double) dataTimesRepeated.get(i) / timesRepeated);
		}
		
		//System.out.println(dataChanceToAppear.size());
		
		for (int i = 0; i < dataChanceToAppear.size(); i++)
		{
			if (i == 0)
			{
				dataSumToAppear.add((double) dataChanceToAppear.get(i));
				//System.out.println(dataSumToAppear.get(i));
			}
			else
			{
				dataSumToAppear.add((double) dataChanceToAppear.get(i) + dataSumToAppear.get(i - 1));
				//System.out.println(dataSumToAppear.get(i));
			}
		}
		
		//System.out.println("Size: " + dataTimesRepeated.size());
		//System.out.println(timesRepeated);
		
		// END TEST
		
	}
	
	public void generate(int sizeOfGeneration)
	{	
		/*for (int i = 0; i < sizeOfGeneration; i++)
		{
			double randomNum = (Math.random());
			
			//System.out.println(repeatedDataList.get((int) randomNum));
			
			generatedData.add(repeatedDataList.get((int) randomNum));
		}*/
		
		for (int i = 0; i < sizeOfGeneration; i++)
		{
			double randomNum = Math.random();			
			//boolean foundThing = false;
			//int j = 0;

			for (int j = 0; j < dataSumToAppear.size() - 1; j++)
			//while (j < dataSumToAppear.size())
			{	
				if (randomNum <= dataSumToAppear.get(j))
				{
					//System.out.println(repeatedDataList.get(j));
					generatedData.add(repeatedDataList.get(j));
					//System.out.println("Stopping here");
					break;
				}
				
				//System.out.println("continuing...");
				
				if (j == dataSumToAppear.size() - 2)
				{
					generatedData.add(repeatedDataList.get(repeatedDataList.size() - 1));
					//System.out.println("Oh no");
					//break;
				}
				
				//j++;
			}
		}
	}
	
	public ArrayList<E> returnGeneratedArray()
	{
		return generatedData;
	}
	
	public void printProbability()
	{
		System.out.println("-----UNIT TEST DATA START-----");
		
		for (int i = 0; i < dataList.size(); i++)
		{
			System.out.println("Data: " + dataList.get(i) + " | Chance to appear: " + dataChanceToAppear.get(i));
		}
		
		System.out.println("------UNIT TEST DATA END------");
	}
	
	public void printPitchesAndRhythm()
	{
		System.out.println("-----UNIT TEST DATA START-----");
		
		for (int i = 0; i < generatedData.size(); i++)
		{
			System.out.print(generatedData.get(i) + " ");
		}
		
		System.out.println('\n' + "------UNIT TEST DATA END------");
	}
}
