cmake_minimum_required(VERSION 3.6.0)

find_program(CCACHE ccache)

if (CCACHE)
    set(CMAKE_CXX_COMPILER_LAUNCHER ${CCACHE})
    set(CMAKE_C_COMPILER_LAUNCHER ${CCACHE})
endif ()

set(CMAKE_CXX_FLAGS "-std=c++14 -DANDROID -g")
set(CMAKE_C_FLAGS "-w -std=c11 -DANDROID -D_LARGEFILE_SOURCE=1 -g")
set(CMAKE_ASM_FLAGS "${CFLAGS} -x assembler-with-cpp")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -ffunction-sections -fdata-sections")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -ffunction-sections -fdata-sections")
set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,--exclude-libs,libtgvoip.a,libtgcalls.a,libtgcalls_tp.a,libtgnet.a,liblz4.a,libwebp.a,libflac.a,librlottie.a,libsqlite.a,
${CMAKE_HOME_DIRECTORY}/ffmpeg/${ANDROID_ABI}/libswscale.a,
${CMAKE_HOME_DIRECTORY}/ffmpeg/${ANDROID_ABI}/libavformat.a,
${CMAKE_HOME_DIRECTORY}/ffmpeg/${ANDROID_ABI}/libavcodec.a,
${CMAKE_HOME_DIRECTORY}/ffmpeg/${ANDROID_ABI}/libavresample.a,
${CMAKE_HOME_DIRECTORY}/ffmpeg/${ANDROID_ABI}/libavutil.a,
${CMAKE_HOME_DIRECTORY}/ffmpeg/${ANDROID_ABI}/libswresample.a,
${CMAKE_HOME_DIRECTORY}/ffmpeg/${ANDROID_ABI}/libvpx.a,
${CMAKE_HOME_DIRECTORY}/boringssl/lib/libssl_${ANDROID_ABI}.a,
${CMAKE_HOME_DIRECTORY}/boringssl/lib/libcrypto_${ANDROID_ABI}.a")

if (${ANDROID_ABI} STREQUAL "armeabi-v7a" OR ${ANDROID_ABI} STREQUAL "arm64-v8a")
    enable_language(ASM)
else()
    enable_language(ASM_NASM)
endif()