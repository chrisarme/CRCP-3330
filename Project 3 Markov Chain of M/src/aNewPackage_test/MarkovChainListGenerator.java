// Chris Arme
// Jeeeeez, this was rollercoaster. However, once I got to the end, everything fit nicely into place as far as I can tell.

package aNewPackage_test;

import java.util.ArrayList;

public class MarkovChainListGenerator <E>
{
	int order;
	
	ArrayList<E> dataset;
	
	ArrayList<E> dataList = new ArrayList<E>(); 
	ArrayList<ArrayList<E>> dataListOfArrays = new ArrayList<ArrayList<E>>();
	ArrayList<E> correctedDataList = new ArrayList<E>();
	ArrayList<ArrayList<E>> correctedDataArrayList = new ArrayList<ArrayList<E>>();
	ArrayList<Integer> dataTimesRepeated = new ArrayList<Integer>();
	ArrayList<Integer> dataArrayTimesRepeated = new ArrayList<Integer>();
	
	// Transition Table
	Integer[][] dataTimesRepeatedArray = new Integer[0][0];

	Float[][] dataChanceToAppear = new Float[0][0];
	ArrayList<Double> dataSumToAppear = new ArrayList<Double>();
	Float[][] dataSumToAppearArray = new Float[0][0];
	
	Float[] singleDataChanceToAppear = new Float[0];
	Float[] singleDataSumToAppear = new Float[0];
	
	ArrayList<E> generatedData = new ArrayList<E>();
	
	public MarkovChainListGenerator()
	{
		order = 1;
	}
	
	public MarkovChainListGenerator(int m)
	{
		order = m;
	}
	
	public void changeOrder(int m)
	{
		order = m;
	}
	
	public void clearData()
	{
		dataList = new ArrayList<E>();
		dataListOfArrays = new ArrayList<ArrayList<E>>();
		
		correctedDataList = new ArrayList<E>();
		correctedDataArrayList = new ArrayList<ArrayList<E>>();
		dataTimesRepeated = new ArrayList<Integer>();
		dataArrayTimesRepeated = new ArrayList<Integer>();
		dataChanceToAppear = new Float[0][0];
		dataTimesRepeatedArray = new Integer[0][0];
		dataSumToAppear = new ArrayList<Double>();
		dataSumToAppearArray = new Float[0][0];
		
		singleDataChanceToAppear = new Float[0];
		singleDataSumToAppear = new Float[0];
		
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
		
		for (int i = 0; i < dataset.size(); i++)
		{
			E data = dataset.get(i);
			//String dataString = data.toString();
			
			//cooking data
			//if (Double.parseDouble(dataString) > 0)
			//{
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
		//}
		
		//cooking mo' data
		for (int i = order; i < correctedDataList.size(); i++)
		{
			ArrayList<E> newArray = new ArrayList<E>();
			
			for (int m = order; m > 0; m--)
			{
				newArray.add(correctedDataList.get(i-m));
			}
			
			correctedDataArrayList.add(newArray);
			
			int index = dataListOfArrays.indexOf(newArray);
			
			if (index == -1)
			{
				dataListOfArrays.add(newArray);
				dataArrayTimesRepeated.add(1);
			}
			else
			{
				dataArrayTimesRepeated.set(index, dataArrayTimesRepeated.get(index) + 1);
			}
		}
		
		//final data
		
		ArrayList<E> newArray = new ArrayList<E>();
		
		for (int m = order; m > 0; m--)
		{
			newArray.add(correctedDataList.get(correctedDataList.size()-m));
		}
		
		correctedDataArrayList.add(newArray);
		
		int index = dataListOfArrays.indexOf(newArray);
		
		if (index == -1)
		{
			dataListOfArrays.add(newArray);
			dataArrayTimesRepeated.add(1);
		}
		else
		{
			dataArrayTimesRepeated.set(index, dataArrayTimesRepeated.get(index) + 1);
		}
		
		//we are actually training
		dataChanceToAppear = new Float[dataListOfArrays.size()][dataList.size()];
		dataTimesRepeatedArray = new Integer[dataListOfArrays.size()][dataList.size()];
		dataSumToAppearArray = new Float[dataListOfArrays.size()][dataList.size()];
		
		singleDataChanceToAppear = new Float[dataList.size()];
		singleDataSumToAppear = new Float[dataList.size()];
		
		for (int i = 0; i < dataListOfArrays.size(); i++)
		{
			for (int j = 0; j < dataList.size(); j++)
			{
				dataChanceToAppear[i][j] = 0f;
				dataTimesRepeatedArray[i][j] = 0;
				dataSumToAppearArray[i][j] = 0f;
				
				singleDataChanceToAppear[j] = 0f;
				singleDataSumToAppear[j] = 0f;
			}
		}
		
		// Fill out dataTimesRepeatedArray
		/*for (int i = 0; i < correctedDataList.size() - 1; i++)
		{
			E currentData = correctedDataList.get(i);
			E nextData = correctedDataList.get(i + 1);
			
			int currentIndex = dataList.indexOf(currentData);
			int nextIndex = dataList.indexOf(nextData);
			
			dataTimesRepeatedArray[currentIndex][nextIndex] = dataTimesRepeatedArray[currentIndex][nextIndex] + 1;
		}*/
		
		// Fill out dataTimesRepeatedArray
		for (int i = 0; i < correctedDataArrayList.size() - order; i++)
		{
			//for (int j = order; j < correctedDataList.size(); j++)
			//{
				ArrayList<E> arrayData = correctedDataArrayList.get(i);
if (i == 39)
{
	int test = 9;
}
				E currentData = correctedDataList.get(i + order);
				
				// previous index
				int arrayIndex = dataListOfArrays.indexOf(arrayData);
				
				// current index
				int currentIndex = dataList.indexOf(currentData);
				
				if(i == 52) {
					int test = 1;}
				
				dataTimesRepeatedArray[arrayIndex][currentIndex] = dataTimesRepeatedArray[arrayIndex][currentIndex] + 1;
			//}
		}
		
		// create the total amount of data points in an array
		Integer[] dataPointTotal = new Integer[dataTimesRepeatedArray.length];
		Integer singleDataPointTotal = 0;
		
		for (int i = 0; i < dataTimesRepeatedArray.length; i++)
		{
			int dataTotal = 0;
			int singleDataTotal = 0;
			
			for (int j = 0; j < dataList.size(); j++)
			{
				dataTotal += dataTimesRepeatedArray[i][j];
				singleDataTotal += dataTimesRepeated.get(j);
			}
			
			dataPointTotal[i] = dataTotal;
			singleDataPointTotal = singleDataTotal;
		}
		
		
		// dataChanceToAppear creation
		for (int i = 0; i < dataChanceToAppear.length; i++)
		{
			float lastSum = 0;
			float singleLastSum = 0;
			
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
		
		float singleLastSum = 0;
		
		for (int i = 0; i < singleDataChanceToAppear.length; i++)
		{
			singleDataChanceToAppear[i] = ((float) dataTimesRepeated.get(i) / (float) singleDataPointTotal);
			singleDataSumToAppear[i] = singleDataChanceToAppear[i] + singleLastSum;
			singleLastSum += singleDataChanceToAppear[i];
		}
	
		int test = 0;
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
		
		for (int i = order; i < sizeOfGeneration; i++)
		{
			ArrayList<E> generation = new ArrayList<E>();
			
			if (i != order)
			{
				double randomNum = Math.random();	
				boolean createdData = false;
				int j = 0;
				
				for (int m = order; m >= 0; m--)
				{
					generation.add(generatedData.get(generatedData.size() - 1 - m));
				}
				
				int index = dataListOfArrays.indexOf(generation);
				
				if (index != -1)
				{
					while (j < dataSumToAppearArray[index].length && createdData == false)
					{	
						if ((randomNum <= dataSumToAppearArray[index][j] && dataSumToAppearArray[index][j] != -1f) || dataSumToAppearArray[index][j] == 1f)
						{
							generatedData.add(dataList.get(j));
							//System.out.println("Stopping here");
							createdData = true;
						}
						/*else if (index == -1)
						{
							if ((randomNum <= dataSumToAppearArray[index][j]) || dataSumToAppearArray[index][j] == 1f)
							{
								
							}
						}*/
		
						/*if (j == dataSumToAppear.size() - 2)
						{
							generatedData.add(correctedDataList.get(correctedDataList.size() - 1));
							//System.out.println("Last Data");
							createdData = true;
						}*/
						
						j++;
					}
				}
				else
				{
					while (j < singleDataSumToAppear.length && createdData == false)
					{
						if (randomNum <= singleDataSumToAppear[j] || singleDataSumToAppear[j] == 1f)
						{
							generatedData.add(dataList.get(j));
							createdData = true;
						}
						
						j++;
					}
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
				for (int m = 0; m <= order; m++)
				{
					generatedData.add(dataset.get(m));
				}
				//System.out.println("First data!");
			}
		}
		int test = 0;
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
		
		for (int i = 0; i < dataList.size(); i++)
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
						for (int m = 0; m < dataListOfArrays.get(i).size(); m++)
						{
							System.out.print("[" + dataListOfArrays.get(i).get(m) + "]");
						}
						System.out.print(" | ");
					}
					
					System.out.print(dataChanceToAppear[i][j] + " ");
			}
			
			System.out.println("");
		}
		
		System.out.println('\n' + "------UNIT TEST DATA END------");
		}
	}
	
