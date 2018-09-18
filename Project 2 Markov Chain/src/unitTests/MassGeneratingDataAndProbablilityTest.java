// Chris Arme
// This tests what the probability of the generated pitches and rhythms are after 10,000 loops
// WARNING: THIS DOESN'T WORK DO TO THE PROCESSING POWER ACTUALLY NEEDED TO DO IT

package unitTests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import aNewPackage_test.MelodyPlayer;

import aNewPackage_test.MidiFileToNotes;
import aNewPackage_test.ProbabilityListGenerator;
import processing.core.PApplet;

//import processing.core.*;

public class MassGeneratingDataAndProbablilityTest
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
		
		ArrayList<Integer> currentPitchArray = midiNotes.getPitchArray();
		ArrayList<Double> currentRhythmArray = midiNotes.getRhythmArray();
		
		for (int i = 0; i < 10000; i++)
		{
			pitchGenerator.clearData();
			rhythmGenerator.clearData();
			
			pitchGenerator.train(currentPitchArray);
			rhythmGenerator.train(currentRhythmArray);
			
			pitchGenerator.generate(20);
			rhythmGenerator.generate(20);
			
			currentPitchArray = pitchGenerator.returnGeneratedArray();
			currentRhythmArray = rhythmGenerator.returnGeneratedArray();
		}
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
