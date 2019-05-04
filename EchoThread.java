import java.io.*; 
import java.net.*; 
import java.util.*;

public class EchoThread extends Thread {
    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;
    ArrayList<String> dictionary = new ArrayList<>();
    String mysteryWord;
    StringBuilder currentGuess;
    String answer = null;
    private Socket connectionSocket;
    public EchoThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }
    public void run() {
        
        
        Scanner inFromClient = null;
        DataOutputStream outToClient = null;
        try {
            inFromClient = new Scanner(connectionSocket.getInputStream());
            outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
            String clientSentence = inFromClient.nextLine(); 
            chooseWord();
            
            
            
            //Send answer to client
            answer = mysteryWord;
            outToClient.writeBytes(answer);         
            
        }
        catch (IOException e) {
            System.err.println("Closing Socket connection");
        }
        finally {
            try {
               if (inFromClient != null)
                  inFromClient.close();
               if (outToClient != null)
                  outToClient.close();
               if (connectionSocket != null)
                  connectionSocket.close();
               }
            catch (IOException e) {
               e.printStackTrace();
            }
        }
    }
    public void chooseWord() throws IOException {
        initializeStream();
        mysteryWord = pickWord();
        
    }
    public void initializeStream() throws IOException{
        try {
            File inFile = new File("dictionary.txt");
            fileReader = new FileReader(inFile);
            bufferedFileReader = new BufferedReader(fileReader);
            String currentLine = bufferedFileReader.readLine();
            while (currentLine != null) {
                dictionary.add(currentLine);
                currentLine = bufferedFileReader.readLine();
            }
            bufferedFileReader.close();
            fileReader.close();
        }
        catch (IOException e) {
            System.out.println("Could not init streams");
        }
    }
    public String pickWord() {
        Random rand = new Random();
        int wordIndex = Math.abs(rand.nextInt())%dictionary.size();
        return dictionary.get(wordIndex);
    }

}


