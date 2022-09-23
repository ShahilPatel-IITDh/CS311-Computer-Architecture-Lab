package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	Instruction instruction;
	int alu_Result;

	public EX_MA_LatchType(){
		MA_enable = false;
	}

	public void setInstruction(Instruction instruction){
		this.instruction = instruction;
	}

	public Instruction getInstruction() {
		return this.instruction;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	
	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setAluResult(int ALUResult){
		this.alu_Result = ALUResult;
	}

	public int getAluResult(){
		return this.alu_Result;
	}

}
