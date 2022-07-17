cmake_minimum_required(VERSION 3.10)
project(gjzs)

find_program(CCACHE ccache)

if (CCACHE)
    set(CMAKE_CXX_COMPILER_LAUNCHER ${CCACHE})
    set(CMAKE_C_COMPILER_LAUNCHER ${CCACHE})
endif ()

enable_language(CXX C)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_C_STANDARD 11)

find_library( # Defines the name of the path variable that stores the
        # location of the NDK library.
        ANDROID_LIBS
        # Specifies the name of the NDK library that
        # CMake needs to locate.
        log)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Werror=format-invalid-specifier -Werror=return-type -Wno-invalid-offsetof")
if ("${CMAKE_CXX_COMPILER_ID}" MATCHES "Clang")
    SET(CLANG_CXX_EXTRA_OPT "-Werror=unknown-warning-option -Werror=format-invalid-specifier -Werror=call-to-pure-virtual-from-ctor-dtor")
    SET(CLANG_C_EXTRA_OPT "-Werror=format-invalid-specifier")
else ()
    SET(CLANG_CXX_EXTRA_OPT "")
    SET(CLANG_C_EXTRA_OPT "")
endif ()

set(CMAKE_C_FLAGS "-w -std=c11 -DANDROID -D_LARGEFILE_SOURCE=1 -g")
set(CMAKE_ASM_FLAGS "${CFLAGS} -x assembler-with-cpp")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -ffunction-sections -fdata-sections")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -ffunction-sections -fdata-sections")

set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,-llog,--no-allow-shlib-undefined,--no-undefined")


set(NATIVE_LIB "gjzs.1")

add_library(${NATIVE_LIB} SHARED
        jni.c
        version.c)
target_compile_definitions(${NATIVE_LIB} PRIVATE GJZS_VERSION=\"${GJZS_VERSION}\")

# signature verification
include(${CMAKE_HOME_DIRECTORY}/integrity/CMakeLists.txt)


target_link_libraries(
        ${NATIVE_LIB}
        ${ANDROID_LIBS}
        genuine)
