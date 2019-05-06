
import java.io.IOException;
import java.util.ArrayList;


public  class Hangman {
    String mysteryWord = null;
    String question;
    StringBuilder currentGuess;
    String os;
    ArrayList<Character> previousGuesses = new ArrayList<>();
   
    int maxTries = 6;
    int currentTry = 0;

    
    public Hangman(String answer){
        
        mysteryWord = answer;
        System.out.println("Word length is "+mysteryWord.length());
        currentGuess = initializeCurrentGuess();
    }
 
    
    public StringBuilder initializeCurrentGuess() {
        StringBuilder current = new StringBuilder();
        for (int i=0; i < (mysteryWord.length())*2; i++) {
            if (i%2 == 0) {
                current.append("_");
            }
            else {
                current.append(" ");
            }
        }
        return current;
    }

    public String getFormalCurrentGuess() {
        return "Current guess: " + currentGuess.toString();
    }

    public boolean gameOver() throws IOException, InterruptedException {
        if (didWeWin()) {
            //clearConsole();
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            System.out.println("Congrats. You won the game");
        }
        else if (didWeLose()) {
            //clearConsole();
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            System.out.println("Sorry. You lost. You used all your 6 chances. \n" +
                                " The mystery word was: " + mysteryWord + ".");
        }
        return didWeWin() || didWeLose();
    }

    public boolean didWeWin() {
        String guess = getCondensedCurrentGuess();
        return guess.equals(mysteryWord);
    }

    public String getCondensedCurrentGuess() {
        String guess = currentGuess.toString();
        return guess.replace(" ", "");
    }

    public boolean didWeLose() {
        return currentTry >= maxTries;
    }

    public boolean isGuessedAlready(char guess) {
        return previousGuesses.contains(guess);
    }

    public boolean playGuess(char guess) {
        boolean isItAGoodGuess = false;
        previousGuesses.add(guess);
        for (int i=0;i<mysteryWord.length();i++) {
            if (mysteryWord.charAt(i) == guess) {
                currentGuess.setCharAt(i*2, guess);
                isItAGoodGuess = true;
            }
        }
        if (!isItAGoodGuess) {
            currentTry++;
        }
        return isItAGoodGuess;
    }

    public String drawPicture() {
        switch(currentTry) {
            case 0: return noPersonDraw();
            case 1: return addHeadDraw();
            case 2: return addBodyDraw();
            case 3: return addOneArmDraw();
            case 4: return addSecondArmDraw();
            case 5: return addFirstLegDraw();
            default: return fullPersonDraw();
        }

    }
    

    
    private String noPersonDraw() {
        return " - - - - -\n"+
                "|        |\n"+
                "|        \n" +
                "|       \n"+
                "|        \n" +
                "|       \n" +
                "|\n" +
                "|\n";
    }

    private String addHeadDraw() {
        return " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|       \n"+
                "|        \n" +
                "|       \n" +
                "|\n" +
                "|\n";
    }

    private String addBodyDraw() {
        return " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|        | \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    private String addOneArmDraw() {
        return   " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / |  \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    private String addSecondArmDraw() {
        return  " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    private String addFirstLegDraw() {
        return   " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|       / \n" +
                "|\n" +
                "|\n";
    }

    private String fullPersonDraw() {
        return   " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|       / \\ \n" +
                "|\n" +
                "|\n";
    }
    public void drawGuess() {
        System.out.println("You already use this letter ");
        System.out.println(previousGuesses);
        
    }
    
    /*private void clearConsole() throws IOException {
        Thread clear = new Thread(ClearConsole());
        clear.start();
    }

    /*private Runnable ClearConsole() throws IOException {
        String os;
        os = System.getProperty("os.name");
        if(os.contains("windows")){
            Runtime.getRuntime().exec("cls");
        }
        else{
            Runtime.getRuntime().exec("cler");
        }
        return null;
    } */
    
    
}
 
    
