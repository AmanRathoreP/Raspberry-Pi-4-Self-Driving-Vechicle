#include <stdio.h>

#include "pico/stdlib.h"
#include "hardware/gpio.h"

#include "FreeRTOS.h"
#include "FreeRTOSConfig.h"
#include "task.h"

//! #include <stdint.h> //* to use int 32
#include <string.h>

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

        if ((strchr(user_input, '0') != NULL) & (strchr(user_input, '1') != NULL))
        {
            // printf("Ambiguous input!\n");
            // printf(user_input);
            // printf("\n");
            // printf(strcat(user_input, "\nAmbiguous input!\n"));
        }
        else if (strchr(user_input, '1') != NULL)
        {
            gpio_put(RED_LED, GPIO_ON);
        }
        else if (strchr(user_input, '0') != NULL)
        {
            gpio_put(RED_LED, GPIO_OFF);
        }
        else
        {
            // printf(strcat(user_input, "\n"));
            // printf(user_input);
            // printf("\n");
        }
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

    TaskHandle_t msgHandler = NULL;
    TaskHandle_t msgHandler_read = NULL;

    xTaskCreate(
        GreenLEDTask,
        "Green LED (Built-in)",
        1024,
        NULL,
        tskIDLE_PRIORITY,
        &gLEDtask);

    // xTaskCreate(
    //     RedLEDTask,
    //     "Red LED",
    //     1024,
    //     NULL,
    //     tskIDLE_PRIORITY,
    //     &rLEDtask);

    xTaskCreate(
        BlueLEDTask,
        "Blue LED",
        1024,
        NULL,
        1,
        &bLEDtask);

    // xTaskCreate(
    //     msgPrint,
    //     "Msg sending over USB",
    //     1024,
    //     NULL,
    //     tskIDLE_PRIORITY,
    //     &msgHandler);

    xTaskCreate(
        msgRead,
        "Msg over USB in terms of reading",
        1024,
        NULL,
        tskIDLE_PRIORITY,
        &msgHandler_read);

    vTaskStartScheduler();

    for (;;)
    {
        // should never get here
    }
}