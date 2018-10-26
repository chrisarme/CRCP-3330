package mainSuffixTreePackage;

import java.util.ArrayList;

public class PST 
{
	PSTNode root;
	
	PST()
	{
		root = new PSTNode<String>(new ArrayList<>());
	}
	
	void addData(PSTNode newNode)
	{
		root.addNode(newNode);
	}
}
