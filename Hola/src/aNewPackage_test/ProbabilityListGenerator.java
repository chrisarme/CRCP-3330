package aNewPackage_test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

public class ProbabilityListGenerator <E>
{
	ArrayList<E> dataset;
	
	ArrayList<E> dataList = new ArrayList<E>(); 
	ArrayList<Integer> dataTimesRepeated = new ArrayList<Integer>();
	
	ArrayList<E> generatedData;
	
	void train(ArrayList<E> newDataSet)
	{
		dataset = newDataSet;
		
		for (E data : dataset)
		{

			int index = dataList.indexOf(data);
			
			if (index == -1)
			{
				dataList.add(data);
				dataTimesRepeated.add(1);
			}
			else
			{
				dataTimesRepeated.set(index, dataTimesRepeated.get(index) + 1);
			}
			//System.out.println(String.valueOf(data));
		}
		
	}
	
	void generate()
	{
		for (int i = 0; i < 100; i++)
		{
			
		}
		
	}
	
	void testData()
	{
		System.out.println("----------TEST DATA----------");
		for (int i = 0; i < dataList.size(); i++)
		{
			System.out.println(String.valueOf(dataList.get(i) + "   " + dataTimesRepeated.get(i)));
		}
		System.out.println("----------END  DATA----------");
	}
}
