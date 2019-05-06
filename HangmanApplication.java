import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class HangmanApplication {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        Scanner sc = new Scanner(System.in);
        String answer = null;
        
        boolean startgame = false;
        
             
        boolean doYouWantToPlay = true;     
        Socket clientSocket = null;
        DataOutputStream outToServer = null;
        Scanner inFromServer = null;
        String inFromUser = null ;
        String inSecFromUser = null;
             
        try {  

                
               
            while(!startgame){
                System.out.println("Welcome to hangman! I will pick a word and you will guess it character by character.\n" +
                            "If you guess it wrong 6 times, then I win. If you can guess it before, then you win"
                            );
                
                System.out.println();
                System.out.println("I have picked a word and below is a picture. Below is your current guess " +
                            "That starts as nothing.\n Every time you guess incorrectly, I will add a body part.\n" +
                            "When its a full person, you lose.");
                System.out.println();
                   
                System.out.println("Do you want to play a Hangman Games Y/N ");
                
                inSecFromUser = sc.nextLine();
                if(inSecFromUser.equals("Y") || inSecFromUser.equals("y")){ 
                    System.out.println("");
                    startgame = true;
                       
                }
                else{
                    System.out.println("Programe will exit ");
                    doYouWantToPlay = false;
                    startgame = true;
                }
            }

        

            while (doYouWantToPlay) {
                clientSocket = new Socket("192.168.10.161", 1234);
                outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
                inFromServer = new Scanner(clientSocket.getInputStream());
                
                System.out.println();
                System.out.println("Alright, Lets play");
                
                outToServer.writeBytes(inFromUser+'\n');
                answer = inFromServer.nextLine(); 
                Hangman game = new Hangman(answer);

            
           
          do {
                
                System.out.println();
                System.out.println(game.drawPicture());
                System.out.println();
                game.drawGuess();
                System.out.println();
                System.out.println(game.getFormalCurrentGuess());
                System.out.println();

                // Get the guess
                System.out.println("Enter a character that you think is in the word ");
                
                char guess = (sc.next().toLowerCase()).charAt(0);
                
                System.out.println();

                // Check if the character has been guessed before
                boolean checkGuesed = game.isGuessedAlready(guess);
                if (checkGuesed) {
                    while(checkGuesed){
                    System.out.println("Try again. You have guessed this character already");
                    guess = (sc.next().toLowerCase()).charAt(0);
                        if(game.previousGuesses.contains(guess)){
                            checkGuesed = true;
                        }
                        else checkGuesed = false;
                    }
                    
                }

                if (game.playGuess(guess)) {

                    System.out.println("Great guess. That character is in in the word");
                }
                else {

                    System.out.println("Unfortunately that word is not in the guess");
                }
                
               
                
            }
            
            while (!game.gameOver());

            System.out.println();
            
            System.out.println("Do you want to play another game Y/N ");
            
            boolean check = true;
            while(check){
                inSecFromUser = sc.next();
                if(inSecFromUser.equals("Y") || inSecFromUser.equals("y")){
                    doYouWantToPlay = true;
                    check = false;
                }
                else if(inSecFromUser.equals("N") || inSecFromUser.equals("n")){
                    System.out.println("Thack you for playing our game ");
                    doYouWantToPlay = false;
                    check = false;
                }
                else{
                    System.out.println("Please, Try again ");
                }
            }
            
            
        }
  
        }
        catch (IOException e) {
            System.out.println("Error occurred: Closing the connection");
        }
        finally {
            try {
                if (inFromServer != null)
                    inFromServer.close();
                if (outToServer != null)
                    outToServer.close();
                if (clientSocket != null)
                    clientSocket.close();
                }
            catch (IOException e) {
                e.printStackTrace();
            }
        }      
        

    }

    
    
}


