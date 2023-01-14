#ifndef GLOBAL_VARS_H
#define GLOBAL_VARS_H

#include <stdbool.h> //* to use boolean data type
#include <stdint.h>  //* to use uint8_t, uint16_t, uint32, etc. data types

extern const uint8_t GREEN_LED;
extern const uint8_t LEFT_INDICATOR;
extern const uint8_t RIGHT_INDICATOR;
extern const uint8_t STOP_INDICATOR;
extern const uint8_t BUZZER;
extern const bool GPIO_ON;
extern const bool GPIO_OFF;

extern const uint8_t MOTORs_PINs_motor_right_terminal_1;
extern const uint8_t MOTORs_PINs_motor_right_terminal_2;
extern const uint8_t MOTORs_PINs_motor_left_terminal_1;
extern const uint8_t MOTORs_PINs_motor_left_terminal_2;

extern const uint16_t MOTORs_PINs_motor_right_speed;
extern const uint16_t MOTORs_PINs_motor_left_speed;

#endif