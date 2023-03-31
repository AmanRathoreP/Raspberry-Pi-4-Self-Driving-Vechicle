#include "../src/src/common/logger.hpp"
#include <iostream>
#include <chrono>

#define number_or_iterations_for_testing 100000
int main()
{
    Logger logger(LogLevel::INFO, "at \"" + std::string(__PRETTY_FUNCTION__) + "\" in \"" + std::string(__FILE__) + "\"");
    logger.enableFileOutput("logger_speed_test.log");

    auto start = std::chrono::high_resolution_clock::now();

    for (double i = 1; i <= number_or_iterations_for_testing; ++i)
    {
        logger.log(LogLevel::WARNING, "Logging message number " + std::to_string(i));
    }

    auto end = std::chrono::high_resolution_clock::now();
    auto duration_ms = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();
    std::cout << "Time taken to log " << number_or_iterations_for_testing << " messages: " << duration_ms << "ms" << std::endl;

    logger.disableFileOutput();

    return 0;
}
