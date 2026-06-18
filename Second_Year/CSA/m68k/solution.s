    .data
    .org 0x0
buf:             .byte '________________________________________'
input_addr:      .word  0x80
output_addr:     .word  0x84

    .text
    .org 0x300
_start:
    movea.l 0x700, A7   ; a_stack_pointer
    jsr init_stack
    jsr init_io
    jsr read_input_start
    jsr read_first_symb_start
    jmp output_start

init_stack:
    movea.l 0x650, A5   ; d_stack_pointer
    clr.l D7            ; кол-во обращений в стек
    rts

init_io:
    movea.l input_addr, A0
    movea.l (A0), A0        ;input port
    movea.l output_addr, A2
    movea.l (A2), A2        ;output port
    rts

read_input_start:
    movea.l buf, A1
read_input:
    cmp.b 0x40, D2
    beq output_overflow

    move.b (A0), D0
    move.b D0, (A1)+

    add.l 1, D2

    cmp.b 10, D0
    beq read_input_end
    jmp read_input
read_input_end:
    rts


read_first_symb_start:
    movea.l buf, A1
    clr.l D2
read_first_symb:
    move.b (A1)+, D2

    cmp.b 43, D2   ; +
    beq do_plus

    cmp.b 45, D2   ; -
    beq do_minus

    cmp.b 42, D2   ; '
    beq do_mul

    cmp.b 47, D2   ; /
    beq do_div

    cmp.b 10, D2
    beq end_read_first_symb

    cmp.b 32, D2
    beq read_first_symb

    jmp read_full_num_start
do_plus:
    jsr   pls_case
    jmp   read_first_symb
do_minus:
    jsr   mns_case
    jmp   read_first_symb
do_mul:
    jsr   mul_case
    jmp   read_first_symb
do_div:
    jsr   div_case
    jmp   read_first_symb
end_read_first_symb:
    rts

read_full_num_start:
    clr.l D3
    move.l 10, D5
read_full_num:
    sub.l 48, D2
    add.l D2, D3

    move.b (A1)+, D2
    cmp.b 32, D2
    beq read_full_num_end

    cmp.b 10, D2
    beq output_error

    move.l 10, D4
    mul.l D5, D3
    
    jmp read_full_num
read_full_num_end:
    move.l  D3, -(A7)
    jsr put_num_on_stack
    move.l (A7)+, D6
    jmp read_first_symb


put_num_on_stack:
    link    A6, -8   
    move.l  D0, -4(A6)

    move.l   1, -8(A6)

    move.l  8(A6), D0        
    move.l  D0, -(A5)
    add.l   -8(A6), D7

    move.l  -4(A6), D0      
    unlk    A6
    rts

output_start:
    cmp.l 1, D7
    bne output_error
output:
    move.l (A5)+, D3
    move.l D3, (A2)
end:
    halt

pop_two:
    link    A6, -8
    move.l  D3, -8(A6)
    move.l  D4, -4(A6)

    cmp.l   2, D7
    blt     pop_two_error   

    move.l  (A5)+, D3
    move.l  D3, 12(A6)

    move.l  (A5)+, D4
    move.l  D4, 8(A6)

    sub.l   2, D7

    move.l  -4(A6), D4
    move.l  -8(A6), D3

    unlk    A6
    rts
pop_two_error:
    unlk    A6
    jmp     output_error


pls_case:
    link    A6, -12
    move.l  D0, -4(A6)

    jsr     pop_two
    
    move.l  -12(A6), D0
    add.l   -8(A6), D0
    
    move.l  D0, -(A7)
    jsr     put_num_on_stack
    move.l  (A7)+, D6
    
    move.l  -4(A6), D0
    unlk    A6
    rts


mns_case:
    link    A6, -12
    move.l  D0, -4(A6)
    jsr     pop_two
    move.l  -12(A6), D0
    sub.l   -8(A6), D0
    move.l  D0, -(A7)
    jsr     put_num_on_stack
    move.l  (A7)+, D6
    move.l  -4(A6), D0
    unlk    A6
    rts

div_case:
    link    A6, -12
    move.l  D0, -4(A6)

    jsr     pop_two

    move.l  -8(A6), D0
    cmp.l   0, D0
    beq     div_error

    move.l  -12(A6), D0
    div.l   -8(A6), D0

    move.l  D0, -(A7)
    jsr     put_num_on_stack
    move.l  (A7)+, D6

    move.l  -4(A6), D0
    unlk    A6
    rts
div_error:
    move.l  -4(A6), D0
    unlk    A6
    jmp     output_error


mul_case:
    link    A6, -12
    move.l  D0, -4(A6)

    jsr     pop_two

    move.l  -12(A6), D0
    mul.l   -8(A6), D0

    move.l  D0, -(A7)
    jsr     put_num_on_stack
    move.l  (A7)+, D6

    move.l  -4(A6), D0
    unlk    A6
    rts

output_error:
    move.l -1, -(A5)
    jmp output

output_overflow:
    move.l 0xCCCCCCCC, -(A5)
    jmp output