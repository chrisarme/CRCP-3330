package mainPackage;

import processing.core.*;
import com.github.jreddit.*;
import mainPackage.MarkovChainListGenerator;

import java.io.IOException;
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
	ArrayList<ArrayList<String>> seperatedText = new ArrayList<ArrayList<String>>();
	ArrayList<Float> textYPos = new ArrayList<Float>();
	ArrayList<ArrayList<Float>> textXPos = new ArrayList<ArrayList<Float>>();
	ArrayList<Boolean> textDirection = new ArrayList<Boolean>();
	ArrayList<Float> textSpeed = new ArrayList<Float>();
	ArrayList<Float> textSize = new ArrayList<Float>();
	ArrayList<Float[]> stressColor = new ArrayList<Float[]>();
	ArrayList<Integer> stopState = new ArrayList<Integer>();
	ArrayList<Long> timeOfStop = new ArrayList<Long>();
	
	ArrayList<String> stressLines = new ArrayList<String>();
	
	public static void main(String[] args) 
	{
		PApplet.main("mainPackage.Main");
	}
	
	public void settings()
	{
		//size(600, 600);
		fullScreen();
		//textAlign(LEFT);
	}
	
	public void setup()
	{
		background(0);
		
		loadNovel("../data/StressDoc.txt");
		
		stressLines = loadWordsOnly("../data/StressWords.txt");
		
		for (int i = 0; i < 15; i++)
		{
			markovGenerator.generate(50);
			generatedMarkovText.add(returnMarkovString());
			seperatedText.add(returnSeperatedStrings(generatedMarkovText.get(i)));
			textXPos.add(new ArrayList<Float>());
			textYPos.add((float) (Math.random() * height));
			textSpeed.add((float) (Math.random() * 2) + .5f);
			textSize.add((float) ((Math.random() * 30) + 10));
			
			stopState.add(0);
			timeOfStop.add((long) 0);
			
			Float[] colorArray = new Float[3];
			colorArray[0] = (float) ((Math.random() * 255));
			colorArray[1] = (float) ((Math.random() * 255));
			colorArray[2] = (float) ((Math.random() * 255));
			
			
			stressColor.add(colorArray);
			
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
				for (int j = 0; j < seperatedText.get(i).size(); j++)
				{
					if (j % 2 == 0)
					{
						textSize(textSize.get(i));
					}
					else
					{
						textSize(textSize.get(i) + 20);
					}
					//textXPos.add(new ArrayList<Float>());
					if (j == 0)
					{
						//textXPos.get(i).add(200 - textWidth(seperatedText.get(i).get(j)));
						textXPos.get(i).add(-200f);
					}
					else
					{
						
						float prevTextWidth;// = textXPos.get(i).get(j - 1);
						
						if (j % 2 == 0)
						{
							textSize(textSize.get(i) + 20);
							prevTextWidth = textWidth(seperatedText.get(i).get(j - 1));
						}
						else
						{
							textSize(textSize.get(i));
							prevTextWidth = textWidth(seperatedText.get(i).get(j - 1));
						}
						//float test2 = textWidth(seperatedText.get(i).get(j - 1));
						textXPos.get(i).add(textXPos.get(i).get(j - 1) + prevTextWidth);
					}
				}
			}
			else
			{
				//textXPos.add((float) (600 + (Math.random() * 100) * Math.sin(Math.random() * Math.PI)));
				
				for (int j = 0; j < seperatedText.get(i).size(); j++)
				{
					if (j % 2 == 0)
					{
						textSize(textSize.get(i));
					}
					else
					{
						textSize(textSize.get(i) + 20);
					}
					//textXPos.add(new ArrayList<Float>());
					if (j == 0)
					{
						//textXPos.get(i).add(200 - textWidth(seperatedText.get(i).get(j)));
						textXPos.get(i).add(500f);
					}
					else
					{
						float prevTextWidth;// = textXPos.get(i).get(j - 1);
						
						if (j % 2 == 0)
						{
							textSize(textSize.get(i) + 20);
							prevTextWidth = textWidth(seperatedText.get(i).get(j - 1));
						}
						else
						{
							textSize(textSize.get(i));
							prevTextWidth = textWidth(seperatedText.get(i).get(j - 1));
						}
						//float test2 = textWidth(seperatedText.get(i).get(j - 1));
						textXPos.get(i).add(textXPos.get(i).get(j - 1) + prevTextWidth);
					}
				}
			}
			
			if (textXPos.get(i).size() == 0)
			{
				int test = 0;
			}
		}
		
		for (int i = 0; i < seperatedText.size(); i++)
		{
			textSize(textSize.get(i));

			for (int j = 0; j < seperatedText.get(i).size(); j++)
			{
				if (j % 2 == 0)
				{
					textSize(textSize.get(i));
					fill(255);
				}
				else
				{
					textSize(textSize.get(i) + 20);
					fill(175, 0, 0);
				}
				
				text(seperatedText.get(i).get(j), textXPos.get(i).get(j), textYPos.get(i));
			}
		}
	}
	
	public void update()
	{
		for (int i = 0; i < generatedMarkovText.size(); i++)
		{
			double randNum = Math.random();
			
			if (textDirection.get(i) == true)
			{	
				if (stopState.get(i) == 0)
				{
					if (randNum > .999)
					{
						stopState.set(i, 1);
						timeOfStop.set(i, System.currentTimeMillis());
					}
					else
					{
						for (int j = 0; j < textXPos.get(i).size(); j++)
						{
							textXPos.get(i).set(j, textXPos.get(i).get(j) + textSpeed.get(i));
						}
					}
				}
				else if (stopState.get(i) == 1)
				{
					if (timeOfStop.get(i) + (3000) < System.currentTimeMillis())
					{
						stopState.set(i, 2);
					}
				}
				else
				{
					for (int j = 0; j < textXPos.get(i).size(); j++)
					{
						textXPos.get(i).set(j, textXPos.get(i).get(j) + textSpeed.get(i));
					}
				}
				
				if (textXPos.get(i).get(0) > width)
				{
					float prevTextSize = textSize.get(i);
					
					textSize.set(i, (float) ((Math.random() * 30) + 10));
					stopState.set(i, 0);
					
					Float[] colorArray = new Float[3];
					colorArray[0] = (float) ((Math.random() * 255));
					colorArray[1] = (float) ((Math.random() * 255));
					colorArray[2] = (float) ((Math.random() * 255));
					
					
					stressColor.set(i, colorArray);
					
					markovGenerator.generate(50);
					generatedMarkovText.set(i, returnMarkovString());
					seperatedText.set(i, returnSeperatedStrings(generatedMarkovText.get(i)));
					textYPos.set(i, (float) (Math.random() * height));
					textSpeed.set(i, (float) (Math.random() * 2) + .5f);
					
					textXPos.set(i, new ArrayList<Float>());
					
					for (int j = 0; j < seperatedText.get(i).size(); j++)
					{
						if (j % 2 == 0)
						{
							textSize(textSize.get(i));
						}
						else
						{
							textSize(textSize.get(i) + 20);
						}
						//textXPos.add(new ArrayList<Float>());
						if (j == 0)
						{
							//textXPos.get(i).add(200 - textWidth(seperatedText.get(i).get(j)));
							textSize(prevTextSize + 20);
							textXPos.get(i).add(-50 - textWidth(generatedMarkovText.get(i)));
						}
						else
						{
							
							float prevTextWidth;// = textXPos.get(i).get(j - 1);
							
							if (j % 2 == 0)
							{
								textSize(textSize.get(i) + 20);
								prevTextWidth = textWidth(seperatedText.get(i).get(j - 1));
							}
							else
							{
								textSize(textSize.get(i));
								prevTextWidth = textWidth(seperatedText.get(i).get(j - 1));
							}
							//float test2 = textWidth(seperatedText.get(i).get(j - 1));
							textXPos.get(i).add(textXPos.get(i).get(j - 1) + prevTextWidth);
						}
					}

				}
			}
			else
			{
				/*textSize(textSize.get(i));
				textXPos.set(i, textXPos.get(i) - textSpeed.get(i));
				if (textXPos.get(i) < -textWidth(generatedMarkovText.get(i)))
				{
					textSize.set(i, (float) ((Math.random() * 30) + 10));
					textSize(textSize.get(i));
					
					markovGenerator.generate(50);
					generatedMarkovText.set(i, returnMarkovString());
					textYPos.set(i, (float) (Math.random() * height));
					textXPos.set(i, (float) width + 50);
					textSpeed.set(i, (float) (Math.random() * 2) + .5f);
				}*/
				
				textSize(textSize.get(seperatedText.get(i).size() - 1));
				
				if (stopState.get(i) == 0)
				{
					if (randNum > .999)
					{
						stopState.set(i, 1);
						timeOfStop.set(i, System.currentTimeMillis());
					}
					else
					{
						for (int j = 0; j < textXPos.get(i).size(); j++)
						{
							textXPos.get(i).set(j, textXPos.get(i).get(j) - textSpeed.get(i));
						}
					}
				}
				else if (stopState.get(i) == 1)
				{
					if (timeOfStop.get(i) + (3000) < System.currentTimeMillis())
					{
						stopState.set(i, 2);
					}
				}
				else
				{
					for (int j = 0; j < textXPos.get(i).size(); j++)
					{
						textXPos.get(i).set(j, textXPos.get(i).get(j) - textSpeed.get(i));
					}
				}
				
				if (textXPos.get(i).size() > 0)
				{
					float prevTextSize = textSize.get(i);
					textSize(textSize.get(textSize.size() - 1) + 10);
					
					if ((textXPos.get(i).get(textXPos.get(i).size() - 1) + textWidth(seperatedText.get(i).get(seperatedText.get(i).size() - 1))) < 0)
					{
						textSize.set(i, (float) ((Math.random() * 30) + 10));	
						stopState.set(i, 0);
						
						Float[] colorArray = new Float[3];
						colorArray[0] = (float) ((Math.random() * 255));
						colorArray[1] = (float) ((Math.random() * 255));
						colorArray[2] = (float) ((Math.random() * 255));
						
						
						stressColor.set(i, colorArray);
						
						markovGenerator.generate(50);
						generatedMarkovText.set(i, returnMarkovString());
						seperatedText.set(i, returnSeperatedStrings(generatedMarkovText.get(i)));
						textYPos.set(i, (float) (Math.random() * height));
						
						textXPos.set(i, new ArrayList<Float>());
						
						for (int j = 0; j < seperatedText.get(i).size(); j++)
						{
							/*if (j % 2 == 0)
							{
								textSize(textSize.get(i));
							}
							else
							{
								textSize(textSize.get(i) + 20);
							}*/
							//textXPos.add(new ArrayList<Float>());
							if (j == 0)
							{
								//textXPos.get(i).add(200 - textWidth(seperatedText.get(i).get(j)));
								textXPos.get(i).add(width + 50f);
							}
							else
							{
								float prevTextWidth;// = textXPos.get(i).get(j - 1);
								
								if (j % 2 == 0)
								{
									textSize(textSize.get(i) + 20);
									prevTextWidth = textWidth(seperatedText.get(i).get(j - 1));
								}
								else
								{
									textSize(textSize.get(i));
									prevTextWidth = textWidth(seperatedText.get(i).get(j - 1));
								}
								//float test2 = textWidth(seperatedText.get(i).get(j - 1));
								textXPos.get(i).add(textXPos.get(i).get(j - 1) + prevTextWidth);
							}
						}
						
						textSpeed.set(i, (float) (Math.random() * 2) + .5f);
					}
				}
			}
		}
	}
	
	public void draw()
	{
		background(0);
		
		for (int i = 0; i < seperatedText.size(); i++)
		{
			//textSize(textSize.get(i));
			
			for (int j = 0; j < seperatedText.get(i).size(); j++)
			{
				if (j % 2 == 0)
				{
					textSize(textSize.get(i));
					fill(255);
				}
				else
				{
					textSize(textSize.get(i) + 20);
					fill(stressColor.get(i)[0], stressColor.get(i)[1], stressColor.get(i)[2]);
				}
				
				text(seperatedText.get(i).get(j), textXPos.get(i).get(j), textYPos.get(i));
			}
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
	
	ArrayList<String> loadWordsOnly(String p) 
	{
		String filePath = getPath(p);
		Path path = Paths.get(filePath);
		wordData = new ArrayList<ArrayList<String>>();

			ArrayList<String> lines = new ArrayList<String>();
			try {
				lines.addAll(Files.readAllLines(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lines;

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
		
		int lastDot = markovString.lastIndexOf('.');
		if (lastDot != -1 && lastDot != markovString.length())
		{
			String correctedString = markovString.substring(0, lastDot + 1);
			return correctedString;
		}
		else
		{
			return markovString;
		}
	}
	
	
	// returns ArrayList of the string separated by stress words 
	ArrayList<String> returnSeperatedStrings(String baseText)
	{
		ArrayList<String> baseTextToArray = new ArrayList<String>();
		baseTextToArray.add(baseText);
		
		ArrayList<String> sepStrings = new ArrayList<String>();
		
		for (int i = 0; i < stressLines.size(); i++)
		{
			if (i == 0)
			{
				sepStrings = (returnStressStringArrayBasedOnWord(baseTextToArray, stressLines.get(i)));
			}
			else
			{
				sepStrings = (returnStressStringArrayBasedOnWord(sepStrings, stressLines.get(i)));
			}
		}

		
		return sepStrings;
	}
	
	ArrayList<Integer> indexesGivenCheckWord(String baseText, String wordToCheck)
	{
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		int index = baseText.toLowerCase().indexOf(wordToCheck);
		boolean repeat = true;
		
		while (repeat)
		{
			if (index != -1)
			{
				indexes.add(index);
			}
			else
			{
				repeat = false;
			}
			
			index  = baseText.toLowerCase().indexOf(wordToCheck, index + wordToCheck.length());
		}
		
		return indexes;
	}
	
	ArrayList<String> returnStressStringArrayBasedOnWord(ArrayList<String> baseText, String wordToCheck)
	{
		ArrayList<String> stringArray = new ArrayList<String>();
		
		for (int t = 0; t < baseText.size(); t++)
		{
				String lowerText = baseText.get(t).toLowerCase();
				ArrayList<Integer> indexes = indexesGivenCheckWord(lowerText, wordToCheck);
				int lastIndex = 0;
			
					for (int i = 0; i < indexes.size(); i++)
					{
						stringArray.add(baseText.get(t).substring(lastIndex, indexes.get(i)));
						stringArray.add(baseText.get(t).substring(indexes.get(i), indexes.get(i) + wordToCheck.length()));
						lastIndex += indexes.get(i) + wordToCheck.length();
					}
				
					if (indexes.size() > 0)
					{
						stringArray.add(baseText.get(t).substring(lastIndex, baseText.get(t).length()));
					}
					else
					{
						stringArray.add(baseText.get(t));
					}
		}
		
		return stringArray;
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
	
	void fillStressedWords(ArrayList<String> baseString)
	{
		
	}

}
