// Chris Arme
// This prints the pitches and rhythms that are generated

package unitTests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import aNewPackage_test.MelodyPlayer;

import aNewPackage_test.MidiFileToNotes;
import aNewPackage_test.ProbabilityListGenerator;
import processing.core.PApplet;

//import processing.core.*;

public class PrintPitchesAndRhythmTest
{	
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	
	ProbabilityListGenerator<Integer> pitchGenerator = new ProbabilityListGenerator<Integer>();
	ProbabilityListGenerator<Double> rhythmGenerator = new ProbabilityListGenerator<Double>();
	
	/*private static void main() 
	{	
		PApplet.main("aNewPackage_test.NoteProbablilityTest");
	}*/
	
	public void actualTest()
	{
		String path = getPath("../mid/MaryHadALittleLamb.mid");
		
		//println(path);
		
		//playMidiFile(path);
		midiNotes = new MidiFileToNotes(path);
		midiNotes.setWhichLine(0); // change which channel we are grabbing notes from
		midiNotes.processPitchesAsTokens();
		
		pitchGenerator.train(midiNotes.getPitchArray());
		rhythmGenerator.train(midiNotes.getRhythmArray());
		
		pitchGenerator.generate(20);
		rhythmGenerator.generate(20);
		
		System.out.println("Pitches: ");
		pitchGenerator.printPitchesAndRhythm();
		System.out.println('\n' + "Rhythm: ");
		rhythmGenerator.printPitchesAndRhythm();
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
