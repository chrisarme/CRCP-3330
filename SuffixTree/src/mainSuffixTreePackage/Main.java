package mainSuffixTreePackage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	static PST pstTest = new PST();
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		String[] word = {"a", "b", "r", "a", "c", "a", "d"};
		ArrayList<String> wordArray = new ArrayList(Arrays.asList(word));
		//PSTNode<String> testNode = new PSTNode<String>(wordArray);
		
		// length
		for (int l = 0; l < 2; l++)
		{
			// position
			for (int p = 0; p < wordArray.size() - l; p++)
			{
				ArrayList<String> data = new ArrayList<String>();
				
				// create word
				for (int i = 0; i < l+1; i++)
				{
					if (l > 0)
					{
						int testttt = 0;
					}
					data.add(wordArray.get(p + i));
				}
				
				ArrayList<String> testA = new ArrayList<String>();
				testA.add("a");
				
				if (data.equals(testA))
				{
					int testsdgsg = 0;
				}
				
				pstTest.addData(new PSTNode<String>(data));
			}
		}
		
		int test = 1;
	}

}
