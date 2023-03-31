#pragma once

#include <iostream>
#include <fstream>
#include <chrono>
#include <iomanip>

enum class LogLevel
{
    INFO,
    WARNING,
    ERROR,
    DEBUG
};
/**
 * @brief Logger is a simple C++ logger that can log messages with a specified log level,
 * location, and message format to the console and/or to a file. It supports the
 * following log levels: INFO, DEBUG, WARNING, and ERROR.
 * @code
    Logger logger(LogLevel::INFO, "at \"" + std::string(__PRETTY_FUNCTION__) + "\" in \"" + std::string(__FILE__) + "\"");
    logger.enableFileOutput("logs.log");
    logger.log(LogLevel::WARNING, "x = %d, y = %.4f!", 324234, 2.3432);
    logger.log("logged without a specifying the log level", 324234, 2.3432);
    logger.disableFileOutput();
 * @endcode
 */
class Logger
{
public:
    /**
     * @brief a Logger object with a specified log level.
     * @param level The log level of the logger.
     * @param location Defines the place the object is created in order to log stuff accordingly
     */
    Logger(LogLevel level, std::string location) : m_logLevel(level) { msg_logged_at = location; }
    /**
     * @brief Enables file output for the logger.
     * @param filename The name of the file to output logs to.
     */
    void enableFileOutput(const std::string &filename)
    {
        m_logToFile = true;
        m_fileStream.open(filename, std::ios::out | std::ios::app);
    }
    /**
     * @brief Disables file output for the logger.
     */
    void disableFileOutput()
    {
        m_logToFile = false;
        if (m_fileStream.is_open())
        {
            m_fileStream.close();
        }
    }
    /**
     * @brief Logs a message with the specified log level, location, and message format.
     * @param level The log level of the message.
     * @param format The format of the message.
     * @param args The arguments to the message format.
     */
    template <typename... Args>
    void log(LogLevel level, const std::string &format, Args... args)
    {
        if (level >= m_logLevel)
        {
            std::stringstream message;
            message << "[ " << level_to_string(level) << " ][ " << getTimestamp() << " ][ " << msg_logged_at << " ]: " << format << std::endl;
            if (m_logToFile)
            {
                m_fileStream << message.str();
            }
            std::cout << message.str();
        }
    }
    /**
     * @brief Logs a message with the default log level i.e, INFO, location, and message format.
     * @param format The format of the message.
     * @param args The arguments to the message format.
     */
    template <typename... Args>
    void log(const std::string &format, Args... args)
    {
        log(LogLevel::INFO, format);
    }

private:
    LogLevel m_logLevel;
    bool m_logToFile;
    std::string msg_logged_at;
    std::ofstream m_fileStream;
    /**
     * @brief Converts a LogLevel enum value to its string representation.
     * @param level The LogLevel to convert.
     * @return The string representation of the LogLevel.
     */
    std::string level_to_string(LogLevel level)
    {
        switch (level)
        {
        case LogLevel::INFO:
            return "INFO   ";
        case LogLevel::DEBUG:
            return "DEBUG  ";
        case LogLevel::WARNING:
            return "WARNING";
        case LogLevel::ERROR:
            return "ERROR  ";
        default:
            return "";
        }
    }
    /**
     * @brief Returns the current timestamp in ISO 8601 format.
     * @return The current timestamp in ISO 8601 format.
     */
    std::string getTimestamp()
    {
        auto now = std::chrono::system_clock::now();
        auto now_c = std::chrono::system_clock::to_time_t(now);
        std::stringstream ss;
        ss << std::put_time(std::localtime(&now_c), "%Y-%m-%d %H:%M:%S");
        return ss.str();
    }
};
