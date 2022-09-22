package processor.pipeline;

import processor.Processor;
import generic.Instruction.OperationType;
import generic.Instruction;

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
		//TODO
		if(EX_MA_Latch.isMA_enable()){

			int alu_result = EX_MA_Latch.getALU();

			MA_RW_Latch.setALU(alu_result);

			Instruction instruction = EX_MA_Latch.getInst();

			OperationType Operation_Type = instruction.getOperationType();

			if(Operation_Type.toString().equals("store")){
				
				int res_store = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());
				containingProcessor.getMainMemory().setWord(alu_result, res_store);
			}

			else if(Operation_Type.toString().equals("load")){
				int res_load = containingProcessor.getMainMemory().getWord(alu_result);
				MA_RW_Latch.setLoadResult(res_load);
			}

			MA_RW_Latch.setInst(instruction);
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_enable(false);
		}

	}

}
