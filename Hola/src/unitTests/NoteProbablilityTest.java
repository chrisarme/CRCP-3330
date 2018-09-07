package unitTests;

import aNewPackage_test.MelodyPlayer;
import aNewPackage_test.MidiFileToNotes;
import aNewPackage_test.ProbabilityListGenerator;

public class NoteProbablilityTest
{
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	
	ProbabilityListGenerator<Integer> pitchGenerator = new ProbabilityListGenerator<Integer>();
	ProbabilityListGenerator<Double> rhythmGenerator = new ProbabilityListGenerator<Double>();
	
	private static void main() 
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
	}

}
