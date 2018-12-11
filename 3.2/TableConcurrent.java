//imports
import java.util.Random;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
 
class TableConcurrent {
	//Internal fields are two enums
	private Hand handOne;
	private Hand handTwo;

    //variables needed to summarize the results
	private int playerOneVictories = 0;
	private int playerTwoVictories = 0;
	private int totalMatches = 0;

	//variables needed to create random value
	private final Hand[] VALUES = Hand.values();
	private final int SIZE = VALUES.length;
	private Random random;

	//variables needed to creaate the synchronization
	private ReentrantLock lock = new ReentrantLock();
    private Condition cond = lock.newCondition(); // provide a means for one thread to suspend execution 
    											  //(to "wait") until notified by another thread that some 
    											  //state condition may now be true
	
	//Getters
    public Hand getHandOne(){
       	return handOne;
    }
    public Hand getHandTwo(){
    	return handTwo;
    }
    //Setters
	public void setHandOne(Hand hand){
		this.handOne = hand;
	}

	public void setHandTwo(Hand hand){
		this.handTwo = hand;
	}

	//used to simulate the game
	public Hand getRandomHand()  {	
		// contains all values inside the Enum Hand
		random = new Random(); // we always need a new random
    	return VALUES[random.nextInt(SIZE)];
  	}

  	//method called by the thread spieler
  	public void play(int handOneOrTwo) throws InterruptedException{			
	  	lock.lock(); //acquire the lock, similar to synchronize(this)
	  	try{
		  	Hand randomHand = this.getRandomHand();
		  	if(handOneOrTwo == 1){
			   	this.setHandOne(randomHand);
		  	} 
		  	else if (handOneOrTwo == 2){
		  		this.setHandTwo(randomHand);
		  	}
		  	cond.await(); //similar to wait()	
	  	}
	  	finally{
	  		lock.unlock();	
	  	}
	  	
		
  	} 
  	public void checkTheWinnerAndNotify() throws InterruptedException{
  		
        Thread.sleep(300);//um sicherzustellen,dass die zwei Spieler fertig sind
        lock.lock();
  		
  		//Recommended to always immediately follow a call to lock with a try block
  		try{
  			int result = this.theWinnerIs(handOne,handTwo);	
  			String winner = (result > 0) ? "Spieler1" : (result < 0) ? "Spieler2" : " nobody";
	 		System.out.println("Spieler1 plays: " + handOne + ", Spieler2 plays: " + handTwo + " so " + winner + " wins");
	  		this.emptyTable();
	  		cond.signalAll();	
  		}
  		finally{
  			lock.unlock();
	  	}
   	}

	public int theWinnerIs(Hand handOne, Hand handTwo){
		this.totalMatches++;
		//Unentschieden		
		if(handOne == handTwo){
			return 0;	
		}
		try{
			switch(handOne){
				case STEIN:
					if(handTwo == Hand.SCHERE){
						this.playerOneVictories++;
						
						return 1;
					}	
					else{
						this.playerTwoVictories++;
						
						return -1;
					}
		        case PAPIER:
		            if(handTwo == Hand.STEIN){
						this.playerOneVictories++;
						return 1;
					}	
					else{
						this.playerTwoVictories++;
						return -1;
					}
		        case SCHERE:
		             if(handTwo == Hand.PAPIER){
						this.playerOneVictories++;
						return 1;
					}	
					else{
						this.playerTwoVictories++;
						return -1;
					}  	
				default:
					return 0;	
			}
		}
		catch(Exception e){
			System.err.println("NULL VALUE for Hand in the match " + totalMatches);
			return 0;
		}
	}

	public void emptyTable(){
		this.handOne = null;
		this.handTwo = null;	
	}
	public void showResults(){
		int ties = totalMatches - playerOneVictories - playerTwoVictories;
		System.out.println();
		System.out.println("Anzahl totalMatches: " + totalMatches); 
		System.out.println("Anzahl Unentschieden: " + ties);
		System.out.println("Anzahl Gewinne Spieler1: " + playerOneVictories);
		System.out.println("Anzahl Gewinne Spieler2: " + playerTwoVictories);
	}
}

enum Hand {
	    SCHERE,STEIN,PAPIER; 
}