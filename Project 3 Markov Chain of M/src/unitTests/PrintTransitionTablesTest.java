// Chris Arme
// This prints the pitches and rhythms that are generated

package unitTests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import aNewPackage_test.MarkovChainListGenerator;
import aNewPackage_test.MelodyPlayer;

import aNewPackage_test.MidiFileToNotes;
import aNewPackage_test.ProbabilityListGenerator;
import processing.core.PApplet;

//import processing.core.*;

public class PrintTransitionTablesTest
{	
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	
	MarkovChainListGenerator<Integer> pitchMarkovGenerator = new MarkovChainListGenerator<>(2);
	MarkovChainListGenerator<Double> rhythmMarkovGenerator = new MarkovChainListGenerator<>(2);
	
	/*private static void main() 
	{	
		PApplet.main("aNewPackage_test.NoteProbablilityTest");
	}*/
	
	public void actualTest()
	{
		String path = getPath("../mid/MaryHadALittleLamb.mid");
		
		//println(path);
		
		for (int i = 1; i <= 10; i++)
		{
		//playMidiFile(path);
			midiNotes = new MidiFileToNotes(path);
			midiNotes.setWhichLine(0); // change which channel we are grabbing notes from
			midiNotes.processPitchesAsTokens();
			
			pitchMarkovGenerator.clearData();
			rhythmMarkovGenerator.clearData();
			
			pitchMarkovGenerator.changeOrder(i);
			rhythmMarkovGenerator.changeOrder(i);
			
			pitchMarkovGenerator.train(midiNotes.getPitchArray());
			rhythmMarkovGenerator.train(midiNotes.getRhythmArray());
			
			pitchMarkovGenerator.generate(20);
			rhythmMarkovGenerator.generate(20);
			
			System.out.println("Pitches: ");
			pitchMarkovGenerator.printTransitionTable();
			System.out.println('\n' + "Rhythm: ");
			rhythmMarkovGenerator.printTransitionTable();
		}
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
