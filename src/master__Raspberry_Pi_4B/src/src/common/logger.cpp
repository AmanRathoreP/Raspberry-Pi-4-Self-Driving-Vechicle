#include "logger.h"

Logger::Logger(const std::string &filename)
{
    m_outputStream.open(filename, std::ios::out | std::ios::app);
}

Logger::~Logger()
{
    m_outputStream.close();
}

void Logger::log(const std::string &message)
{
    m_outputStream << message << std::endl;
}
