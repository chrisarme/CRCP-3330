package aNewPackage_test;

import processing.core.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import jm.music.*;
import jm.util.*;
import jm.music.data.Score;

public class HelloWorld extends PApplet
{
	int generationAmount = 50;
	boolean playMusic = false;
	
	//float x = 150;
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	
	ProbabilityListGenerator<Integer> pitchGenerator = new ProbabilityListGenerator<Integer>();
	ProbabilityListGenerator<Double> rhythmGenerator = new ProbabilityListGenerator<Double>();
	
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
		String path = getPath("../mid/MaryHadALittleLamb.mid");
		
		//println(path);
		
		//playMidiFile(path);
		midiNotes = new MidiFileToNotes(path);
		midiNotes.setWhichLine(0); // change which channel we are grabbing notes from
		midiNotes.processPitchesAsTokens();
		
		pitchGenerator.train(midiNotes.getPitchArray());
		rhythmGenerator.train(midiNotes.getRhythmArray());
		
		pitchGenerator.generate(generationAmount);
		rhythmGenerator.generate(generationAmount);
		
		//pitchGenerator.testData();
		//rhythmGenerator.testData();
		
		player = new MelodyPlayer(this, 100f);
		player.setup();
		//player.setMelody(midiNotes.getPitchArray());
		//player.setRhythm(midiNotes.getRhythmArray());
		
		player.setMelody(pitchGenerator.returnGeneratedArray());
		player.setRhythm(rhythmGenerator.returnGeneratedArray());
		
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
		background(150);
		
		fill(255);
		textSize(30);
		
		text("Press Space to Start/Stop Music", (width / 2) - textWidth("Press Space to Start/Stop Music") / 2, 50);
		
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
	}
}
