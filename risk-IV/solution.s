    .data
.org             0x0
buf_start:       .byte  'Hello, '
buf_end:         .byte  '_________________________'

what_name_string: .byte  'What is your name?\n' , 0
input_addr:      .word  0x80
output_addr:     .word  0x84
overflow:        .word  0xCCCCCCCC

    .text
    .org     0x200
_start:
    jal      ra, init_io
    jal      ra, stack_init
    jal      ra, output_what_name
    jal      ra, input_name
    jal      ra, output_name
    j        end

init_io:
    lui      t0, %hi(input_addr)
    addi     t0, t0, %lo(input_addr)

    lw       t0, 0(t0)

    lui      t1, %hi(output_addr)
    addi     t1, t1, %lo(output_addr)

    lw       t1, 0(t1)
    jr       ra

output_what_name:
    lui      t2, %hi(what_name_string)
    addi     t2, t2, %lo(what_name_string)
    addi     t3, zero, 0x00FF
output_what_name_loop:
    lw       t4, 0(t2)
    and      t4, t4, t3
    beqz     t4, output_what_name_end
    sb       t4, 0(t1)
    addi     t2, t2, 1
    j        output_what_name_loop
output_what_name_end:
    jr       ra

input_name:
    lui      t2, %hi(buf_end)
    addi     t2, t2, %lo(buf_end)
    addi     t3, zero, 10
    addi     t6, zero, 23
input_name_loop:
    beqz     t6, output_error
    lw       t4, 0(t0)
    beqz     t4, clean_input
    sub      t5, t4, t3
    beqz     t5, input_name_end
    sb       t4, 0(t2)
    addi     t2, t2, 1
    addi     t6, t6, -1
    j        input_name_loop
input_name_end:
    addi     sp, sp, -4
    sw       ra, 0(sp)
    jal      ra, finish_string
    lw       ra, 0(sp)
    addi     sp, sp, 4
    jr       ra

finish_string:
    addi     t4, zero, 33
    sb       t4, 0(t2)
    addi     t2, t2, 1
    mv       t4, zero
    sb       t4, 0(t2)
    jr       ra

clean_input:
    lw       t4, 0(t0)
    addi     t5, zero, 10
    sub      t5, t4, t5
    bnez     t5, clean_input
    j        input_name_end

output_name:
    lui      t2, %hi(buf_start)
    addi     t2, t2, %lo(buf_start)
    addi     t3, zero, 0x00FF
output_name_loop:
    lw       t4, 0(t2)
    and      t4, t4, t3
    beqz     t4, end
    sb       t4, 0(t1)
    addi     t2, t2, 1
    j        output_name_loop
output_name_end:
    jr       ra

output_error:
    lui      t2, %hi(overflow)
    addi     t2, t2, %lo(overflow)
    lw       t4, 0(t2)
    sw       t4, 0(t1)
    j        end


stack_init:
    addi     sp, zero, 0x400
    jr       ra

end:
    halt
