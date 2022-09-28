#include <stdio.h>

#include "pico/stdlib.h"
#include "hardware/gpio.h"

#include "FreeRTOS.h"
#include "FreeRTOSConfig.h"
#include "task.h"

#define GREEN_LED 25
#define RED_LED 15
#define BLUE_LED 16

#define GPIO_ON 1
#define GPIO_OFF 0

void GreenLEDTask(void *param)
{
    while (true)
    {
        gpio_put(GREEN_LED, GPIO_ON);
        vTaskDelay(100);
        gpio_put(GREEN_LED, GPIO_OFF);
        vTaskDelay(100);
    }
}

void BlueLEDTask(void *param)
{
    while (true)
    {
        gpio_put(BLUE_LED, GPIO_ON);
        vTaskDelay(150);
        gpio_put(BLUE_LED, GPIO_OFF);
        vTaskDelay(250);
    }
}
void RedLEDTask(void *param)
{
    while (true)
    {
        gpio_put(RED_LED, GPIO_ON);
        vTaskDelay(700);
        gpio_put(RED_LED, GPIO_OFF);
        vTaskDelay(400);
    }
}

int main()
{
    stdio_init_all();

    gpio_init(GREEN_LED);
    gpio_set_dir(GREEN_LED, GPIO_OUT);

    gpio_init(RED_LED);
    gpio_set_dir(RED_LED, GPIO_OUT);

    gpio_init(BLUE_LED);
    gpio_set_dir(BLUE_LED, GPIO_OUT);
    gpio_put(BLUE_LED, GPIO_OFF);

    TaskHandle_t gLEDtask = NULL;
    TaskHandle_t rLEDtask = NULL;
    TaskHandle_t bLEDtask = NULL;

    xTaskCreate(
        GreenLEDTask,
        "Green LED",
        1024,
        NULL,
        tskIDLE_PRIORITY,
        &gLEDtask);

    xTaskCreate(
        RedLEDTask,
        "Red LED",
        1024,
        NULL,
        tskIDLE_PRIORITY,
        &rLEDtask);

    xTaskCreate(
        BlueLEDTask,
        "Blue LED",
        1024,
        NULL,
        1,
        &bLEDtask);

    vTaskStartScheduler();

    for (;;)
    {
        // should never get here
    }
}