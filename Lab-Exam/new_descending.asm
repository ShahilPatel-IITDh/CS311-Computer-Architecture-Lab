    .data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main: 
	add %x0, %x0, %x19 // temp
	add %x0, %x0, %x20 // i = 0
	add %x0, %x0, %x21 // j = 0
	load %x0, $n, %x5 // length
	jmp loop
loop:
	beq %x20, %x5, end
 	addi %x20, 1, %x21 		// j = i+1
	blt %x21, %x5, nested  // nested loop
	addi %x20, 1, %x20
	jmp loop
nested:
	beq %x21, %x5, end
	load %x20, $a, %x10 // arr[i]
	load %x21, $a, %x11 // arr[j]
	blt %x10, %x11, swap // swap
	addi %21, 1, %x21
	jmp nested
swap:
	add %x0, %x10, %x19 // temp = arr[i]
	add %x0, %x11, %x10 // arr[i] = arr[j]
	add %x0, %x19, %x11 // arr[j] = temp
	store %x10, $a, %x20
	store %x11, $a, %x21
	jmp loop