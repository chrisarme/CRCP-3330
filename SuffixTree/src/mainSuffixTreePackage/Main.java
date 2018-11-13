package mainSuffixTreePackage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import processing.core.*;
import unitTests.UnitTest1;
import unitTests.UnitTest2;
import unitTests.UnitTest3;
import unitTests.UnitTest4;

public class Main extends PApplet{

	static PST<String> pstTest = new PST<String>();
	
	// tests
	UnitTest1 unitTest1 = new UnitTest1();
	UnitTest2 unitTest2 = new UnitTest2();
	UnitTest3 unitTest3 = new UnitTest3();
	UnitTest4 unitTest4 = new UnitTest4();
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		//String[] word = {"a", "b", "r", "a", "c", "a", "d"};
		//ArrayList<String> wordArray = new ArrayList(Arrays.asList(word));

		PApplet.main("mainSuffixTreePackage.Main");
	}
	
	public void settings()
	{
		size(600, 600);
	}
	
	public void setup()
	{
		
	}
	
	public void draw()
	{
		background(150);
		
		fill(255);
		textSize(30);
		
		//text("Press Space to Start/Stop Music", (width / 2) - textWidth("Press Space to Start/Stop Music") / 2, 50);
		
		
		// setup UI
		fill(255);
		textSize(18);
		text ("Press 1 for \"abracadabra\" (Unit Test 1)", (width / 2) - textWidth("Press 1 for \"abracadabra\" (Unit Test 1)") / 2, 400);
		text ("Press 2 for \"acadaacbda\" (Unit Test 2)", (width / 2) - textWidth("Press 2 for \"acadaacbda\" (Unit Test 2)") / 2, 440);
		text ("Press 3 for \"abcccdaadcdaabcadad\" (Unit Test 3)", (width / 2) - textWidth("Press 3 for \"abcccdaadcdaabcadad\" (Unit Test 3)") / 2, 480);
		text ("Press 4 for Mary Had a Little Lamb (Unit Test 4)", (width / 2) - textWidth("Press 4 for Mary Had a Little Lamb (Unit Test 4)") / 2, 520);
	}

	public void keyPressed()
	{		
		if (key == '1')
		{
			unitTest1.run();
		}
		else if (key == '2')
		{
			unitTest2.run();
		}
		else if (key == '3')
		{
			unitTest3.run();
		}
		else if (key == '4')
		{
			unitTest4.run();
		}
	}
	
	public String getPath(String filename)
	{
		String filepath = "";
		
		try
		{
			filepath = URLDecoder.decode(getClass().getResource(filename).getPath(), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return filepath;
	}
	
}
