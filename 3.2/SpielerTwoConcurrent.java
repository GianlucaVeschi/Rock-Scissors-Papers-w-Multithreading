public class SpielerTwoConcurrent extends Thread {

	private TableConcurrent tableConcurrent;

	//Constructor
	public SpielerTwoConcurrent(TableConcurrent tableConcurrent) {
      this.tableConcurrent = tableConcurrent;
  }
   	
  //Method Run 
  public void run() {
    try{
      while(!isInterrupted()){
        tableConcurrent.play(2);
      }
    }
    catch(InterruptedException e){
      this.interrupt();
    }
  }	
}