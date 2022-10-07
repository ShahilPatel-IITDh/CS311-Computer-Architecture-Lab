package processor.pipeline;

public class EX_IF_LatchType {

	boolean IS_enable;
	int PC;

	//constructors
	public EX_IF_LatchType() {
		this.IS_enable = false;
	}

	public EX_IF_LatchType(boolean ex_if_enable) {
		this.IS_enable = ex_if_enable;
	}

	public EX_IF_LatchType(boolean ex_if_enable, int pc) {
		this.IS_enable = ex_if_enable;
		PC = pc;
	}

	public boolean isEX_IF_enable() {
		return IS_enable;
	}

	public void setEX_IF_enable(boolean ex_if_enable, int pc) {
		this.IS_enable = ex_if_enable;
		PC = pc;
	}

	public void setEX_IF_enable(boolean ex_if_enable) {
		this.IS_enable = ex_if_enable;
	}

	public void setPC(int pc) {
		PC = pc;
	}

	public int getPC() {
		return PC;
	}
}