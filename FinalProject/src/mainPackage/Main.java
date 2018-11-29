package mainPackage;

import processing.core.*;
import com.github.jreddit.*;
import mainPackage.MarkovChainListGenerator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main extends PApplet
{

	ArrayList<ArrayList<String>> wordData = new ArrayList<ArrayList<String>>();
	
	MarkovChainListGenerator markovGenerator = new MarkovChainListGenerator(1);
	
	ArrayList<String> generatedMarkovText = new ArrayList<String>();
	ArrayList<Float> textYPos = new ArrayList<Float>();
	ArrayList<Float> textXPos = new ArrayList<Float>();
	ArrayList<Boolean> textDirection = new ArrayList<Boolean>();
	ArrayList<Float> textSpeed = new ArrayList<Float>();
	ArrayList<Float> textSize = new ArrayList<Float>();
	
	public static void main(String[] args) 
	{
		PApplet.main("mainPackage.Main");
	}
	
	public void settings()
	{
		size(600, 600);
	}
	
	public void setup()
	{
		background(100);
		
		loadNovel("../data/StressDoc.txt");
		
		for (int i = 0; i < 10; i++)
		{
			markovGenerator.generate(50);
			generatedMarkovText.add(returnMarkovString());
			textYPos.add((float) (Math.random() * height));
			textSpeed.add((float) (Math.random() * 3));
			textSize.add((float) ((Math.random() * 30) + 10));
			
			double randNum = Math.random();
			
			// set the direction that the text will move
			if (randNum > .5)
			{
				textDirection.add(true);
			}
			else
			{
				textDirection.add(false);
			}
			
			// sets base position of the text based on the direction of movement
			if (textDirection.get(i) == true)
			{
				//textXPos.add((int) (300 + (Math.random() * 50) * Math.sin(Math.random() * Math.PI)));
				textXPos.add((float) (-600 + (Math.random() * 100) * Math.sin(Math.random() * Math.PI)));
			}
			else
			{
				textXPos.add((float) (600 + (Math.random() * 100) * Math.sin(Math.random() * Math.PI)));
			}
		}
		
		for (int i = 0; i < generatedMarkovText.size(); i++)
		{
			textSize(textSize.get(i));
			text(generatedMarkovText.get(i), textXPos.get(i), textYPos.get(i));
		}
	}
	
	public void update()
	{
		for (int i = 0; i < generatedMarkovText.size(); i++)
		{
			if (textDirection.get(i) == true)
			{
				textXPos.set(i, textXPos.get(i) + textSpeed.get(i));
			}
			else
			{
				textXPos.set(i, textXPos.get(i) - textSpeed.get(i));
			}
		}
	}
	
	public void draw()
	{
		background(100);
		
		for (int i = 0; i < generatedMarkovText.size(); i++)
		{
			textSize(textSize.get(i));
			text(generatedMarkovText.get(i), textXPos.get(i), textYPos.get(i));
		}
		
		update();
	}
	
	void loadNovel(String p) 
	{
		String filePath = getPath(p);
		Path path = Paths.get(filePath);
		wordData = new ArrayList<ArrayList<String>>();

		try 
		{
			List<String> lines = Files.readAllLines(path);

			for (int i = 0; i < lines.size(); i++) 
			{
				if (!lines.get(i).isEmpty() && lines.get(i) != " ")
				{
					wordData.add(new ArrayList<String>());
					
					String[] lineWords = lines.get(i).split(" ");
					for (int j = 0; j < lineWords.length; j++)
					{
						wordData.get(wordData.size() - 1).add(lineWords[j]);
					}
				}
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//println("Oopsie! We had a problem reading a file!");
		}
		
		for (int i = 0; i < wordData.size(); i++)
		{
			println(wordData.get(i));
			
			markovGenerator.train(wordData.get(i));
		}
		
		markovGenerator.generate(50);
		
		println("");
		println(markovGenerator.returnGeneratedArray());
	}
	
	String returnMarkovString()
	{
		String markovString = "";
		ArrayList<String> returnedArray = markovGenerator.returnGeneratedArray();
		
		for (int i = 0; i < returnedArray.size(); i++)
		{
			if (i < returnedArray.size() - 1)
			{
				markovString += returnedArray.get(i) + " ";
			}
			else
			{
				markovString += returnedArray.get(i);
			}
		}
		
		return markovString;
	}
	
	
	String getPath(String path) {

		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");
			filePath = filePath.substring(1, filePath.length());

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

}
