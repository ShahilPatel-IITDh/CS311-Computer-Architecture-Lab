package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {

	boolean RW_enable;
	Instruction instruction;
	int load_output;
	int alu_output;

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

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction cmd) {
		instruction = cmd;
	}

	public void setLoad_Output(int output) {
		load_output = output;
	}

	public int getLoad_Output() {
		return load_output;
	}

	public int getALU_Output() {
		return alu_output;
	}

	public void setALU_Output(int output) {
		alu_output = output;
	}

}
