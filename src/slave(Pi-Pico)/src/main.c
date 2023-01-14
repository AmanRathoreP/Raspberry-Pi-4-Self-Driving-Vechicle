#include <stdio.h>

#include "pico/stdlib.h"
#include "hardware/gpio.h"

#include "FreeRTOS.h"
#include "FreeRTOSConfig.h"
#include "task.h"

#include "hardware/pwm.h"

#include <string.h>

#include "global_vars.h"
#include "functions.h"

int main()
{
    stdio_init_all();

    gpio_init(GREEN_LED);
    gpio_set_dir(GREEN_LED, GPIO_OUT);

    gpio_init(MOTORs_PINs_motor_right_terminal_1);
    gpio_set_dir(MOTORs_PINs_motor_right_terminal_1, GPIO_OUT);
    gpio_init(MOTORs_PINs_motor_right_terminal_2);
    gpio_set_dir(MOTORs_PINs_motor_right_terminal_2, GPIO_OUT);
    gpio_init(MOTORs_PINs_motor_left_terminal_1);
    gpio_set_dir(MOTORs_PINs_motor_left_terminal_1, GPIO_OUT);
    gpio_init(MOTORs_PINs_motor_left_terminal_2);
    gpio_set_dir(MOTORs_PINs_motor_left_terminal_2, GPIO_OUT);
    gpio_init(LEFT_INDICATOR);
    gpio_set_dir(LEFT_INDICATOR, GPIO_OUT);
    gpio_init(RIGHT_INDICATOR);
    gpio_set_dir(RIGHT_INDICATOR, GPIO_OUT);
    gpio_init(STOP_INDICATOR);
    gpio_set_dir(STOP_INDICATOR, GPIO_OUT);
    gpio_init(BUZZER);
    gpio_set_dir(BUZZER, GPIO_OUT);

    gpio_set_function(MOTORs_PINs_motor_right_speed, GPIO_FUNC_PWM);
    gpio_set_function(MOTORs_PINs_motor_left_speed, GPIO_FUNC_PWM);

    TaskHandle_t gLEDtask = NULL;

    TaskHandle_t msgHandler = NULL;
    TaskHandle_t msgHandler_read = NULL;

    TaskHandle_t movement_controller = NULL;

    xTaskCreate(
        GreenLEDTask,
        "Green LED (Built-in)",
        1024,
        NULL,
        1,
        &gLEDtask);

    xTaskCreate(
        mv_vehicle,
        "This task controls vehicle's movements",
        1024,
        NULL,
        tskIDLE_PRIORITY,
        &movement_controller);

    vTaskStartScheduler();

    for (;;)
    {
        // should never get here
    }
}