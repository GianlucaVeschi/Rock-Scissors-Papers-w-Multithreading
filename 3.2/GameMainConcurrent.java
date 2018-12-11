import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GameMainConcurrent {
	
	public static void main (String [] args){
		//all classes should be synchronized according to Table
		//the resource table should be locked, that means only one Thread at a time should be able to access it		
		TableConcurrent table = new TableConcurrent();

    //Initialize the threads
		Thread spieler1 = new Thread(new SpielerOneConcurrent(table));
    Thread spieler2 = new Thread(new SpielerTwoConcurrent(table));
		Thread schiedsrichter = new Thread(new SchiedsrichterConcurrent(table));
				
		spieler1.start();
		spieler2.start();
    schiedsrichter.start();

    //regulates how long the Spielers play
    try{  
      Thread.sleep(4000);	
    }
    catch(Exception e){ 
      /* Threads beenden */
      System.err.println("error in the main");
    }

    //Interrupt the threads 
    spieler1.interrupt();
    spieler2.interrupt();
    schiedsrichter.interrupt();

    table.showResults();
	
}}