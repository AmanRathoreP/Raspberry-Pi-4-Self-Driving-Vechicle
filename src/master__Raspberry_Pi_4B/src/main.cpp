#include <iostream>
#include <chrono>

void foo();  // Declare the foo function
void foo2(); // Declare the foo function

int main()
{
    foo();
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

    std::cout << "Time taken: " << duration.count() << " seconds\n";
    std::cout << val * -1 << std::endl;
    foo2();
    return 0;
}
