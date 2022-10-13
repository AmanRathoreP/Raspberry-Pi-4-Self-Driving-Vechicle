#include <stdio.h>

#include "pico/stdlib.h"
#include "hardware/gpio.h"

#include "FreeRTOS.h"
#include "FreeRTOSConfig.h"
#include "task.h"

#include "hardware/pwm.h"

#include <string.h>
#include <stdlib.h>

#include "global_vars.h"
#include "functions.h"

struct __MOTOR_CONTROLs
{
    struct __MOTOR
    {
        uint16_t speed;
        bool terminal_1;
        bool terminal_2;
    } motor_right, motor_left;
};

struct __SERIAL_INFO
{
    char direction;
    struct __MOTOR_CONTROLS_FROM_SERIAL
    {
        uint16_t speed;
    } motor_right, motor_left;
};

void __trigger_pwm_speed(uint8_t my_pin_1, uint8_t my_pin_2, uint16_t speed_1, uint16_t speed_2)
{
    /**
     * @brief this function takes two pins and also control their pulse width.
     * @param speed_1 this should be range from [0, 65535]
     * @param speed_2 this should be range from [0, 65535]
     *
     */

    static bool __called_or_not = false;
    static uint slice_num;
    slice_num = pwm_gpio_to_slice_num(my_pin_1);
    if (!(__called_or_not))
    {
        pwm_set_wrap(slice_num, 65535);
        __called_or_not = true;
    }
    pwm_set_chan_level(slice_num, PWM_CHAN_A, speed_1);
    pwm_set_chan_level(slice_num, PWM_CHAN_B, speed_2);
    pwm_set_enabled(slice_num, true);
}

void GreenLEDTask(void *param)
{
    while (true)
    {
        gpio_put(GREEN_LED, GPIO_OFF);
        vTaskDelay(30);
        gpio_put(GREEN_LED, GPIO_ON);
        vTaskDelay(100);
    }
}
void msgPrint(void *param)
{
    static unsigned int counter = 0;
    while (true)
    {
        printf("Seconds after start = %d\n", (counter++) / 10);
        vTaskDelay(100);
    }
}

void msgRead(void *param)
{
    char user_input[1024];
    while (true)
    {
        scanf("%1024s", user_input);
        printf("@%s@\n", user_input);

        if (strchr(user_input, '1') != NULL)
        {
            // gpio_put(RED_LED, GPIO_ON);
        }
        else if (strchr(user_input, '0') != NULL)
        {
            // gpio_put(RED_LED, GPIO_OFF);
        }
    }
}

void __get_vals_from_string(char *serial_str, struct __SERIAL_INFO *info)
{
    /**
     * @brief this function takes a string i.e. a char array of serial data received and provides speed and direction which was given by master(pi 4 in my case)
     *
     */
    char *token = strtok(serial_str, "-");

    info->direction = *token;
    token = strtok(NULL, "-");
    info->motor_right.speed = (uint16_t)(atoi(token));
    token = strtok(NULL, "-");
    info->motor_left.speed = (uint16_t)(atoi(token));
    token = strtok(NULL, "-");
}

void __get_motor_terminal_vals(char direction, struct __MOTOR_CONTROLs *motor)
{
    /**
     * @brief this function takes a char of direction and then according to that direction it provides different terminal values to  struct __MOTOR_CONTROLs *motor
     * @param direction direction in which you want vehicle to move
     * @param motor it is a address of you motor struct in which info will be stored
     * @param speed takes speed of the motor, range should be [0,1024] for best results
     */
    switch (direction)
    {
    case 'f':
        motor->motor_right.terminal_1 = GPIO_OFF;
        motor->motor_right.terminal_2 = GPIO_ON;
        motor->motor_left.terminal_1 = GPIO_OFF;
        motor->motor_left.terminal_2 = GPIO_ON;
        break;

    case 'b':
        motor->motor_right.terminal_1 = GPIO_ON;
        motor->motor_right.terminal_2 = GPIO_OFF;
        motor->motor_left.terminal_1 = GPIO_ON;
        motor->motor_left.terminal_2 = GPIO_OFF;
        break;

    case 'r':
        motor->motor_right.terminal_1 = GPIO_ON;
        motor->motor_right.terminal_2 = GPIO_OFF;
        motor->motor_left.terminal_1 = GPIO_OFF;
        motor->motor_left.terminal_2 = GPIO_ON;
        break;

    case 'l':
        motor->motor_right.terminal_1 = GPIO_OFF;
        motor->motor_right.terminal_2 = GPIO_ON;
        motor->motor_left.terminal_1 = GPIO_ON;
        motor->motor_left.terminal_2 = GPIO_OFF;
        break;

    default:
        motor->motor_right.terminal_1 = GPIO_OFF;
        motor->motor_right.terminal_2 = GPIO_OFF;
        motor->motor_left.terminal_1 = GPIO_OFF;
        motor->motor_left.terminal_2 = GPIO_OFF;
        break;
    }
}

void __mv_vehicle(struct __MOTOR_CONTROLs motor)
{
    /**
     * @brief takes in struct __MOTOR_CONTROLs motor and according to values it gives instructions to microcontroller
     * @param motor you struct __MOTOR_CONTROLs where all info of motor is stored
     */

    gpio_put(MOTORs_PINs_motor_right_terminal_1, motor.motor_right.terminal_1);
    gpio_put(MOTORs_PINs_motor_right_terminal_2, motor.motor_right.terminal_2);
    gpio_put(MOTORs_PINs_motor_left_terminal_1, motor.motor_left.terminal_1);
    gpio_put(MOTORs_PINs_motor_left_terminal_2, motor.motor_left.terminal_2);
    __trigger_pwm_speed(MOTORs_PINs_motor_right_speed, MOTORs_PINs_motor_left_speed, motor.motor_right.speed, motor.motor_left.speed);
}

void mv_vehicle(void *param)
{
    /**
     * @brief takes input from the serial. Generally input was given by pi 4
     *
     */
    char user_input[1024];
    struct __MOTOR_CONTROLs my_motor;
    struct __SERIAL_INFO my_motor_serial_instructions;

    __get_motor_terminal_vals('s', &my_motor);
    my_motor.motor_right.speed = 0;
    my_motor.motor_left.speed = 0;
    __mv_vehicle(my_motor);

    while (true)
    {
        scanf("%1024s", user_input);
        __get_vals_from_string(user_input, &my_motor_serial_instructions);

        printf("@@%c@%d@%d@@\n", my_motor_serial_instructions.direction, my_motor_serial_instructions.motor_right.speed, my_motor_serial_instructions.motor_left.speed);

        __get_motor_terminal_vals(user_input[0], &my_motor);
        my_motor.motor_right.speed = my_motor_serial_instructions.motor_right.speed;
        my_motor.motor_left.speed = my_motor_serial_instructions.motor_left.speed;
        __mv_vehicle(my_motor);
    }
}