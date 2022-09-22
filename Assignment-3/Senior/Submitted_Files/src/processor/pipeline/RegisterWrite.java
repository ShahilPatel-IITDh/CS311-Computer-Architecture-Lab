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
			Instruction cmd = MA_RW_Latch.getInstruction();
			int alu_output = MA_RW_Latch.getALU_Output();
			OperationType operationType = cmd.getOperationType();

			switch(operationType)
			{
				case store:
				case jmp:
				case beq:
				case bne:
				case blt:
				case bgt:
					break;
				case load:
					int load_output = MA_RW_Latch.getLoad_Output();
					int DOP = cmd.getDestinationOperand().getValue();
					containingProcessor.getRegisterFile().setValue(DOP, load_output);
					break;
				case end:
					Simulator.setSimulationComplete(true);
					break;
				default:
					DOP = cmd.getDestinationOperand().getValue();
					containingProcessor.getRegisterFile().setValue(DOP, alu_output);
					break;
			}

			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
