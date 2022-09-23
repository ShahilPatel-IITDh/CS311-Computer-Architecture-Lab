package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	Instruction inst;
	int load_result;
	int alu_result;

	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	//setter for instructions
	public void setInst(Instruction command){
		this.inst = command;
	}
	//getter for instructions
	public Instruction getInst(){
		return inst;
	}

	//setter for Load Output
	public void setLoadResult(int output){
		this.load_result = output;
	}

	//getter for Load Output
	public int getLoadResult(){
		return load_result;
	} 

	//setter for ALU 
	public void setALU(int res){
		this.alu_result = res;
	}

	//getter for ALU
	public int getALU(){
		return alu_result;
	}
}
