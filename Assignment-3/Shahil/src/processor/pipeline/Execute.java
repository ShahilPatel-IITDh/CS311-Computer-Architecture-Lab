package processor.pipeline;

import generic.Instruction;
import generic.Operand;
import processor.Processor;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;
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
		if(OF_EX_Latch.isEX_enable()){

			Instruction command = OF_EX_Latch.getInstruction();

			int currentPC = command.getProgramCounter() - 1;

			OperationType currentOperation = command.getOperationType();

			int remainder;
			int sourceOperand1 = -1;
			int sourceOperand2 = -1;
			int immediate;
			int aluResult = -1;

			switch (currentOperation){

				case muli:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 * immediate;
					break;

				case divi:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 / immediate;
					remainder = (sourceOperand1 % immediate);
					containingProcessor.getRegisterFile().setValue(31, remainder);
					break;

				case addi:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 + immediate;
					break;

				case andi:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 & immediate;
					break;

				case subi:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 - immediate;
					break;

				case ori:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 | immediate;
					break;

				case xori:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 ^ immediate;
					break;

				case slt:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					if(sourceOperand1 < sourceOperand2)
						aluResult = 1;
					else
						aluResult = 0;
					break;

				case slti:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					if(sourceOperand1 < immediate)
						aluResult = 1;
					else
						aluResult = 0;
					break;

				case srl:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 >>> sourceOperand2;
					break;

				case srli:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 >>> immediate;
					break;

				case sll:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 << sourceOperand2;
					break;

				case slli:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 << immediate;
					break;
					
				case sra:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 >> sourceOperand2;
					break;
				case srai:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 >> immediate;
					break;

				case load:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 + immediate;
					break;

				case store:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getDestinationOperand().getValue());
					immediate = command.getSourceOperand2().getValue();
					aluResult = sourceOperand1 + immediate;
					break;

				case jmp:
					OperandType jump = command.getDestinationOperand().getOperandType();
					if(jump == OperandType.Register){
						immediate = containingProcessor.getRegisterFile().getValue(command.getDestinationOperand().getValue());
					}
					else{
						immediate = command.getDestinationOperand().getValue();
					}
					aluResult = currentPC + immediate;
					EX_IF_Latch.setEX_IF_enable(true, aluResult);
					break;

				case and:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 & sourceOperand2;
					break;

				case or:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 | sourceOperand2;
					break;

				case xor:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 ^ sourceOperand2;
					break;

				case beq:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					immediate = command.getDestinationOperand().getValue();
					if(sourceOperand1 == sourceOperand2){
						aluResult = currentPC + immediate;
						EX_IF_Latch.setEX_IF_enable(true, aluResult);
					}
					break;

				case bne:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					immediate = command.getDestinationOperand().getValue();
					if(sourceOperand1 != sourceOperand2){
						aluResult = currentPC + immediate;
						EX_IF_Latch.setEX_IF_enable(true, aluResult);
					}
					break;

				case blt:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					immediate = command.getDestinationOperand().getValue();
					if(sourceOperand1 < sourceOperand2){
						aluResult = currentPC + immediate;
						EX_IF_Latch.setEX_IF_enable(true, aluResult);
					}
					break;

				case bgt:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					immediate = command.getDestinationOperand().getValue();
					if(sourceOperand1 > sourceOperand2){
						aluResult = currentPC + immediate;
						EX_IF_Latch.setEX_IF_enable(true, aluResult);
					}
					break;

				case add:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 + sourceOperand2;
					break;

				case sub:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 - sourceOperand2;
					break;


				case mul:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 * sourceOperand2;
					break;

				case div:
					sourceOperand1 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand1().getValue());
					sourceOperand2 = containingProcessor.getRegisterFile().getValue(command.getSourceOperand2().getValue());
					aluResult = sourceOperand1 / sourceOperand2;
					remainder = (sourceOperand1 % sourceOperand2);
					containingProcessor.getRegisterFile().setValue(31, remainder);
					break;


				case end:
					break;

				default:
					break;
			}
			EX_MA_Latch.setAluResult(aluResult);
			EX_MA_Latch.setInstruction(command);

			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);

			System.out.println("\nEX Stage");
			System.out.println("Operation = " + currentOperation.name());
			if(aluResult != -1)
				System.out.println("Alu Result = " + aluResult);
			System.out.println(sourceOperand1);
			System.out.println(sourceOperand2);
		}
	}
}