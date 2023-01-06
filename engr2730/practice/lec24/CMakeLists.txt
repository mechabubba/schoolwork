cmake_minimum_required(VERSION 3.14)
project(lec24)

set(CMAKE_CXX_STANDARD 14)

configure_file(data.txt data.txt COPYONLY)

add_executable(lec24 main.cpp)