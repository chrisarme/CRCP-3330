package unitTests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

import mainSuffixTreePackage.PST;

public class UnitTest1 
{

	PST<String> testPST = new PST<String>(3);
	
	public void run() 
	{
		String[] word = {"a", "b", "r", "a", "c", "a", "d", "a", "b", "r", "a"};
		testPST.addData(new ArrayList<String>(Arrays.asList(word)));
		testPST.printData();
	}
}
