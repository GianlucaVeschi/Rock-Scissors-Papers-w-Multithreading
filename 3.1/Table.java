//imports
import java.util.Random;

class Table {
	//Internal fields are two enums
	private Hand handOne;
	private Hand handTwo;

	private int playerOneVictories = 0;
	private int playerTwoVictories = 0;
	private int totalMatches = 0;

	//variables needed to create random value
	private final Hand[] VALUES = Hand.values();
	private final int SIZE = VALUES.length;
	private Random random;

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

	public Hand getRandomHand()  {	
		// contains all values inside the Enum Hand
		random = new Random(); // we always need a new random
    	return VALUES[random.nextInt(SIZE)];
  	}

  	public void play(int handOneOrTwo) throws InterruptedException{
  		synchronized(this){ // synchronize the Spieler on the this table object			
	  			Hand randomHand = this.getRandomHand();
	  			if(handOneOrTwo == 1){
		    		this.setHandOne(randomHand);
	  			} 
	  			else if (handOneOrTwo == 2){
	  				this.setHandTwo(randomHand);
	  			}
				wait(); //wait until the Table is free again
	  	}
  	} 
  	public void checkTheWinnerAndNotify() throws InterruptedException{
  		
          Thread.sleep(100);//um sicherzustellen,dass die zwei Spieler fertig sind
        
  		synchronized(this){ // synchronize the Schiedsrichter on the this table object
  			int result = this.theWinnerIs(handOne,handTwo);	
  			String winner = (result > 0) ? "Spieler1" : (result < 0) ? "Spieler2" : " nobody";
	 		System.out.println("Spieler1 plays: " + handOne + ", Spieler2 plays: " + handTwo + " so " + winner + " wins");
	  		this.emptyTable(); 
	  		notifyAll();		
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
			System.out.println("Der Schiedsrichter ist schneller als die Spieler gewesen");
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