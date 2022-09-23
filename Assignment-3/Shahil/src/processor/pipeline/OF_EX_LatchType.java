package processor.pipeline;

import generic.Instruction;
public class OF_EX_LatchType {
	
	boolean EX_enable;

	Instruction inst;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	//setter for instructions
	public void setInst(Instruction instruction){
		this.inst = instruction;
	}

	//getter for instruction
	public Instruction getInst(){
		return inst;
	}

}
