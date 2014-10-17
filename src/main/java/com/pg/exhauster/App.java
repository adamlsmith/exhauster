package com.pg.exhauster;
import java.util.Vector;
import java.io.*;
import java.util.*;
import java.lang.management.*;
import com.sun.management.OperatingSystemMXBean;
/**
 * This is an tool for consuming system resources
 * Memory, CPU and disk space can be exhausted based on configuration
 * @author Adam L Smith
 */
public class App
{
	private int  availableProcessors = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
	private long lastSystemTime      = 0;
	private long lastProcessCpuTime  = 0;
	private static Vector<CpuEater> threads = new Vector<CpuEater>();

	public static void main(String[] args)
	{
		App a = new App();

		if(args.length == 0)
		{
			System.out.println("USAGE:\nPlease pass either file, cpu or mem as an argument.  For memory, followed by a decimal percentage cap\nExample:\nTO FILL 95% of the heap:  java -jar <this jar> mem 0.995\n");
			System.exit(0);
		}
			System.out.println(Runtime.getRuntime().availableProcessors() + " processors found");
		
		if(args[0].equalsIgnoreCase("cpu")){
			while(true){
				double f = a.getCpuUsage();
				System.out.println("CPU Usage: " + (100*f) + "%");
				CpuEater c = new CpuEater();
				new Thread(new CpuEater()).start();
				threads.add(c);
			}
		}


		if(args[0].equalsIgnoreCase("mem")){
			  if(a.getJVMArgs().size() == 0){
				  System.out.println("No JVM arguments specified, exiting");
				  System.out.println("Please specify a heap size and increment which are the same, i.e. java -Xmx5120m -Xms5120m");
				  System.exit(0);
			  }

			float cap = Float.parseFloat(args[1]);

			//create an object to build up memory allocation for
			Vector<byte[]> v = new Vector<byte[]>();

			
			//loop forever
			while(true)
			{
				//garbage collect
				System.gc();
				//print memory stats to stdout
				System.out.println(a.getMemoryUsage(cap));
				//if less than cap % of heap is allocated, add more data to memory
				if (a.getUsed() <= (a.getCommitted()*cap))
				{
				    //add 1mb to the object
				    byte b[] = new byte[1048576];
				    v.add(b);
				}
			}
			}


		if(args[0].equalsIgnoreCase("file")){

			  /* Get a list of all filesystem roots on this system */
			    File[] roots = File.listRoots();

			/* For each filesystem root, print some info */
			    for (File root : roots) {
				  System.out.println("File system root: " + root.getAbsolutePath());
				  System.out.println("Total space (bytes): " + root.getTotalSpace());
			          System.out.println("Free space (bytes): " + root.getFreeSpace());
			          System.out.println("Usable space (bytes): " + root.getUsableSpace());
			}

			System.out.println("Not implemented");
			System.exit(0);

		}
	}

	protected float getUsed(){
		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		MemoryUsage mu = mbean.getHeapMemoryUsage();
		return mu.getUsed();
	}

	protected float getCommitted(){
		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		MemoryUsage mu = mbean.getHeapMemoryUsage();
		return mu.getCommitted();
	}

	protected String getMemoryUsage(float cap)
	{
		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		Runtime rt = Runtime.getRuntime();
		MemoryUsage mu = mbean.getHeapMemoryUsage();
		//figure out the maximum memory to allocate
		String ret = ( "Heap usage: " + (mu.getUsed()/1024/1024) + "mb, "
			+ "Free memory: "  + (rt.freeMemory()/1024/1024)+"mb, max heap: " 
			+ (mu.getMax()/1024/1024)+"mb, "
			+ " max committed: " + (mu.getCommitted()/1024/1024) + "mb, "
			+ ((100 * mu.getUsed())/mu.getCommitted()) + "% of committed, "
			+ ((100 * mu.getUsed())/getPhysicalMemory()) + "% of physical");
		return ret;
	}


	protected List<String> getJVMArgs(){
			//get jvm args
			  RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
			  return runtimeMxBean.getInputArguments();
	}


	protected long getPhysicalMemory(){
			//get OS info
			com.sun.management.OperatingSystemMXBean bean =
			 (com.sun.management.OperatingSystemMXBean)
			  java.lang.management.ManagementFactory.getOperatingSystemMXBean();
			  return bean.getTotalPhysicalMemorySize();
	}

	public synchronized double getCpuUsage()
	{
		if ( lastSystemTime == 0 )
		{
			baselineCounters();
		}

		long systemTime     = System.nanoTime();
		long processCpuTime = 0;

		if ( ManagementFactory.getOperatingSystemMXBean() instanceof OperatingSystemMXBean )
		{
			processCpuTime = ( (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean() ).getProcessCpuTime();
		}

		double cpuUsage = (double) ( processCpuTime - lastProcessCpuTime ) / ( systemTime - lastSystemTime );

		lastSystemTime     = systemTime;
		lastProcessCpuTime = processCpuTime;

		return cpuUsage / availableProcessors;
	}

	private void baselineCounters()
	{
		lastSystemTime = System.nanoTime();
		if ( ManagementFactory.getOperatingSystemMXBean() instanceof OperatingSystemMXBean )
		{
			lastProcessCpuTime = ( (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean() ).getProcessCpuTime();
		}
	}
}
class CpuEater implements Runnable {
	public void run(){
		while(true){

			//do some calculations
			double output = 0.0;
			boolean positive = true; // don't know this
			int input = 10000;
				    
			for (int i=0; i<input; i++) { 
			      double sum = 1.0/(2.0*((double)i) + 1.0); //how no idea how this works
			      if (positive) 
				    output += sum; //same as output = output + sum
			      else 
				    output -= sum; // same as output = output = output - sum
			       positive = !positive; // Don't know this
			}
		}
	}
}
