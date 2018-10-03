// Chris Arme
// This tests what the probability of the generated pitches and rhythms are after 10,000 loops
// WARNING: THIS DOESN'T WORK DO TO THE PROCESSING POWER ACTUALLY NEEDED TO DO IT

package unitTests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import aNewPackage_test.MarkovChainListGenerator;
import aNewPackage_test.MelodyPlayer;

import aNewPackage_test.MidiFileToNotes;
import aNewPackage_test.ProbabilityListGenerator;
import processing.core.PApplet;

//import processing.core.*;

public class MassGeneratingDataAndProbablilityTest
{	
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	
	MarkovChainListGenerator<Integer> pitchMarkovGenerator = new MarkovChainListGenerator<Integer>(3);
	MarkovChainListGenerator<Double> rhythmMarkovGenerator = new MarkovChainListGenerator<Double>(3);
	
	MarkovChainListGenerator<Integer> pitchMarkovMasterGenerator = new MarkovChainListGenerator<Integer>(3);
	MarkovChainListGenerator<Double> rhythmMarkovMasterGenerator = new MarkovChainListGenerator<Double>(3);
	
	/*private static void main() 
	{	
		PApplet.main("aNewPackage_test.NoteProbablilityTest");
	}*/
	
	public void actualTest()
	{
		String path = getPath("../mid/Ambiguous.mid");
		
		//println(path);
		
		pitchMarkovGenerator = new MarkovChainListGenerator<Integer>(3);
		rhythmMarkovGenerator = new MarkovChainListGenerator<Double>(3);
		
		pitchMarkovMasterGenerator = new MarkovChainListGenerator<Integer>(3);
		rhythmMarkovMasterGenerator = new MarkovChainListGenerator<Double>(3);
		
		//playMidiFile(path);
		midiNotes = new MidiFileToNotes(path);
		midiNotes.setWhichLine(0); // change which channel we are grabbing notes from
		midiNotes.processPitchesAsTokens();
		
		ArrayList<Integer> currentPitchArray = midiNotes.getPitchArray();
		ArrayList<Double> currentRhythmArray = midiNotes.getRhythmArray();
		
		for (int i = 0; i < 10000; i++)
		{		
			pitchMarkovGenerator.clearData();
			rhythmMarkovGenerator.clearData();
			
			pitchMarkovGenerator.train(currentPitchArray);
			rhythmMarkovGenerator.train(currentRhythmArray);
			
			pitchMarkovGenerator.generate(20);
			rhythmMarkovGenerator.generate(20);
			
			pitchMarkovMasterGenerator.train(pitchMarkovGenerator.returnGeneratedArray());
			rhythmMarkovMasterGenerator.train(rhythmMarkovGenerator.returnGeneratedArray());
		}
		
		System.out.println("Pitches: ");
		
		pitchMarkovMasterGenerator.printTransitionTable();
		System.out.println('\n' + "Rhythm: ");
		rhythmMarkovMasterGenerator.printTransitionTable();
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
