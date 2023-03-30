#include <iostream>
#include <chrono>

void foo();  // Declare the foo function
void foo2(); // Declare the foo function

int main()
{
    foo();
    std::cout << "Hello from main" << std::endl;
    foo2();
    return 0;
}
