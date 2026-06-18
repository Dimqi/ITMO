.data

input_addr:      .word  0x80      ; адрес ввода
output_addr:     .word  0x84      ; адрес вывода
n:               .word  0x00      ; переменная для числа
count:           .word  0x00      
const_1:         .word  0x01      
const_32:        .word  0x20      

    .text

_start:
    load         input_addr        ; acc = адрес ввода
    load_acc                       ; acc = само число
    
    beqz         zero_case         ; if acc==0 => output = 32
    store        n                 ; сохраняем число в n
    jmp main   

main:
    load         n
    and          const_1

    bnez         output_result     ; если последний бит 0, вывести count

    load         n
    shiftr       const_1
    store        n
    load         count
    add          const_1
    store        count

    jmp          main 

zero_case:
    load         const_32          ; acc = 32
    store        count             ; count = 32

output_result:
    load         count             ; acc = результат
    store_ind    output_addr       ; сохраняем по адресу вывода
    halt


    
