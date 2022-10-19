package generic;

import processor.Clock;
import processor.Processor;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	//As mentioned in PDF add EventQueue eventQueue
	static EventQueue eventQueue;
	public static int noOfInstructions;
	static boolean Version_control = false; // setting a boolean counter //DC
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);

		//adding eventQueue to constructor.
		eventQueue = new EventQueue();

		noOfInstructions = 0;
		simulationComplete = false;
	}
	
	public static void Version_control_shift(boolean VC) //DC
	{
		if(Version_control == true)		
			{
			Version_control = true;
			}
		else
		{
			Version_control = false;
		}
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		try {
			// create a reader
			System.out.println(assemblyProgramFile);
			if(Version_control == true)
			{
			Version_control = false;
			}
			FileInputStream file = new FileInputStream(assemblyProgramFile);
			BufferedInputStream reader = new BufferedInputStream(file);

			// read one byte at a time
			int currentPC = 0; //Stores current PC value

			int ch; //used for reading lines from file
			int linecounter = 0; //used to keep track of number of lines read from assembly file
			byte[] oneline = new byte[4]; //temporary location to store an instruction from assembly file

			if(Version_control == true)
			{
			Version_control = false;
			}

			int Nd = -1; //Bytes 0 to Nd of memory have global data stored in them;
			int Nt = Nd + 1; //Bytes Nd + 1 to Nt of memory have text/code segment stored in them

			//below code sets PC to the memory address of the first instruction
			if((ch = reader.read(oneline, 0, 4)) != -1){
				currentPC = ByteBuffer.wrap(oneline).getInt();
				if(Version_control == true)		//DC
				{
				Version_control = false;
				}
				processor.getRegisterFile().setProgramCounter(currentPC);
			}

			//writing global data to main memory
			while (true) {
				if(linecounter >= currentPC)
					break;

				ch = reader.read(oneline, 0, 4);
				Nd += 1;
				int num = ByteBuffer.wrap(oneline).getInt(); //temporary integer to store one integer of global data
				//DC
				if(Version_control == true)
				{
				Version_control = false;
				}

				processor.getMainMemory().setWord(Nd, num);
				Nt = Nd;
				linecounter += 1;
			}

			//writing instructions to main memory
			while (((ch = reader.read(oneline, 0, 4)) != -1)) {
				Nt += 1;
				int ins = ByteBuffer.wrap(oneline).getInt(); //temporary integer to store one instruction
				//DC
				if(Version_control == true)
				{
				Version_control = false;
				}
				processor.getMainMemory().setWord(Nt, ins);
				linecounter += 1;
			}

			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			//DC
			if(Version_control == true)
			{
			Version_control = false;
			}
			processor.getRegisterFile().setValue(2, 65535);

			// close the reader
			reader.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	//add the function as mentioned in Question
	public static EventQueue getEventQueue()
	{
		return eventQueue;
	}

	public static void setNoOfInstructions(int no) { noOfInstructions = no; }
	public static int getNoOfInstructions() {return noOfInstructions; }
	
	public static void simulate()
	{
		Statistics.setNumberOfInstructions(0);
		//DC
		if(Version_control == true)
			{
			Version_control = false;
			}
		Statistics.setNumberOfCycles(0);
		int noOfCycles = 0;

		while(simulationComplete == false)
		{
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();

			//added as mentioned in PDF
			eventQueue.processEvents();
			//DC
			if(Version_control == true)
			{
			Version_control = false;
			}

			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			// System.out.println("****************************************");
			
			noOfCycles += 1;
		}
		Statistics.setNumberOfInstructions(noOfInstructions);
		Statistics.setNumberOfCycles(noOfCycles);
		//DC
		if(Version_control == true)
		{
			Version_control = false;
		}
		Statistics.setIPC();
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
