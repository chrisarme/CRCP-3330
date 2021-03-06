package unitTests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

import mainSuffixTreePackage.Main;
import mainSuffixTreePackage.MidiFileToNotes;
import mainSuffixTreePackage.PST;

public class PMinUnitTest4 
{

	PST<Integer> testPST1 = new PST<Integer>(3, .15f, .2f);
	PST<Double> testPST2 = new PST<Double>(3, .15f, .2f);
	
	public void run() 
	{
		String path = getPath("../mid/MaryHadALittleLamb.mid");
		
		MidiFileToNotes midiNotes;
		
		midiNotes = new MidiFileToNotes(path);
		midiNotes.setWhichLine(0); // change which channel we are grabbing notes from
		midiNotes.processPitchesAsTokens();
		
		ArrayList<Integer> currentPitchArray = midiNotes.getPitchArray();
		ArrayList<Double> currentRhythmArray = midiNotes.getRhythmArray();

		System.out.println("------- pMin = .15 -------");
		
		testPST1.addData(currentPitchArray);
		testPST2.addData(currentRhythmArray);
		
		System.out.println("-----Pitch-----");
		testPST1.printData();
		
		System.out.println("-----Rhythm-----");
		testPST2.printData();
		
		System.out.println("------- pMin = .2 -------");
		
		PST<Integer> testPST1 = new PST<Integer>(3, .2f, .2f);
		PST<Double> testPST2 = new PST<Double>(3, .2f, .2f);
		
		testPST1.addData(currentPitchArray);
		testPST2.addData(currentRhythmArray);
		
		System.out.println("-----Pitch-----");
		testPST1.printData();
		
		System.out.println("-----Rhythm-----");
		testPST2.printData();
	}
	
	public String getPath(String filename)
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
