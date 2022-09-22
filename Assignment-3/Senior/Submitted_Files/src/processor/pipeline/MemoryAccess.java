package processor.pipeline;

import generic.Instruction;
import generic.Instruction.OperationType;
import processor.Processor;
public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;

	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}

	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable())
		{
			Instruction instruction = EX_MA_Latch.getInstruction();
			int ALU_output = EX_MA_Latch.getALU_result();
			MA_RW_Latch.setALU_Output(ALU_output);
			OperationType Operation_Type = instruction.getOperationType();
			if(Operation_Type.toString().equals("store")){
				int res_st = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());
				containingProcessor.getMainMemory().setWord(ALU_output, res_st);
			}
			else if(Operation_Type.toString().equals("load")){
				int res_ld = containingProcessor.getMainMemory().getWord(ALU_output);
				MA_RW_Latch.setLoad_Output(res_ld);
			}
			MA_RW_Latch.setInstruction(instruction);
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_enable(false);
		}
	}

}
