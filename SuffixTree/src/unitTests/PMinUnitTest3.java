package unitTests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

import mainSuffixTreePackage.PST;

public class PMinUnitTest3 
{

	PST<String> testPST = new PST<String>(3);
	
	public void run() 
	{
		String[] word = {"a", "b", "c", "c", "c", "d", "a", "a", "d", "c", "d", "a", "a", "b", "c", "a", "d", "a", "d"};

		System.out.println("------- pMin = .15 -------");
		testPST.addData(new ArrayList<String>(Arrays.asList(word)));
		testPST.printData();
		
		System.out.println("------- pMin = .2 -------");
		testPST = new PST<String>(3, .2f, .2f);
		testPST.addData(new ArrayList<String>(Arrays.asList(word)));
		testPST.printData();
	}
}
