package processor.pipeline;

public class EX_IF_LatchType {

	boolean IS_enable;
	int PC_value;

	public EX_IF_LatchType() {
		this.IS_enable = false;
	}

	public EX_IF_LatchType(boolean ex_if_enable) {
		this.IS_enable = ex_if_enable;
	}

	public EX_IF_LatchType(boolean ex_if_enable, int pc) {
		this.IS_enable = ex_if_enable;
		this.PC_value = pc;
	}

	public boolean isEX_IF_enable() {
		return this.IS_enable;
	}

	public void setEX_IF_enable(boolean ex_if_enable, int pc) {
		this.IS_enable = ex_if_enable;
		this.PC_value = pc;
	}

	public void setEX_IF_enable(boolean ex_if_enable) {
		this.IS_enable = ex_if_enable;
	}

	public void setPC(int pc) {
		this.PC_value = pc;
	}

	public int getPC() {
		return PC_value;
	}

}