	.data
n:
	10
	.text
main:
	addi %x0, 65535, %x7
	add %x0, %x0, %x5
	store %x5, 0, %x7
	subi %x7, 1, %x7
	load %x0, $n, %x3
	add %x0, %x0, %x4
	addi %x0, 1, %x5
	beq %x0, %x3, endl
	add %x0, %x0, %x6
	add %x0, %x0, %x8
loop:
	beq %x6, %x3, endl
	store %x5, 0, %x7
	add %x4, %x5, %x8
	sub %x4, %x4, %x4
	add %x5, %x0, %x4
	sub %x5, %x5, %x5
	add %x8, %x0, %x5
	addi %x6, 1, %x6
	subi %x7, 1, %x7
	jmp loop
endl:
	end