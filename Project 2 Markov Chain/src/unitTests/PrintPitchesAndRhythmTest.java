// Chris Arme
// This outputs the probability of data appearing based on whatever was passed in
// In this case, it is based off of "Mary Had A Little Lamb"

package unitTests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import aNewPackage_test.MarkovChainListGenerator;
import aNewPackage_test.MelodyPlayer;

import aNewPackage_test.MidiFileToNotes;
import aNewPackage_test.ProbabilityListGenerator;
import processing.core.PApplet;

//import processing.core.*;

public class PrintPitchesAndRhythmTest
{	
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	
	MarkovChainListGenerator<Integer> pitchMarkovGenerator = new MarkovChainListGenerator<Integer>();
	MarkovChainListGenerator<Double> rhythmMarkovGenerator = new MarkovChainListGenerator<Double>();
	
	/*private static void main() 
	{	
		PApplet.main("aNewPackage_test.NoteProbablilityTest");
	}*/
	
	public void actualTest()
	{
		String path = getPath("../mid/MaryHadALittleLamb.mid");
		
		pitchMarkovGenerator = new MarkovChainListGenerator<Integer>();
		rhythmMarkovGenerator = new MarkovChainListGenerator<Double>();
		
		//println(path);
		
		//playMidiFile(path);
		midiNotes = new MidiFileToNotes(path);
		midiNotes.setWhichLine(0); // change which channel we are grabbing notes from
		midiNotes.processPitchesAsTokens();
		
		pitchMarkovGenerator.train(midiNotes.getPitchArray());
		rhythmMarkovGenerator.train(midiNotes.getRhythmArray());
		
		pitchMarkovGenerator.generate(20);
		rhythmMarkovGenerator.generate(20);
		
		System.out.println("Pitches: ");
		pitchMarkovGenerator.printPitchesAndRhythm();
		System.out.println('\n' + "Rhythm: ");
		rhythmMarkovGenerator.printPitchesAndRhythm();
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
}
