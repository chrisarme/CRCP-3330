package mainSuffixTreePackage;

import java.util.ArrayList;

public class PSTNode <E>
{
	int count;
	ArrayList<E> nodeData;
	ArrayList<PSTNode> nodes;
	
	PSTNode(ArrayList<E> wordArray)
	{
		count = 1;
		nodeData = wordArray;
		nodes = new ArrayList<PSTNode>();
	}
	
	boolean addNode(PSTNode<E> newNode)
	{
		boolean found = nodeData.equals(newNode.getData());
		boolean isSuffix = isSuffix(newNode.getData());
		
		if ((!found && isSuffix) || nodeData.size() == 0)
		{
			int i = 0;
			while (i < nodes.size())
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
			E lastDataToCheck = dataToCheck.get(dataToCheck.size()-1);
			
			return nodeData.get(nodeData.size()-1).equals(lastDataToCheck);
		}
		else
		{
			return false;
		}
	}
	
	ArrayList<E> getData()
	{
		return nodeData;
	}
	
}
