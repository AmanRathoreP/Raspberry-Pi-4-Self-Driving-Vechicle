#include "global_vars.h"

const struct __MOTORs_PIN
{
    struct __MOTOR_PIN
    {
        unsigned short int terminal_1;
        unsigned short int terminal_2;
    } motor_right, motor_left;
} MOTORs_PINs = {16, 17, 18, 19};

const short int MOTORs_PINs_motor_right_terminal_1 = MOTORs_PINs.motor_right.terminal_1;
const short int MOTORs_PINs_motor_right_terminal_2 = MOTORs_PINs.motor_right.terminal_2;
const short int MOTORs_PINs_motor_left_terminal_1 = MOTORs_PINs.motor_left.terminal_1;
const short int MOTORs_PINs_motor_left_terminal_2 = MOTORs_PINs.motor_left.terminal_2;

const bool GPIO_ON = 1;
const bool GPIO_OFF = 0;
const unsigned short int GREEN_LED = 25;
