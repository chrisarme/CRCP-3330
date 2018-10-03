// Chris Arme
// Main Class

package aNewPackage_test;

import processing.core.*;
import unitTests.MassGeneratingDataAndProbablilityTest;
import unitTests.PrintPitchesAndRhythmTest;
import unitTests.PrintTransitionTablesTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import jm.music.*;
import jm.util.*;
import jm.music.data.Score;

public class HelloWorld extends PApplet
{
	static int generationAmount = 50;
	boolean playMusic = false;
	
	//float x = 150;
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	
	ProbabilityListGenerator<Integer> pitchGenerator = new ProbabilityListGenerator<Integer>();
	ProbabilityListGenerator<Double> rhythmGenerator = new ProbabilityListGenerator<Double>();
	
	MarkovChainListGenerator<Integer> pitchMarkovGenerator = new MarkovChainListGenerator<>(3);
	MarkovChainListGenerator<Double> rhythmMarkovGenerator = new MarkovChainListGenerator<>(3);
	
	PrintPitchesAndRhythmTest printDataTest = new PrintPitchesAndRhythmTest();
	PrintTransitionTablesTest transitionTableTest = new PrintTransitionTablesTest();
	MassGeneratingDataAndProbablilityTest massGenerationAndProbTest = new MassGeneratingDataAndProbablilityTest();
	
	//ProbabilityListGenerator<MidiFileToNotes> probablityGenerator; 
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		PApplet.main("aNewPackage_test.HelloWorld");
	}
	
	public void settings()
	{
		size(600, 600);
	}
	
	public void setup()
	{
		String path = getPath("../mid/Ambiguous.mid");
		
		//println(path);
		
		//playMidiFile(path);
		midiNotes = new MidiFileToNotes(path);
		midiNotes.setWhichLine(0); // change which channel we are grabbing notes from
		midiNotes.processPitchesAsTokens();
		
		//pitchGenerator.train(midiNotes.getPitchArray());
		//rhythmGenerator.train(midiNotes.getRhythmArray());
		
		pitchMarkovGenerator.train(midiNotes.getPitchArray());
		rhythmMarkovGenerator.train(midiNotes.getRhythmArray());
		
		// The number of data points generated can be anything, 20 is just a good test size
		pitchMarkovGenerator.generate(100);
		rhythmMarkovGenerator.generate(100);
		
		//pitchGenerator.generate(generationAmount);
		//rhythmGenerator.generate(generationAmount);
		
		//pitchGenerator.testData();
		//rhythmGenerator.testData();
		
		player = new MelodyPlayer(this, 100f);
		player.setup();
		//player.setMelody(midiNotes.getPitchArray());
		//player.setRhythm(midiNotes.getRhythmArray());
		
		player.setMelody(pitchMarkovGenerator.returnGeneratedArray());
		player.setRhythm(rhythmMarkovGenerator.returnGeneratedArray());
		
	}
	
	public void playMidiFile(String filename)
	{
		Score score = new Score("Temp Score");
		Read.midi(score, filename);
		Play.midi(score);
	}
	
	String getPath(String filename)
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
	
	public void draw()
	{	
		// display only
		background(150);
		
		fill(255);
		textSize(30);
		
		text("Press Space to Start/Stop Music", (width / 2) - textWidth("Press Space to Start/Stop Music") / 2, 50);
		
		textSize(18);
		text ("Press 1 for TransitionTableTest (Unit Test 1)", (width / 2) - textWidth("Press 1 for TransitionTableTest (Unit Test 1)") / 2, 400);
		text ("Press 2 for PrintPitchesAndRhythmTest (Unit Test 2)", (width / 2) - textWidth("Press 2 for PrintPitchesAndRhythmTest (Unit Test 2)") / 2, 440);
		text ("Press 3 for MassGeneratingDataAndProbabliltyTest (Unit Test 3)", (width / 2) - textWidth("Press 3 for MassGeneratingDataAndProbabliltyTest (Unit Test 3)") / 2, 480);
		
		textSize(15);
		fill(220, 0, 0);
		//text("WARNING: UNIT TEST 3 WILL CAUSE ECLIPSE TO THROW AN ERROR", (width / 2) - textWidth("WARNING: UNIT TEST 3 WILL CAUSE ECLIPSE TO THROW AN ERROR") / 2, 500);
		
		fill(255);
		textSize(40);
		
		if (playMusic == true)
		{
			text("Playing", (width / 2) - textWidth("Playing") / 2, 100);
		}
		else 
		{
			text("Not Playing", (width / 2) - textWidth("Not Playing") / 2, 100);
		}
		
		if (playMusic == true)
		{
			player.play();
		}
	}

	public void keyPressed()
	{
		if (key == ' ')
		{
			if (playMusic == true)
			{
				playMusic = false;
			}
			else 
			{
				playMusic = true;
			}
		}
		
		if (key == '1')
		{
			transitionTableTest.actualTest();
		}
		else if (key == '2')
		{
			printDataTest.actualTest();
		}
		else if (key == '3')
		{
			massGenerationAndProbTest.actualTest();
		}
	}
}
