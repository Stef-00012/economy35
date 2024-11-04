package org.cup.engine.core;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;



public class PerformanceMonitor extends Thread {
    private static final OperatingSystemMXBean OS_BEAN = ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean.class);
            
    private static int nMonitors = 0;

    public final double MAX_MEMORY_USAGE = .2; //? <- 20% of the system memory is CRAZYYY
    public final double MAX_CPU_USAGE = .3; // +30% is not uncommon as soon as the game starts
    public final double MAX_THREAD_COUNT = 50; // swing threads + game threads + sounds
    
    public long sleepTimeMillis = 1000;
    
    // automatically call the garbage collector
    // when too much RAM space is being used
    public final boolean autoClean;
    
    /**
     * The class uses the {@link com.sun.management.OperatingSystemMXBean} interface to access system-level metrics.
     * <p>
     * This interface is part of the Java Management Extensions (JMX) API, which provides access to various system-level information.
     * <ul>
     * <li> The {@link #getCpuUsage()} method returns the current CPU usage as a percentage.
     * <li> The {@link #getMemoryUsage()} method returns the current memory usage in bytes.
     * <li> The {@link #getHeapUsage()} method returns the current heap usage as a percentage.
     * <li> The {@link #getThreadCount()} method returns the current number of active threads.
     * </ul>
     * */
    public PerformanceMonitor(boolean autoClean){
        super("PerformanceMonitor-" + nMonitors);
        nMonitors++;

        this.autoClean = autoClean;
    }

    public PerformanceMonitor(boolean autoClean, long sleepTimeMillis){
        this(autoClean);
        this.sleepTimeMillis = sleepTimeMillis;
    }
    

    public static double getCpuUsage() {
        return OS_BEAN.getProcessCpuLoad();
    }

    public static double getMemoryUsage() {
        return OS_BEAN.getSystemLoadAverage();
    }

    public static long getMemoryUsageBytes() {
        return OS_BEAN.getTotalMemorySize() - OS_BEAN.getFreeMemorySize();
    }

    public static double getHeapUsage() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        return (double) usedMemory / maxMemory;
    }

    public static long getThreadCount() {
        return Thread.getAllStackTraces().keySet().size();
    }

    public static void ForceGC() {
        System.gc();
    }

    @Override
    public void run() {
        Debug.log("PerformanceMonitor is active");
        while(true) {
            if (getCpuUsage() > MAX_CPU_USAGE && MAX_CPU_USAGE > 0) {
                Debug.engineLogErr("eccessive cpu usage: " + getCpuUsage() * 100 + "%");
            }
            
            if (getThreadCount() > MAX_THREAD_COUNT && MAX_THREAD_COUNT > 0) {
                Debug.engineLogErr("eccessive thread number: " + getThreadCount());
            }

            if (getMemoryUsage() > MAX_MEMORY_USAGE && MAX_MEMORY_USAGE > 0) {
                Debug.engineLogErr(
                    "eccessive memory usage: " + getMemoryUsageBytes() + "B (" + getMemoryUsage() * 100 + "%)");

                if (autoClean){
                    ForceGC();
                    Debug.engineLog("Called garbage collector (new memory usage: " + getMemoryUsage() + "%)");
                }
            }

            try {
                Thread.sleep(sleepTimeMillis);
            } catch (InterruptedException e) {
                Debug.engineLogErr(e.getMessage());
                return;
            }
        }
    }

}
