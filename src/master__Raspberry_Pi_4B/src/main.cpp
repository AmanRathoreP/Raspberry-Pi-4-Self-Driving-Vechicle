#include "main.h"

void foo();  // Declare the foo function
void foo2(); // Declare the foo function

int main()
{
    Logger logger("logs.log");
    logger.log("Hello, world!");
    auto start = std::chrono::high_resolution_clock::now();
    double val = 1L;
    for (int i = 1; i < 1000000; i++)
    {
        for (short unsigned int j = 1; j < 254; j++)
        {
            val = val * i * j * i * j * i * j * i * j * i * j;
        }
    }

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> duration = end - start;

    logger.log("Hello, world!2");
    std::cout << "Time taken: " << duration.count() << " seconds\n";
    std::cout << GPIO_ON << std::endl;
    std::cout << MAX_VALUE << std::endl;
    std::cout << val * -1 << std::endl;
    return 0;
}
