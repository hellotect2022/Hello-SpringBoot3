package com.dhhan.demo.dto;

public class MemoryInfo {
    long maxMemory;
    long allocatedMemory;
    long freeMemory;

    public MemoryInfo(Runtime runtime) {
        this.maxMemory = runtime.maxMemory() / 1024 /1024;
        this.allocatedMemory = runtime.totalMemory() / 1024 /1024;
        this.freeMemory = runtime.freeMemory() / 1024 /1024;
    }

    public long getMaxMemory() {
        return maxMemory;
    }
    public long getAllocatedMemory() {
        return allocatedMemory;
    }
    public long getFreeMemory() {
        return freeMemory;
    }
}
