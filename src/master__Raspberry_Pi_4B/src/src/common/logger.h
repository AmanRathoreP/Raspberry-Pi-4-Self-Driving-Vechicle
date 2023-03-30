#pragma once

#include <iostream>
#include <fstream>
#include <string>

class Logger
{
public:
    Logger(const std::string &filename);
    ~Logger();
    void log(const std::string &message);

private:
    std::ofstream m_outputStream;
};
