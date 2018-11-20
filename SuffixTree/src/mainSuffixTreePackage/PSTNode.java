// Chris Arme
package mainSuffixTreePackage;

import java.util.ArrayList;

import javafx.scene.chart.PieChart.Data;

public class PSTNode <E>
{
	int count;
	ArrayList<E> nodeData;
	ArrayList<PSTNode<E>> nodes;
	
	ArrayList<ArrayList<E>> symbolsAfter;
	ArrayList<Integer> symbolsCount;
	
	boolean isSymbolAtEnd;
	E symbolAtEnd;
	
	PSTNode(ArrayList<E> wordArray)
	{
		count = 1;
		nodeData = wordArray;
		nodes = new ArrayList<PSTNode<E>>();
		
		symbolsAfter = new ArrayList<ArrayList<E>>();
		symbolsCount = new ArrayList<Integer>();
	}
	
	//
	boolean addNode(PSTNode<E> newNode, ArrayList <E> mainData, int positionOfCurrentData)
	{
		boolean found = nodeData.equals(newNode.getData());
		boolean isSuffix = isSuffix(newNode.getData());
		
		//newNode.symbolsAfter = symbolsAfter;
		//newNode.symbolsCount = symbolsCount;
		
		if ((!found && isSuffix) || nodeData.size() == 0)
		{
			int i = 0;
			while (i < nodes.size() && !found)
			{
				found = nodes.get(i).addNode(newNode, mainData, positionOfCurrentData);
				i++;
			}
			
			if (!found && ((newNode.getData().size() - 1) == nodeData.size()))
			{
				nodes.add(newNode);
				found = true;
			}
		}
		else if (found)
		{
			count++;
		}
		
		return found;
	}
	
	boolean isSuffix(ArrayList<E> dataToCheck)
	{
		if (nodeData.size() > 0)
		{
			//E lastDataToCheck = dataToCheck.get(dataToCheck.size()-1);
			
			ArrayList<E> theDataToCheck = new ArrayList<E>();
			
			for (int i = nodeData.size(); i > 0; i--)
			{
				theDataToCheck.add(dataToCheck.get(dataToCheck.size() - i));
			}
			
			return nodeData.equals(theDataToCheck);
		}
		else
		{
			return false;
		}
	}
	
	public void printNodes()
	{
		for (int i = 0; i < nodeData.size(); i++)
		{
			System.out.print("	");
		}
		
		if (nodeData.size() > 0)
		{
			System.out.print("-->");
		}
		
		System.out.println(nodeData);
		
		for (int i = 0; i < nodes.size(); i++)
		{
			nodes.get(i).printNodes();
		}
	}
	
	boolean pminEliminate(float pMin, int posToOccur)
	{
		boolean shouldEliminate = false;
		
		if (nodeData.size() > 0)
		{
			if (nodeData.get(nodeData.size() - 1) == "a")
			{
				int test = 0;
			}
		}
		
		float pMinCheck = (float)count / (float)posToOccur;
		
		if (pMinCheck <= pMin)
		{
			shouldEliminate = true;
		}
		
		if (shouldEliminate == false || nodeData.size() <= 1)
		{
			for (int i = nodes.size() - 1; i >= 0; i--)
			{
				boolean eliminateChild = nodes.get(i).pminEliminate(pMin, posToOccur - 1);
				
				if (eliminateChild == true)
				{
					nodes.remove(i);
				}
			}
		}
		
		return shouldEliminate;
	}
	
	boolean rEliminate(float r)
	{
		boolean shouldEliminate = false;
		
		if (nodeData.size() > 0)
		{
			if (nodeData.get(nodeData.size() - 1) == "a")
			{
				int test = 0;
			}
		}
		
		// FIX
		float rCheck = (float)count / 1;
		
		if (rCheck <= r)
		{
			shouldEliminate = false;
		}
		
		if (shouldEliminate == false || nodeData.size() <= 1)
		{
			for (int i = nodes.size() - 1; i >= 0; i--)
			{
				boolean eliminateChild = nodes.get(i).rEliminate(123 - 1);
				
				if (eliminateChild == true)
				{
					nodes.remove(i);
				}
			}
		}
		
		return shouldEliminate;
	}
	
	void addToSymbols(ArrayList<E> symbolData)
	{
		if (isSuffix(symbolData))
		{
			
		}
	}
	
	ArrayList<E> getData()
	{
		return nodeData;
	}
	
}
