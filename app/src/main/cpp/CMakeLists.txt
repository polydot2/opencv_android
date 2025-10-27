# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

project("opencv")

include_directories(${CMAKE_SOURCE_DIR}/include)

# In order to load a library into your app from Java/Kotlin, you must call
# System.loadLibrary() and pass the name of the library defined here;
add_library(${CMAKE_PROJECT_NAME} SHARED
        native-lib.cpp)

# Déclare OpenCV comme lib prebuilt (IMPORTED)
add_library(opencv_java4 SHARED IMPORTED)
set_target_properties(opencv_java4 PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libopencv_java4.so)

# Specifies libraries CMake should link to your target library. You
# can link libraries from various origins, such as libraries defined in this
# build script, prebuilt third-party libraries, or Android system libraries.
target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log
        opencv_java4)