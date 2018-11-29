// Chris Arme
// Jeeeeez, this was rollercoaster. However, once I got to the end, everything fit nicely into place as far as I can tell.

package mainPackage;

import java.util.ArrayList;

public class MarkovChainListGenerator
{
	int order;
	
	boolean firstRun = true;
	
	ArrayList<String> dataset;
	
	ArrayList<String> dataList = new ArrayList<String>(); 
	ArrayList<ArrayList<String>> dataListOfArrays = new ArrayList<ArrayList<String>>();
	ArrayList<String> correctedDataList = new ArrayList<String>();
	ArrayList<ArrayList<String>> correctedDataArrayList = new ArrayList<ArrayList<String>>();
	ArrayList<Integer> dataTimesRepeated = new ArrayList<Integer>();
	ArrayList<Integer> dataArrayTimesRepeated = new ArrayList<Integer>();
	
	// Transition Table
	ArrayList<ArrayList<Integer>> dataTimesRepeatedArray = new ArrayList<ArrayList<Integer>>();

	ArrayList<ArrayList<Float>> dataChanceToAppear = new ArrayList<ArrayList<Float>>();
	ArrayList<Double> dataSumToAppear = new ArrayList<Double>();
	ArrayList<ArrayList<Float>> dataSumToAppearArray = new ArrayList<ArrayList<Float>>();
	
	ArrayList<Float> singleDataChanceToAppear = new ArrayList<Float>();
	ArrayList<Float> singleDataSumToAppear = new ArrayList<Float>();
	
	ArrayList<String> generatedData = new ArrayList<String>();
	
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
		dataList = new ArrayList<String>();
		dataListOfArrays = new ArrayList<ArrayList<String>>();
		
		correctedDataList = new ArrayList<String>();
		correctedDataArrayList = new ArrayList<ArrayList<String>>();
		dataTimesRepeated = new ArrayList<Integer>();
		dataArrayTimesRepeated = new ArrayList<Integer>();
		dataChanceToAppear = new ArrayList<ArrayList<Float>>();
		dataTimesRepeatedArray = new ArrayList<ArrayList<Integer>>();
		dataSumToAppear = new ArrayList<Double>();
		dataSumToAppearArray = new ArrayList<ArrayList<Float>>();
		
		singleDataChanceToAppear = new ArrayList<Float>();
		singleDataSumToAppear = new ArrayList<Float>();
		
		generatedData = new ArrayList<String>();
	}
	
	public void train(ArrayList<String> newDataSet)
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
		
		correctedDataList = new ArrayList<String>();
		correctedDataArrayList = new ArrayList<ArrayList<String>>();
		
		//System.out.println(dataset.size());
		
		prepareData();
		
		//we are actually training
	
		
		
		if (firstRun)
		{
			firstRun = false;
			
			//dataChanceToAppear = new Float[dataListOfArrays.size()][dataList.size()];
			//dataTimesRepeatedArray = new Integer[dataListOfArrays.size()][dataList.size()];
			//dataSumToAppearArray = new Float[dataListOfArrays.size()][dataList.size()];
			
			//singleDataChanceToAppear = new Float[dataList.size()];
			//singleDataSumToAppear = new Float[dataList.size()];
			
			for (int i = 0; i < dataListOfArrays.size(); i++)
			{
				dataChanceToAppear.add(new ArrayList<Float>());
				dataTimesRepeatedArray.add(new ArrayList<Integer>());
				dataSumToAppearArray.add(new ArrayList<Float>());
				
				for (int j = 0; j < dataList.size(); j++)
				{
					dataChanceToAppear.get(i).add(0f);
					dataTimesRepeatedArray.get(i).add(0);
					dataSumToAppearArray.get(i).add(0f);
					
					singleDataChanceToAppear.add(0f);
					singleDataSumToAppear.add(0f);
				}
			}
		}
		else
		{
			for (int i = 0; i < dataChanceToAppear.size(); i++)
			{		
				for (int j = dataChanceToAppear.get(i).size(); j < dataList.size(); j++)
				{
					dataChanceToAppear.get(i).add(0f);
					dataTimesRepeatedArray.get(i).add(0);
					dataSumToAppearArray.get(i).add(0f);
					
					singleDataChanceToAppear.add(0f);
					singleDataSumToAppear.add(0f);
				}
			}
			
			for (int i = dataChanceToAppear.size(); i < dataListOfArrays.size(); i++)
			{
				dataChanceToAppear.add(new ArrayList<Float>());
				dataTimesRepeatedArray.add(new ArrayList<Integer>());
				dataSumToAppearArray.add(new ArrayList<Float>());
				
				for (int j = 0; j < dataList.size(); j++)
				{
					dataChanceToAppear.get(i).add(0f);
					dataTimesRepeatedArray.get(i).add(0);
					dataSumToAppearArray.get(i).add(0f);
					
					singleDataChanceToAppear.add(0f);
					singleDataSumToAppear.add(0f);
				}
			}
		}
		
		// Fill out dataTimesRepeatedArray
		for (int i = 0; i < correctedDataArrayList.size() - order; i++)
		{
			//for (int j = order; j < correctedDataList.size(); j++)
			//{
				ArrayList<String> arrayData = correctedDataArrayList.get(i);
				
				if (i == 40 - order)
				{
					int test = 9;
				}

				String currentData = correctedDataList.get(i + order);
				
				// previous index
				int arrayIndex = dataListOfArrays.indexOf(arrayData);
				
				// current index
				int currentIndex = dataList.indexOf(currentData);

				ArrayList<Integer> test = dataTimesRepeatedArray.get(arrayIndex);
				
				dataTimesRepeatedArray.get(arrayIndex).set(currentIndex, dataTimesRepeatedArray.get(arrayIndex).get(currentIndex) + 1);
			//}
		}
		
		// create the total amount of data points in an array
		Integer[] dataPointTotal = new Integer[dataTimesRepeatedArray.size()];
		Integer singleDataPointTotal = 0;
		
		for (int i = 0; i < dataTimesRepeatedArray.size(); i++)
		{
			int dataTotal = 0;
			int singleDataTotal = 0;
			
			for (int j = 0; j < dataList.size(); j++)
			{
				dataTotal += dataTimesRepeatedArray.get(i).get(j);
				singleDataTotal += dataTimesRepeated.get(j);
			}
			
			dataPointTotal[i] = dataTotal;
			singleDataPointTotal = singleDataTotal;
		}
		
		int test = 0;
		
		// dataChanceToAppear creation
		for (int i = 0; i < dataChanceToAppear.size(); i++)
		{
			float lastSum = 0;
			float singleLastSum = 0;
			
			if (order == 2)
			{
				int test2 = 2;
			}
			
			for (int j = 0; j < dataChanceToAppear.get(i).size(); j++)
			{
				if (dataPointTotal[i] > 0)
				{
					dataChanceToAppear.get(i).set(j, ( (float) dataTimesRepeatedArray.get(i).get(j) / (float) dataPointTotal[i]));
					
					
					float number = dataChanceToAppear.get(i).get(j);
					
					if (dataChanceToAppear.get(i).get(j) != 0f)
					{
						ArrayList<Integer> ababaaba = dataTimesRepeatedArray.get(i);
						
						dataSumToAppearArray.get(i).set(j, dataChanceToAppear.get(i).get(j) + lastSum);
						lastSum += dataChanceToAppear.get(i).get(j);
					}
					else
					{
						dataSumToAppearArray.get(i).set(j, -1f);
					}
				}
				else
				{
					dataSumToAppearArray.get(i).set(j, -1f);
				}
			}
		}
		
		float singleLastSum = 0;

		/*for (int i = 0; i < singleDataChanceToAppear.length; i++)
		{
			singleDataChanceToAppear[i] = ((float) dataTimesRepeated.get(i) / (float) singleDataPointTotal);
			singleDataSumToAppear[i] = singleDataChanceToAppear[i] + singleLastSum;
			singleLastSum += singleDataChanceToAppear[i];
		}*/
	}
	
	public void generate(int sizeOfGeneration)
	{	
		generatedData = new ArrayList<String>();
		
		for (int i = order; i < sizeOfGeneration; i++)
		{
			ArrayList<String> generation = new ArrayList<String>();		
			
			if (i != order)
			{
				double randomNum = Math.random();	
				boolean createdData = false;
				int j = 0;
				
				for (int m = order; m > 0; m--)
				{
					generation.add(generatedData.get(generatedData.size() - m));
				}
				
				int index = dataListOfArrays.indexOf(generation);
				if (index != -1)
				{
					while (j < dataSumToAppearArray.get(index).size() && createdData == false)
					{	
						if ((randomNum <= dataSumToAppearArray.get(index).get(j) && dataSumToAppearArray.get(index).get(j) != -1f) || dataSumToAppearArray.get(index).get(j) == 1f)
						{
							generatedData.add(dataList.get(j));
							//System.out.println("Stopping here");
							createdData = true;
						}

						j++;
					}
				}
				else
				{
					/*while (j < singleDataSumToAppear.size() && createdData == false)
					{
						if (randomNum <= singleDataSumToAppear[j] || singleDataSumToAppear[j] == 1f)
						{
							generatedData.add(dataList.get(j));
							createdData = true;
						}
						
						j++;
					}*/
				}
				
				// This should only happen if the note being check is the last note in the original song. For now, we will add the first note to start the generation again
				if (createdData == false)
				{
					int randNum = (int) Math.ceil(Math.random() * dataList.size());
					generatedData.add(dataList.get(randNum));
					createdData = true;
				}
			}
			else
			{
				for (int m = 0; m < order; m++)
				{
					generatedData.add(dataList.get(m));
				}
			}
		}
	}
	
	// This will prepare the data for training, placing it inside multiple arrays
	private void prepareData()
	{
		for (int i = 0; i < dataset.size(); i++)
		{
			String data = dataset.get(i).toString();
			data = data.replaceAll("[^a-zA-Z0-9\\.\\,\\'\\’]", "");

			correctedDataList.add(data);
			
			int index = dataList.indexOf(data);
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
		
		//cooking mo' data
		for (int i = order; i < dataset.size(); i++)
		{
			ArrayList<String> newArray = new ArrayList<String>();
			
			for (int m = order; m > 0; m--)
			{
				newArray.add(dataset.get(i-m));
			}
			
			for (int a = 0; a < newArray.size(); a++)
			{
				newArray.set(a, newArray.get(a).replaceAll("[^a-zA-Z0-9\\.\\,\\'\\’]", ""));
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
		ArrayList<String> newArray = new ArrayList<String>();
		
		for (int m = order; m > 0; m--)
		{
			newArray.add(correctedDataList.get(correctedDataList.size()-m));
		}
		
		for (int a = 0; a < newArray.size(); a++)
		{
			newArray.set(a, newArray.get(a).replaceAll("[^a-zA-Z0-9\\.\\,\\'\\’]", ""));
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
	
	public ArrayList<String> returnGeneratedArray()
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
		
		for (int i = 0; i < dataTimesRepeatedArray.size(); i++)
		{
			for (int j = 0; j < dataTimesRepeatedArray.get(i).size(); j++)
			{
					if (j == 0)
					{
						for (int m = 0; m < dataListOfArrays.get(i).size(); m++)
						{
							System.out.print("[" + dataListOfArrays.get(i).get(m) + "]");
						}
						System.out.print(" | ");
					}
					
					System.out.print(dataChanceToAppear.get(i).get(j) + " ");
			}
			
			System.out.println("");
		}
		
		System.out.println('\n' + "------UNIT TEST DATA END------");
		}
	}
	
