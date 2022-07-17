//
// Created by qwq233 on 6/19/2022.
//
#include <stdio.h>
#include <unistd.h>

#if defined(__aarch64__) || defined(__x86_64__)
const char so_interp[] __attribute__((used, section(".interp"))) = "/system/bin/linker64";
#elif defined(__i386__) || defined(__arm__)
const char so_interp[] __attribute__((used, section(".interp"))) = "/system/bin/linker";
#else
#error Unknown Arch
#endif

#ifndef GJZS_VERSION
#error Please define macro GJZS_VERSION in CMakeList
#endif

__attribute__((used, noreturn, section(".entry_init")))
void libgjzs_main(void) {
    printf("GJZS R version "
    GJZS_VERSION
    ".\n"
    "Copyright (C) 2022-2022 qwq233@qwq2333.top\n"
    "This software is distributed in the hope that it will be useful,\n"
    "but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
    "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n");
    _exit(0);
}