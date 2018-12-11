public class SpielerOneConcurrent extends Thread {
  
  private TableConcurrent tableConcurrent;
  
  //Constructor
  public SpielerOneConcurrent(TableConcurrent tableConcurrent) {
      this.tableConcurrent = tableConcurrent;
  }
    
  //Method Run 
  public void run() {
    try{
      while(!isInterrupted()){
        tableConcurrent.play(1);
      }
    }
    catch(InterruptedException e){
      this.interrupt();
    } 	
  }	
}
