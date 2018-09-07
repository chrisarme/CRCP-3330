package aNewPackage_test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

public class ProbabilityListGenerator <E>
{
	ArrayList<E> dataset;
	
	//ArrayList<E> dataList = new ArrayList<E>(); 
	ArrayList<E> repeatedDataList = new ArrayList<E>();
	//ArrayList<Integer> dataTimesRepeated = new ArrayList<Integer>();
	//ArrayList<Double> dataChanceToAppear = new ArrayList<Double>();
	
	ArrayList<E> generatedData = new ArrayList<E>();
	
	void train(ArrayList<E> newDataSet)
	{
		dataset = newDataSet;
		
		for (E data : dataset)
		{
			String dataString = data.toString();
			if (Double.parseDouble(dataString) > 0)
			{
				repeatedDataList.add(data);
			}
			
			/*int index = dataList.indexOf(data);
			
			if (index == -1)
			{
				dataList.add(data);
				dataTimesRepeated.add(1);
			}
			else
			{
				dataTimesRepeated.set(index, dataTimesRepeated.get(index) + 1);
			}*/
			//System.out.println(String.valueOf(data));
		}
		
	}
	
	void generate(int sizeOfGeneration)
	{
		//int timesRepeated = 0;
		
		//for (int i = 0; i < dataTimesRepeated.size(); i++)
		//{
			//timesRepeated += dataTimesRepeated.get(i);
		//}

		//for (int i = 0; i < dataTimesRepeated.size(); i++)
		//{
			//dataChanceToAppear.add((double) dataTimesRepeated.get(i) / timesRepeated);
		//}
		
		//System.out.println("Size: " + dataTimesRepeated.size());
		//System.out.println(timesRepeated);
		
		for (int i = 0; i < sizeOfGeneration; i++)
		{
			double randomNum = (Math.random() * (repeatedDataList.size()));
			
			//System.out.println(repeatedDataList.get((int) randomNum));
			
			generatedData.add(repeatedDataList.get((int) randomNum));
		}
	}
	
	ArrayList<E> returnGeneratedArray()
	{
		return generatedData;
	}
	
	/*void testData()
	{
		System.out.println("----------TEST DATA----------");
		for (int i = 0; i < dataList.size(); i++)
		{
			System.out.println(String.valueOf(dataList.get(i) + "   " + dataTimesRepeated.get(i)));
		}
		System.out.println("----------END  DATA----------");
	}*/
}
