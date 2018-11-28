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
	
	MarkovChainListGenerator<String> markovGenerator = new MarkovChainListGenerator<>(1);
	
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
		loadNovel("../data/StressDoc.txt");
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
