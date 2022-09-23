package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	//variables to store the ALU result and instruction
	int ALU;
	Instruction inst;

	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	//setter for instruction inst
	public void setInst(Instruction instruction){
		this.inst = instruction;
	}
	
	//getter for instruction
	public Instruction getInst(){
		return inst;
	}

	//setter for ALU
	public void setALU(int ans){
		this.ALU = ans;
	}

	//getter for ALU
	public int getALU(){
		return ALU;
	}

	

}
