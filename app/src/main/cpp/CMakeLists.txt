# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

project("OpencvLib")

set(OpenCV_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../../sdk/native/jni)

find_package(OpenCV REQUIRED)

message(STATUS "OpenCV trouvé, version : ${OpenCV_VERSION}")

add_library(${CMAKE_PROJECT_NAME} SHARED custom.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}
        ${OpenCV_LIBS}
        android
        log
)