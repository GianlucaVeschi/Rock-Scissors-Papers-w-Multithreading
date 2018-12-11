//Referee
public class Schiedsrichter extends Thread {
	private Table table;
	
  public Schiedsrichter(Table table) {
    this.table = table;  
  }

  public void run() {
    try{
      while(!isInterrupted()){
        table.checkTheWinnerAndNotify();    
      }
    }  
    catch(InterruptedException e){
      this.interrupt();//a thread that it should stop what it is doing and do something else
    }
  }
}