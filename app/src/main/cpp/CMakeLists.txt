# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

project("OpencvLib")

include_directories(${CMAKE_SOURCE_DIR}/../../sdk/native/jni/include)  # Racine des headers

add_library(${CMAKE_PROJECT_NAME} SHARED custom.cpp)

# Déclare OpenCV comme lib prebuilt (IMPORTED)
add_library(opencv_java4 SHARED IMPORTED)
set_target_properties(opencv_java4 PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../../sdk/native/libs/${ANDROID_ABI}/libopencv_java4.so)

target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log
        opencv_java4
)