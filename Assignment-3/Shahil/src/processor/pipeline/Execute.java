package processor.pipeline;

import processor.Processor;
import generic.Instruction.OperationType;
import generic.Instruction;
import generic.Operand.OperandType;
import java.util.ArrayList;
import java.util.Arrays;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{
		//TODO
		if(OF_EX_Latch.isEX_enable()){
			//getting the command from OF-EX-Latch 
			Instruction command = OF_EX_Latch.getInst();

			//passing the same command to EX-MA Latch
			EX_MA_Latch.setInst(command);

			OperationType cmd_operation = command.getOperationType();

			int opcode = Arrays.asList(OperationType.values()).indexOf(cmd_operation);

			int curr_PC = containingProcessor.getRegisterFile().getProgramCounter()-1;

			int alu_result = 0;

			if(opcode < 21 && opcode%2 == 0){
				int op1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());

				int op2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());

				if(cmd_operation.toString().equals("add")){
					alu_result = op1+op2;
				}
				else if(cmd_operation.toString().equals("sub")){
					alu_result = op1-op2;
				}
				else if(cmd_operation.toString().equals("mul")){
					alu_result = op1*op2;
				}
				else if(cmd_operation.toString().equals("div")){
					alu_result = op1/op2;
					int remainder = op1%op2;
					containingProcessor.getRegisterFile().setValue(31, remainder);
				}
				else if(cmd_operation.toString().equals("and")){
					alu_result = op1 & op2;
				}
				else if(cmd_operation.toString().equals("or")){
					alu_result = op1 | op2;
				}
				else if(cmd_operation.toString().equals("xor")){
					alu_result = op1^op2;
				}
				else if(cmd_operation.toString().equals("slt")){
					if(op1 < op2){
						alu_result = 1;
					}
					else{
						alu_result = 0;
					}
				}
				else if(cmd_operation.toString().equals("sll")){
					alu_result = op1 << op2;
				}
				else if(cmd_operation.toString().equals("srl")){
					alu_result = op1 >>> op2;
				}
				else if(cmd_operation.toString().equals("sra")){
					alu_result = op1 >> op2;
				}

			}

			else if(opcode < 23){
				int con = command.getSourceOperand1().getValue();
				int op1 = containingProcessor.getRegisterFile().getValue(con);
				int op2 = command.getSourceOperand2().getValue();

				if(cmd_operation.toString().equals("addi")){
					alu_result = op1 + op2;
				}
				else if(cmd_operation.toString().equals("subi")){
					alu_result = op1 - op2;
				}
				else if(cmd_operation.toString().equals("muli")){
					alu_result = op1 * op2;
				}
				else if(cmd_operation.toString().equals("divi")){
					alu_result = op1 / op2;
					int r = op1 % op2;
					containingProcessor.getRegisterFile().setValue(31, r);
				}
				else if(cmd_operation.toString().equals("andi")){
					alu_result = op1 & op2;
				}
				else if(cmd_operation.toString().equals("ori")){
					alu_result = op1 | op2;
				}
				else if(cmd_operation.toString().equals("xori")){
					alu_result = op1 ^ op2;
				}
				else if(cmd_operation.toString().equals("slti")){
					if(op1 < op2)
						alu_result = 1;
					else
						alu_result = 0;
				}
				else if(cmd_operation.toString().equals("slli")){
					alu_result = op1 << op2;
				}
				else if(cmd_operation.toString().equals("srli")){
					alu_result = op1 >>> op2;
				}
				else if(cmd_operation.toString().equals("srai")){
					alu_result = op1 >> op2;
				}
				else if(cmd_operation.toString().equals("load")){
					alu_result = op1 + op2;
				}
			}

			else if(opcode == 23){
				int op1 = containingProcessor.getRegisterFile().getValue(command.getDestinationOperand().getValue());
				int op2 = command.getSourceOperand2().getValue();
				alu_result = op1+op2;
			}

			else if(opcode == 24){
				OperandType OPERNDTYPE = command.getDestinationOperand().getOperandType();

				int immediate = 0;

				if (OPERNDTYPE == OperandType.Register){
					immediate = containingProcessor.getRegisterFile().getValue(
							command.getDestinationOperand().getValue());
				}
				else{
					immediate = command.getDestinationOperand().getValue();
				}
				alu_result = immediate + curr_PC;
				EX_IF_Latch.setEnable(true, alu_result);
			}

			else if(opcode < 29)
			{
				int op1 = containingProcessor.getRegisterFile().getValue(
						command.getSourceOperand1().getValue());
				int op2 = containingProcessor.getRegisterFile().getValue(
						command.getSourceOperand2().getValue());
				int immediate = command.getDestinationOperand().getValue();

				if(cmd_operation.toString().equals("beq")){
					if(op1 == op2)
					{
						alu_result = immediate + curr_PC;
						EX_IF_Latch.setEnable(true, alu_result);
					}
				}
				else if(cmd_operation.toString().equals("bne")){
					if(op1 != op2)
					{
						alu_result = immediate + curr_PC;
						EX_IF_Latch.setEnable(true, alu_result);
					}
				}
				else if(cmd_operation.toString().equals("blt")){

					if(op1 < op2){
						alu_result = immediate + curr_PC;
						EX_IF_Latch.setEnable(true, alu_result);
					}
				}
				else if(cmd_operation.toString().equals("bgt")){
					if(op1 > op2)
					{
						alu_result = immediate + curr_PC;
						EX_IF_Latch.setEnable(true, alu_result);
					}
				}
			}
			EX_MA_Latch.setALU(alu_result);

		}
	}

}
