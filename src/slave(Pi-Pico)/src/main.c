#include <stdio.h>

#include "pico/stdlib.h"
#include "hardware/gpio.h"

#include "FreeRTOS.h"
#include "FreeRTOSConfig.h"
#include "task.h"

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

    // xTaskCreate(
    //     msgPrint,
    //     "Msg sending over USB",
    //     1024,
    //     NULL,
    //     tskIDLE_PRIORITY,
    //     &msgHandler);

    // xTaskCreate(
    //     msgRead,
    //     "Msg over USB in terms of reading",
    //     1024,
    //     NULL,
    //     tskIDLE_PRIORITY,
    //     &msgHandler_read);

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