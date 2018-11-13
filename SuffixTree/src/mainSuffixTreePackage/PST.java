package mainSuffixTreePackage;

import java.util.ArrayList;

public class PST <E>
{
	PSTNode<E> root;
	int length;
	double pMin = .15;
	
	ArrayList<String> symbolsFound;
	ArrayList<Integer> symbolsCount;
	
	public PST()
	{
		root = new PSTNode<E>(new ArrayList<>());
		length = 1;
	}
	
	public PST(int l)
	{
		root = new PSTNode<E>(new ArrayList<>());
		length = l;
	}
	
	public void addData(ArrayList<E> array)
	{
		for (int l = 0; l < length; l++)
		{
			// position
			for (int p = 0; p < array.size() - l; p++)
			{
				ArrayList<E> data = new ArrayList<E>();
				
				// create word
				for (int i = 0; i < l+1; i++)
				{
					data.add(array.get(p + i));
				}
				
				root.addNode(new PSTNode<E>(data));
			}
		}
		
		beginPminEliminate(array.size());
	}
	
	void beginPminEliminate(int dataSize)
	{
		root.pminEliminate(pMin, dataSize);
	}
	
	public void printData()
	{
		root.printNodes();
	}
}
