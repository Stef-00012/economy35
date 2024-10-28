package org.cup.engine.core;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;



public class PerformanceMonitor extends Thread {
    private static final OperatingSystemMXBean OS_BEAN = ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean.class);
            
    private static int nMonitors = 0;

    public final double MAX_MEMORY_USAGE = .3;
    public final double MAX_CPU_USAGE = 3;
    public final double MAX_THREAD_COUNT = 3;
    
    public long sleepTimeMillis = 1000;
    
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
    public PerformanceMonitor(){
        super("PerformanceMonitor-" + nMonitors);
        nMonitors++;
    }

    public PerformanceMonitor(long sleepTimeMillis){
        this();
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
        System.out.println("active ");
        while(true) {
            if (getCpuUsage() > MAX_CPU_USAGE) {
                Debug.engineLogErr("eccessive cpu usage: " + getCpuUsage() * 100 + "%");
            }
            
            if (getThreadCount() > MAX_THREAD_COUNT) {
                Debug.engineLogErr("eccessive thread number: " + getThreadCount());
            }

            if (getMemoryUsage() > MAX_MEMORY_USAGE) {
                Debug.engineLogErr(
                    "eccessive memory usage: " + getMemoryUsageBytes() + "B (" + getMemoryUsage() * 100 + "%)");
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
