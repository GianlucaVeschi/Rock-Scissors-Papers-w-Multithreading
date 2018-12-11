public class SpielerTwo extends Thread {
	//private GSpieler gspieler;
	private Table table;
	//Constructor
	public SpielerTwo(Table table) {
      this.table = table;
  }
   	
   	//Method Run 
  public void run() {

    try{
      while(!isInterrupted()){
        table.play(2);
      }
    }
    catch(InterruptedException e){
      this.interrupt();
    }
    /*
    for (int i = 0; i < 5; i++) {
   	    table.play(2);
    }
    */
  }	
}