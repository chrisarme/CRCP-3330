package aNewPackage_test;

import processing.core.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import jm.music.*;
import jm.util.*;
import jm.music.data.Score;

public class HelloWorld extends PApplet
{
	//float x = 150;
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		PApplet.main("aNewPackage_test.HelloWorld");
	}
	
	public void settings()
	{
		size(300, 300);
	}
	
	public void setup()
	{
		String path = getPath("../mid/la_cumparsita.mid");
		
		//println(path);
		
		//playMidiFile(path);
		midiNotes = new MidiFileToNotes(path);
		midiNotes.setWhichLine(0); // change which channel we are grabbing notes from
		midiNotes.processPitchesAsTokens();
		
		player = new MelodyPlayer(this, 100f);
		player.setup();
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
		
		
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
		//ellipse(x, 100, 100, 100);
		//x += 1;
		
		player.play();
	}

}
