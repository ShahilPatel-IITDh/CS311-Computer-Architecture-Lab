package generic;

import processor.Clock;
import processor.Processor;
import java.nio.ByteBuffer;


import java.io.IOException;
import java.io.InputStream;
import processor.memorysystem.MainMemory;

import java.io.FileInputStream;

import processor.pipeline.RegisterFile;
public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
	
		//  * TODO
		//  * 1. load the program into memory according to the program layout described
		//  *    in the ISA specification
		 

		 try(
			InputStream inputStream = new FileInputStream(assemblyProgramFile);
		 ){
			MainMemory mainMemory = new MainMemory();
			RegisterFile register = new RegisterFile();

			byte[] arr = new byte[4];
			int i=0, j=0, pc=0;

			while(inputStream.read(arr) != -1){

				ByteBuffer w = ByteBuffer.wrap(arr);

				int n= w.getInt();

				if(j==0){
					pc = n;
				}

				if(j!=0){
					mainMemory.setWord(i,n);
					i+=1;
				}

				j+=1;
			}

			processor.setMainMemory(mainMemory);

			// * 2. set PC to the address of the first instruction in the main
			register.setProgramCounter(pc);

			// 3. set the following registers:
			//     x0 = 0
			//     x1 = 65535
			//     x2 = 65535

			register.setValue(0,0);
			register.setValue(1,65535);
			register.setValue(2,65535);

			processor.setRegisterFile(register);

			}catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
		}
		
		// TODO
		// set statistics
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
