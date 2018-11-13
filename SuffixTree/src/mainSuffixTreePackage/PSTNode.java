// Chris Arme
package mainSuffixTreePackage;

import java.util.ArrayList;

import javafx.scene.chart.PieChart.Data;

public class PSTNode <E>
{
	int count;
	ArrayList<E> nodeData;
	ArrayList<PSTNode> nodes;
	
	ArrayList<String> symbolsAfter;
	ArrayList<Integer> symbolsCount;
	
	PSTNode(ArrayList<E> wordArray)
	{
		count = 1;
		nodeData = wordArray;
		nodes = new ArrayList<PSTNode>();
	}
	
	//
	boolean addNode(PSTNode<E> newNode)
	{
		boolean found = nodeData.equals(newNode.getData());
		boolean isSuffix = isSuffix(newNode.getData());
		
		if ((!found && isSuffix) || nodeData.size() == 0)
		{
			int i = 0;
			while (i < nodes.size() && !found)
			{
				found = nodes.get(i).addNode(newNode);
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
	
	ArrayList<E> getData()
	{
		return nodeData;
	}
	
}
