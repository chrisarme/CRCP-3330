import processing.core.*;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.jaunt.JauntException;

import markovChain.MarkovChainListGenerator;

//This class serves as a template for creating twitterbots and demonstrates string tokenizing and web scraping and the use of the 
//twitter API
public class TwitterBotMain extends PApplet {

	private ArrayList<String> tokens;
	private static String HEYER_TWITTER_URL = "https://twitter.com/BotPhlip"; //this is mine, you should use yours
	private static int TWITTER_CHAR_LIMIT = 140; //I understand this has changed... but forget limit
	
	//useful constant strings -- for instance if you want to make sure your tweet ends on a space or ending punctuation, etc.
	private static final String fPUNCTUATION = "\",.!?;:()/\\";
	private static final String fENDPUNCTUATION = ".!?;,";
	private static final String fREALENDPUNCTUATION = ".!?";

	private static final String fWHITESPACE = "\t\r\n ";
	
	//example twitter hastag search term
	private static final String fPASSIVEAGG = "passiveaggressive";
	private static final String fCOMMA = ","; 
	
	
	
	// AI part
	int order = 1;
	
	MarkovChainListGenerator<String> markovChainGenerator = new MarkovChainListGenerator<String>(order);
	
	//handles twitter api
	TwitterInteraction tweet; 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("TwitterBotMain");  //Not really using processing functionality but ya know, you _could_. UI not required.
		
	}

	public void settings() {
		size(300, 300); //dummy window

	};

	public void setup() {
		tweet = new TwitterInteraction(); 
		
		loadNovel("data/Manifesto.txt"); //TODO: must train from another source
		println("Token size:"+tokens.size());

		//TODO: train an AI algorithm (eg, Markov Chain) and generate text for markov chain status
		
		//can train on twitter statuses -- note: in your code I would put this part in a separate function
		//but anyhow, here is an example of searrching twitter hashtag. You have to pay $$ to the man to get more results. :(
		//see TwitterInteraction class
		ArrayList<String> tweetResults = tweet.searchForTweets(fPASSIVEAGG);
		for (int i = 0; i < tweetResults.size(); i++) {
				//println(tweetResults.get(i)); //just prints out the results for now
		}
		
		//Make sure within Twitter limits (used to be 140 but now is more?)
		//String status = "Hello, world -- I am a twitterbot!!";
		
		ArrayList<String> specialChar = new ArrayList<String>();
		specialChar.add(" ");
		specialChar.add(".");
		specialChar.add(",");
		
		ArrayList<String> lastWords = new ArrayList<String>();
		
		for (int i = 0; i < order; i++)
		{
			lastWords.add(tokens.get(i));
		}
		
		for (int i = 0; i < 10; i++)
		{
			
			String status = "";
			
			/*while (status.length() < 250)
			{
				status += tokens.get(i);
				
				if (i < tokens.size() - 1)
				{
				
					int isSpecialChar = specialChar.indexOf(tokens.get(i + 1));
					if (isSpecialChar == -1)
					{
						status += " ";
					}
					
					i++;
				}
			}*/
			
			status = "";
			
			markovChainGenerator = new MarkovChainListGenerator<>(order);
			markovChainGenerator.train(tokens);
			markovChainGenerator.generate(20, lastWords);
			
			ArrayList<String> generatedStatus = markovChainGenerator.returnGeneratedArray();
			
			lastWords = new ArrayList<String>();
			
			for (int o = order; o >= 0; o--)
			{
				lastWords.add(generatedStatus.get(generatedStatus.size() - 1 - o));
			}
			
			for (int s = 0; s < generatedStatus.size(); s++)
			{
				status += generatedStatus.get(s) + " ";
			}
			
			status += " #equalityforall";
			
			tweet.updateTwitter(status);
			
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		//prints the text content of the sites that come up with the google search of dogs
		//you may use this content to train your AI too
		/*Scraper scraper = new Scraper(); 
		try {
			scraper.scrapeGoogleResults("dogs");
			//scraper.scrape("http://google.com",  "dogs"); //see class documentation

		} catch (JauntException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
				
	}


	//this loads the novel 'The Grand Sophy' given a path p -- but really will load any file.
	void loadNovel(String p) {
		String filePath = getPath(p);
		Path path = Paths.get(filePath);
		tokens = new ArrayList<String>();

		try {
			List<String> lines = Files.readAllLines(path);

			for (int i = 0; i < lines.size(); i++) {

				TextTokenizer tokenizer = new TextTokenizer(lines.get(i));
				Set<String> t = tokenizer.parseSearchText();
				tokens.addAll(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			println("Oopsie! We had a problem reading a file!");
		}
	}
	
	void printTokens() {
		for (int i = 0; i < tokens.size(); i++)
			print(tokens.get(i) + " ");
	}

	//get the relative file path 
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

	public void draw() {
		// ellipse(width / 2, height / 2, second(), second());

	}

}
