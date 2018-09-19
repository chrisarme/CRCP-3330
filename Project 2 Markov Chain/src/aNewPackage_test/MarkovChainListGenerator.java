// Chris Arme
// Jeeeeez, this was rollercoaster. However, once I got to the end, everything fit nicely into place as far as I can tell.

package aNewPackage_test;

import java.util.ArrayList;

public class MarkovChainListGenerator <E>
{
	ArrayList<E> dataset;
	
	ArrayList<E> dataList = new ArrayList<E>(); 
	ArrayList<E> correctedDataList = new ArrayList<E>();
	ArrayList<Integer> dataTimesRepeated = new ArrayList<Integer>();
	
	// Transition Table
	Integer[][] dataTimesRepeatedArray = new Integer[0][0];
	
	
	Float[][] dataChanceToAppear = new Float[0][0];
	ArrayList<Double> dataSumToAppear = new ArrayList<Double>();
	Float[][] dataSumToAppearArray = new Float[0][0];
	
	ArrayList<E> generatedData = new ArrayList<E>();
	
	public void clearData()
	{
		dataList = new ArrayList<E>(); 
		correctedDataList = new ArrayList<E>();
		dataTimesRepeated = new ArrayList<Integer>();
		dataChanceToAppear = new Float[0][0];
		dataTimesRepeatedArray = new Integer[0][0];
		dataSumToAppear = new ArrayList<Double>();
		dataSumToAppearArray = new Float[0][0];
		
		generatedData = new ArrayList<E>();
	}
	
	public void train(ArrayList<E> newDataSet)
	{
		/*
		 # The statespace
		states = ["Sleep","Icecream","Run"]

		# Possible sequences of events
		transitionName = [["SS","SR","SI"],["RS","RR","RI"],["IS","IR","II"]]
		
		# Probabilities matrix (transition matrix)
		transitionMatrix = [[0.2,0.6,0.2],[0.1,0.6,0.3],[0.2,0.7,0.1]]
		 */
		dataset = newDataSet;
		
		//System.out.println(dataset.size());
		
		for (E data : dataset)
		{
			String dataString = data.toString();
			
			if (Double.parseDouble(dataString) > 0)
			{
				correctedDataList.add(data);
				
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
		
		dataChanceToAppear = new Float[dataList.size()][dataList.size()];
		dataTimesRepeatedArray = new Integer[dataList.size()][dataList.size()];
		dataSumToAppearArray = new Float[dataList.size()][dataList.size()];
		
		for (int i = 0; i < dataList.size(); i++)
		{
			for (int j = 0; j < dataList.size(); j++)
			{
				dataChanceToAppear[i][j] = 0f;
				dataTimesRepeatedArray[i][j] = 0;
				dataSumToAppearArray[i][j] = 0f;
			}
		}
		
		// Fill out dataTimesRepeatedArray
		for (int i = 0; i < correctedDataList.size() - 1; i++)
		{
			E currentData = correctedDataList.get(i);
			E nextData = correctedDataList.get(i + 1);
			
			int currentIndex = dataList.indexOf(currentData);
			int nextIndex = dataList.indexOf(nextData);
			
			dataTimesRepeatedArray[currentIndex][nextIndex] = dataTimesRepeatedArray[currentIndex][nextIndex] + 1;
		}
		
		// create the total amount of data points in an array
		Integer[] dataPointTotal = new Integer[dataList.size()];
		
		for (int i = 0; i < dataPointTotal.length; i++)
		{
			int dataTotal = 0;
			
			for (int j = 0; j < dataPointTotal.length; j++)
			{
				dataTotal += dataTimesRepeatedArray[i][j];
			}
			
			dataPointTotal[i] = dataTotal;
		}
		
		
		// dataChanceToAppear creation
		for (int i = 0; i < dataChanceToAppear.length; i++)
		{
			float lastSum = 0;
			
			for (int j = 0; j < dataChanceToAppear[i].length; j++)
			{
				if (dataPointTotal[i] > 0)
				{
					dataChanceToAppear[i][j] = ( (float) dataTimesRepeatedArray[i][j] / (float) dataPointTotal[i]);
					
					if (dataChanceToAppear[i][j] != 0f)
					{
						dataSumToAppearArray[i][j] = dataChanceToAppear[i][j] + lastSum;
						lastSum += dataChanceToAppear[i][j];
					}
					else
					{
						dataSumToAppearArray[i][j] = -1f;
					}
				}
				else
				{
					dataSumToAppearArray[i][j] = -1f;
				}
			}
		}
		
		//int test = 0;
		// dataSumToAppearArray creation
		/*for (int i = 0; i < dataChanceToAppear.length; i++)
		{
			for (int j = 0; j < dataChanceToAppear[i].length; j++)
			{
				if (1 == 1)
				{
					//dataChanceToAppear[i][j] = ( (float) dataTimesRepeatedArray[i][j] / (float) dataPointTotal[i]);
				}
			}
		}*/
			
		/*for (int i = 0; i < dataChanceToAppear.size(); i++)
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
		}*/

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
			if (i != 0)
			{
				double randomNum = Math.random();	
				boolean createdData = false;
				int j = 0;
				
				int index = dataList.indexOf(generatedData.get(i-1));
				
				while (j < dataSumToAppearArray[index].length && createdData == false)
				{	
					if ((randomNum <= dataSumToAppearArray[index][j] && dataSumToAppearArray[index][j] != -1f) || dataSumToAppearArray[index][j] == 1f)
					{
						generatedData.add(dataList.get(j));
						//System.out.println("Stopping here");
						createdData = true;
					}
	
					/*if (j == dataSumToAppear.size() - 2)
					{
						generatedData.add(correctedDataList.get(correctedDataList.size() - 1));
						//System.out.println("Last Data");
						createdData = true;
					}*/
					
					j++;
				}
				
				// This should only happen if the note being check is the last note in the original song. For now, we will add the first note to start the generation again
				if (createdData == false)
				{
					generatedData.add(dataList.get(0));
					//System.out.println("Stopping here");
					createdData = true;
				}
			}
			else
			{
				generatedData.add(dataset.get(0));
				//System.out.println("First data!");
			}
		}
	}
	
	public ArrayList<E> returnGeneratedArray()
	{
		return generatedData;
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
	
	public void printTransitionTable()
	{
		// The data on the top is the before, while the data on the side is the after.
		
		/*
		 * 		   62 64
		 * 		62|	0 1					This means that there is 1 instance of there being a 62 after a 64 
		 * 		64|	2 0					and there are 2 instances of 64 after a 62
		 * 
		 */
		
		System.out.println("-----UNIT TEST DATA START-----");
		System.out.println("-------TRANSITION TABLE-------");
		
		for (int i = 0; i < dataTimesRepeatedArray.length; i++)
		{
			if (i == 0)
			{
				System.out.print("   ");
			}
			
			System.out.print(dataList.get(i) + " ");
		}
		
		System.out.println("");
		
		for (int i = 0; i < dataTimesRepeatedArray.length; i++)
		{
			for (int j = 0; j < dataTimesRepeatedArray[i].length; j++)
			{
					if (j == 0)
					{
						System.out.print(dataList.get(i) + "|");
					}
					
					System.out.print(dataChanceToAppear[i][j] + " ");

				
			}
			
			System.out.println("");
		}
		
		System.out.println('\n' + "------UNIT TEST DATA END------");
		}
	}
	
