cmake_minimum_required(VERSION 3.14)
project(hw1)

set(CMAKE_CXX_STANDARD 14)
configure_file(files/aliceSequence.txt files/aliceSequence.txt COPYONLY)
configure_file(files/dna.txt files/dna.txt COPYONLY)

add_executable(hw1 main.cpp)
