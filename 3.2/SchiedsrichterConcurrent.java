public class SchiedsrichterConcurrent extends Thread {
	
  private TableConcurrent tableConcurrent;
	
  //Constuctor
  public SchiedsrichterConcurrent(TableConcurrent tableConcurrent) {
      this.tableConcurrent = tableConcurrent;  
  }

  public void run() {
    try{
      while(!isInterrupted()){
        tableConcurrent.checkTheWinnerAndNotify();    
      }
    }  
    catch(InterruptedException e){
      this.interrupt();//a thread that it should stop what it is doing and do something else
    }
  }
}