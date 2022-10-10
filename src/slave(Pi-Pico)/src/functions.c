#include <stdio.h>

#include "pico/stdlib.h"
#include "hardware/gpio.h"

#include "FreeRTOS.h"
#include "FreeRTOSConfig.h"
#include "task.h"

#include <string.h>

#include "global_vars.h"
#include "functions.h"

struct __MOTOR_CONTROLs
{
    struct __MOTOR
    {
        unsigned short int speed;
        bool terminal_1;
        bool terminal_2;
    } motor_right, motor_left;
};

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

void __get_motor_terminal_vals(char direction, struct __MOTOR_CONTROLs *motor)
{
    /**
     * @brief this function takes a char of direction and then according to that direction it provides different terminal values to  struct __MOTOR_CONTROLs *motor
     * @param direction direction in which you want vehicle to move
     * @param motor it is a address of you motor struct in which info will be stored
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
}

void mv_vehicle(void *param)
{
    /**
     * @brief takes input from the serial. Generally input was given by pi 4
     *
     */
    char user_input[1024];
    struct __MOTOR_CONTROLs my_motor;
    while (true)
    {
        scanf("%1024s", user_input);
        printf("@%c@\n", user_input[0]);

        __get_motor_terminal_vals(user_input[0], &my_motor);
        __mv_vehicle(my_motor);
    }
}