package processor.pipeline;

import generic.Simulator;
import processor.Processor;


import generic.Instruction;
import generic.Instruction.OperationType;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			Instruction command = MA_RW_Latch.getInst();
			OperationType operationType = command.getOperationType();


			int alu_result = MA_RW_Latch.getALU();
			int load_result = MA_RW_Latch.getLoadResult();
			int destination = command.getDestinationOperand().getValue();


			// if(operationType.equals(store)){

			// }

			// if(operationType.equals(jmp)){

			// }

			// if(operationType.equals(beq)){

			// }

			// if(operationType.equals(bne)){

			// }

			// if(operationType.equals(blt)){

			// }

			// if(operationType.equals(bgt)){

			// }

			switch(operationType){
				case load:
					destination = command.getDestinationOperand().getValue();

					//loading the load_result in destination register.
					containingProcessor.getRegisterFile().setValue(destination, load_result);
					break;
				
				case end:
					//terminating the process of Simulator
					Simulator.setSimulationComplete(true);
					break;
					
				default:
					destination = command.getDestinationOperand().getValue();
					
					//loading 
					containingProcessor.getRegisterFile().setValue(destination, alu_result);
					break;
			}
			
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
