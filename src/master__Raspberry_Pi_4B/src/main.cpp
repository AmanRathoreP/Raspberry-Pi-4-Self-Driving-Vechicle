#include "main.h"

void foo();  // Declare the foo function
void foo2(); // Declare the foo function

int main()
{
    foo();
    Logger logger(LogLevel::INFO, "at \"" + std::string(__PRETTY_FUNCTION__) + "\" in \"" + std::string(__FILE__) + "\"");
    logger.enableFileOutput("logs.log");

    logger.log(LogLevel::WARNING, "Hello, world!");
    auto start = std::chrono::high_resolution_clock::now();
    double val = 1L;
    for (int i = 1; i < 1000000; i++)
    {
        for (short unsigned int j = 1; j < 254; j++)
        {
            val = val * i * j * i * j * i * j * i * j * i * j;
        }
        if (i % 100000 == 0)
        {
            logger.log(LogLevel::DEBUG, std::to_string(i) + " iterations completed!");
        }
    }

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> duration = end - start;

    logger.log("Hello, world!2");
    std::cout << "Time taken: " << duration.count() << " seconds\n";
    std::cout << GPIO_ON << std::endl;
    std::cout << (int)MAX_VALUE << std::endl;
    std::cout << val * -1 << std::endl;
    logger.disableFileOutput();
    foo2();
    return 0;
}

void foo(void)
{
    Logger logger(LogLevel::INFO, "at \"" + std::string(__PRETTY_FUNCTION__) + "\" in \"" + std::string(__FILE__) + "\"");
    logger.enableFileOutput("logs.log");

    logger.log("I reached to the function successfully!");
    logger.disableFileOutput();
}
void foo2(void)
{
    Logger logger(LogLevel::INFO, "at \"" + std::string(__PRETTY_FUNCTION__) + "\" in \"" + std::string(__FILE__) + "\"");
    logger.enableFileOutput("logs.log");

    logger.log("I reached to the function successfully!");
    logger.disableFileOutput();
}
