public class SpielerOne extends Thread {
  private Table table;
  //Constructor
  public SpielerOne(Table table) {
      this.table = table;
  }
    
  //Method Run 
  public void run() {
    try{
      while(!isInterrupted()){
        table.play(1);
      }
    }
    catch(InterruptedException e){
      this.interrupt();
    }

    /*
    for (int i = 0; i < 5; i++) {
   	  table.play(1);
    } 
    */ 	
  }	
}
