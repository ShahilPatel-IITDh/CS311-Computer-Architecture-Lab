package processor.pipeline;

public class EX_IF_LatchType {


	// setEnable sets the enabled to parameter passed in the function
	// getEnable returns the value of Enable
	// getPC returns the value of PC 

	public boolean enabled;
	public int PC;
	
	//constructor
	public EX_IF_LatchType()
	{
		enabled = false;
	}

	//value setter for branch condition, as PC will change to new_PC
	public void setEnable(boolean is_enabled, int new_PC){
		this.enabled = is_enabled;
		this.PC = new_PC;
	}

	//value setter for normal condition (i.e. no branch)
	//is_enabled will be true / false
	public void setEnable(boolean is_enabled){
		this.enabled = is_enabled;
	}

	public void setPC(int program_counter){
		this.PC = program_counter;
	}
	
	//value getter for enable 
	public boolean getEnable(){
		return enabled;
	}

	//value getter for pc
	public int getPC(){
		return PC;
	}


}
