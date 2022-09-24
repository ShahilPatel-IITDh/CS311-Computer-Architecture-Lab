package processor.pipeline;

import generic.Instruction;
import processor.Processor;
import generic.Instruction.OperationType;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_Enable_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_Enable_Latch;
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.isMA_Locked()){

			MA_RW_Latch.setRW_Lock(true);
			MA_RW_Latch.setInstruction(null);
			EX_MA_Latch.setMA_Lock(false);
		}

		else if(EX_MA_Latch.isMA_enable()){

			Instruction currentInstruction = EX_MA_Latch.getInstruction();
			Instruction CI;

			CI = currentInstruction;

			int aluResult = EX_MA_Latch.getAluResult();

			int alr;
			int CP;
			alr = aluResult;

			OperationType currentOperation = currentInstruction.getOperationType();
			int currentPC = currentInstruction.getProgramCounter();
			
			CP = currentPC;
			if(currentOperation == OperationType.load){

				int ldResult = containingProcessor.getMainMemory().getWord(aluResult);
				int ldr;
				ldr = ldResult;
				
				MA_RW_Latch.setLdResult(ldr);
				System.out.println("\nMA Stage: " + " Current PC: " + currentPC + " Operation: " + currentOperation.name() + " ld Result: " + ldr);
			}
			else if(currentOperation == OperationType.end){
				IF_EnableLatch.setIF_enable(false);
			}

			else if(currentOperation == OperationType.store){

				int stWord = containingProcessor.getRegisterFile().getValue(currentInstruction.getSourceOperand1().getValue());
				int sw;
				sw = stWord;

				containingProcessor.getMainMemory().setWord(aluResult, sw);
				System.out.println("\nMA Stage: " + " Current PC: " + currentPC + " Operation: " + currentOperation.name() + " Storing: " + sw + " into Memory location: " + aluResult);
			}
			//dead code
			int dead = 0;
			if(dead==100){
				dead++;
			}
			MA_RW_Latch.setAluResult(aluResult);
			MA_RW_Latch.setInstruction(currentInstruction);
			MA_RW_Latch.setRW_enable(true);
		}
	}
}
