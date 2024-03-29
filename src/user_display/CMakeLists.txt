cmake_minimum_required(VERSION 3.16)

project(user_display VERSION 0.1 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_AUTORCC ON)

find_package(Qt6 6.4 REQUIRED COMPONENTS Quick Network Concurrent)

qt_standard_project_setup()

set( project_sources
   src/src/qt/myappsettingsclass.h
   src/src/qt/myappsettingsclass.cpp
   src/src/qt/vehiclecommunication.h
   src/src/qt/vehiclecommunication.cpp
)

qt_add_executable(appuser_display
    resources.qrc
    src/main.cpp
    ${project_sources}
)

qt_add_qml_module(appuser_display
    URI user_display
    VERSION 1.0
    QML_FILES
        "src/ui/Main.qml"
        "src/ui/pages/home.qml"
        "src/ui/pages/vehicle-logs.qml"
        "src/ui/pages/app-logs.qml"
        "src/ui/pages/app-settings.qml"
        "src/ui/others/control-tabs/Semi-Automatic.qml"
        "src/ui/others/control-tabs/Automatic.qml"
        "src/ui/others/control-tabs/Manual.qml"
)

set_target_properties(appuser_display PROPERTIES
    MACOSX_BUNDLE_GUI_IDENTIFIER my.example.com
    MACOSX_BUNDLE_BUNDLE_VERSION ${PROJECT_VERSION}
    MACOSX_BUNDLE_SHORT_VERSION_STRING ${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}
    MACOSX_BUNDLE TRUE
    WIN32_EXECUTABLE TRUE
)

target_link_libraries(appuser_display
    PRIVATE Qt6::Quick
    PRIVATE Qt6::Network
    PRIVATE Qt6::Concurrent
)

install(TARGETS appuser_display
    BUNDLE DESTINATION .
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})
