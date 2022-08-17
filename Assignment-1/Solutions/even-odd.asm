    .data
a:
	101
	.text
main:
	load %x0, $a, %x4
	divi %x4, 2, %x6
	muli %x6, 2, %x6
	beq %x4, %x6, even
	addi %x0, 1, %x10
	end
even:
	subi %x0, 1, %x10
	end