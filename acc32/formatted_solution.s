    .data

input_addr:      .word  0x80
output_addr:     .word  0x84
n:               .word  0x00
count:           .word  0x00
const_1:         .word  0x01
const_32:        .word  0x20

    .text

_start:
    load         input_addr
    load_acc

    beqz         zero_case
    store        n
    jmp          main

main:
    load         n
    and          const_1

    bnez         output_result

    load         n
    shiftr       const_1
    store        n
    load         count
    add          const_1
    store        count

    jmp          main

zero_case:
    load         const_32
    store        count

output_result:
    load         count
    store_ind    output_addr
    halt

