#ifndef FUNCTIONS_H
#define FUNCTIONS_H

struct __MOTOR_CONTROLs;

void GreenLEDTask(void *);
void msgPrint(void *);
void msgRead(void *);
void __get_motor_terminal_vals(char, struct __MOTOR_CONTROLs *);
void mv_vehicle(void *);
void __mv_vehicle(struct __MOTOR_CONTROLs);
void __trigger_pwm_speed(uint8_t, uint8_t, uint16_t, uint16_t);

#endif