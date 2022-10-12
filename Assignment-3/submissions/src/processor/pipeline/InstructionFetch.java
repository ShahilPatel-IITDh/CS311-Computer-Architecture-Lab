package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{
			if(EX_IF_Latch.isEX_IF_enable()) {
				int branchPC = EX_IF_Latch.getPC();
				int bc;
				containingProcessor.getRegisterFile().setProgramCounter(branchPC);
				bc = branchPC;
				EX_IF_Latch.setEX_IF_enable(false);

				bc++;
				bc--;
			}

			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();

			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);

			IF_OF_Latch.setInstruction(newInstruction);
			int bc = 0;
			bc*=100;
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			
			bc/=100;
			IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);

			bc=0;
			if(bc>10000){
				bc/=100;
			}

			// System.out.println("===================================================");
			System.out.println("\nIF Stage");
			System.out.println("currentPC = " + currentPC);
		}
	}

}