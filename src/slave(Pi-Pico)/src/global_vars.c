#include "global_vars.h"

const struct __MOTORs_PIN
{
    struct __MOTOR_PIN
    {
        uint8_t terminal_1;
        uint8_t terminal_2;
        uint16_t speed;

    } motor_right, motor_left;
} MOTORs_PINs = {16, 17, 0, 18, 19, 1};

const uint8_t MOTORs_PINs_motor_right_terminal_1 = MOTORs_PINs.motor_right.terminal_1;
const uint8_t MOTORs_PINs_motor_right_terminal_2 = MOTORs_PINs.motor_right.terminal_2;
const uint8_t MOTORs_PINs_motor_left_terminal_1 = MOTORs_PINs.motor_left.terminal_1;
const uint8_t MOTORs_PINs_motor_left_terminal_2 = MOTORs_PINs.motor_left.terminal_2;
const uint16_t MOTORs_PINs_motor_right_speed = MOTORs_PINs.motor_right.speed;
const uint16_t MOTORs_PINs_motor_left_speed = MOTORs_PINs.motor_left.speed;

const bool GPIO_ON = 1;
const bool GPIO_OFF = 0;
const uint8_t GREEN_LED = 25;
const uint8_t LEFT_INDICATOR = 14;
const uint8_t RIGHT_INDICATOR = 15;
const uint8_t STOP_INDICATOR = 11;
const uint8_t BUZZER = 10;
