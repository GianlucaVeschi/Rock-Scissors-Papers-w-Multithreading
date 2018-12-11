public class GameMainOnly {
	
	public static void main (String [] args){
		//all classes should be synchronized according to Table
		//the resource table should be locked, that means only one Thread at a time should be able to access it		
		Table table = new Table();

		Thread spieler1 = new Thread(new SpielerOne(table));
    	Thread spieler2 = new Thread(new SpielerTwo(table));
		Thread schiedsrichter = new Thread(new Schiedsrichter(table));
				
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

      	spieler1.interrupt();
    	spieler2.interrupt();
        schiedsrichter.interrupt();

      	table.showResults();

	}
}