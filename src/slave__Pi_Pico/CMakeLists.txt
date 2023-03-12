cmake_minimum_required(VERSION 3.12)

# # Pull in PICO SDK (must be before project)
include(pico_sdk_import.cmake)
project(slave_for_SDC C CXX ASM)
set(CMAKE_C_STANDARD 11)
set(CMAKE_CXX_STANDARD 17)

# set(PICO_EXAMPLES_PATH ${PROJECT_SOURCE_DIR})

# # Initialize the SDK
pico_sdk_init()

include_directories(${PROJECT_SOURCE_DIR})

add_executable(main
    src/main.c
    src/global_vars.c
    src/functions.c)

FILE(GLOB FreeRTOS_src FreeRTOS-Kernel/*.c)

add_library(FreeRTOS STATIC
    ${FreeRTOS_src}
    FreeRTOS-Kernel/portable/GCC/ARM_CM0/port.c
    FreeRTOS-Kernel/portable/MemMang/heap_4.c
)

pico_enable_stdio_usb(main 1)
pico_enable_stdio_uart(main 0)
pico_add_extra_outputs(main)
target_link_libraries(main pico_stdlib hardware_gpio hardware_pwm
    FreeRTOS)

target_include_directories(FreeRTOS PUBLIC
    FreeRTOS-Kernel/include
    include/
    FreeRTOS-Kernel/portable/GCC/ARM_CM0
)