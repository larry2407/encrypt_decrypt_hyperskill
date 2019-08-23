package encryptdecrypt;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

   // private static Scanner sc = new Scanner(System.in); //STAGE 4
    private static Scanner sc = new Scanner(System.in); //STAGE 5

    public static void main(String[] args) {
        //System.out.println(firstEncrypt("we found a treasure!")); //STAGE 1
        //String[] commands = getCommandLineArgumentsFifthStage(args);
        String[] commands = getCommandLineArgumentsSixthStage(args);
        if(null == commands){
            System.out.println("no command line arguments");
            return;
        }else{
            for(int j=0; j<6; j++){
               if(null == commands[j]){
                   commands[j] = "";
               }
            }
        }
        boolean isInputFromKeyboard=false;
        if( commands[2].isEmpty() && commands[3].isEmpty()){
            String entry = sc.nextLine();
            commands[2] = entry;
            isInputFromKeyboard = true;
        }

        if(!commands[2].isEmpty() || !commands[3].isEmpty()) {
            String action = commands[0]; //sc.nextLine();
            String inputStrFile = commands[2].isEmpty() ? commands[3] : commands[2];
            String inputStr="";
            if(isInputFromKeyboard){
                inputStr = inputStrFile;
            }
            String keyStr = commands[1];//sc.nextLine();
            String alg = commands[5];
            if(!action.equals("enc") && !action.equals("dec") || !keyStr.matches("^-?\\d+$") || !alg.equals("shift") && !alg.equals("unicode")){
                System.out.println("Either action or key or algorithm have wrong arguments! (or any among those three)");
                return;
            }
            int key = Integer.valueOf(keyStr);
            if(commands[2].isEmpty()){//which means that commands[3] is not empty
                // read from the file indicated in input
                try {
                    inputStr = new String(Files.readAllBytes(Paths.get(inputStrFile)));
                } catch (IOException e) {
                    System.out.printf("An exception occurred %s", e.getMessage());
                }
            }

            if (commands[4].isEmpty() || commands[4].isBlank()){
                System.out.println(CypherFactory.make(inputStr, alg, key, action.equals("enc")).generateOutput());
                /*if (action.equals("enc")) {
                    System.out.println(thirdEncrypt(inputStr, key)); //STAGE 3
                } else if (action.equals("dec")) {
                    System.out.println(thirdDecrypt(inputStr, key)); //STAGE 3
                }*/
          }else{
                File file = new File(commands[4]);
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(CypherFactory.make(inputStr, alg, key, action.equals("enc")).generateOutput());
                    /*if (action.equals("enc")) {
                        writer.write(thirdEncrypt(inputStr, key)); //STAGE 5
                    } else if (action.equals("dec")) {
                        writer.write(thirdDecrypt(inputStr, key)); //STAGE 5
                    }*/
                } catch (IOException e) {
                    System.out.printf("An exception occurred %s", e.getMessage());
                }
            }
        }
    }

    private static String firstEncrypt(String str){
        int l = str.length();
        char[] strCharArr = str.toCharArray();
        char[] output = new char[l];
        for(int i=0; i<l; i++){
            int current = (int) strCharArr[i];
            if(current>=97 && current<=122) {
                output[i] = (char) (96 + 123 - current);
            }else{
                output[i] = strCharArr[i];
            }
        }
        return new String(output);
    }

    private static String secondEncrypt(String str, int key){
        int l = str.length();
        char[] strCharArr = str.toCharArray();
        char[] output = new char[l];
        for(int i=0; i<l; i++){
            int current = (int) strCharArr[i];
            if(current>=97 && current<=122) {
                int code = current + key <= 122 ? current + key : current + key - 122 + 96;
                output[i] = (char) (code);
            }else{
                output[i] = strCharArr[i];
            }
        }
        return new String(output);
    }

    private static String thirdEncrypt(String str, int key){
        int l = str.length();
        char[] strCharArr = str.toCharArray();
        char[] output = new char[l];
        for(int i=0; i<l; i++){
            int current = (int) strCharArr[i];
            int code = current + key ;
            output[i] = (char) (code);
        }
        return new String(output);
    }

    private static String thirdDecrypt(String str, int key){
        int l = str.length();
        char[] strCharArr = str.toCharArray();
        char[] output = new char[l];
        for(int i=0; i<l; i++){
            int current = (int) strCharArr[i];
            int code = current - key;
            output[i] = (char) (code);
            }
        return new String(output);
    }
    //STAGE 4
    private static String[] getCommandLineArguments(String [] argsInput){
        String[] results = new String[3];
        for(int i=0; i<argsInput.length; i++){
            if(argsInput[i].equals("-mode")){
                String mode = argsInput[i+1].isEmpty() || argsInput[i+1].isBlank() ? "enc" : argsInput[i+1];
                results[0] = mode;
            }else if(argsInput[i].equals("-key")){
                String key = argsInput[i+1];
                results[1] = key;
            }else if(argsInput[i].equals("-data")) {
                String data = argsInput[i + 1];
                results[2] = data;
            }
        }
        return results;
    }
    //STAGE 5
    private static String[] getCommandLineArgumentsFifthStage(String [] argsInput){
        String[] results = new String[5];
        for(int i=0; i<argsInput.length; i++){
            if(argsInput[i].equals("-mode")){
                String mode = argsInput[i+1].isEmpty() || argsInput[i+1].isBlank() ? "enc" : argsInput[i+1];
                results[0] = mode;
            }else if(argsInput[i].equals("-key")){
                String key = argsInput[i+1];
                results[1] = key;
            }else if(argsInput[i].equals("-data")) {
                String data = argsInput[i + 1];
                results[2] = data;
            }else if(argsInput[i].equals("-in")){
                String in = argsInput[i + 1];
                results[3] = in;
            }else if(argsInput[i].equals("-out")){
                String out = argsInput[i + 1];
                results[4] = out;
            }
        }
        return results;
    }
    //STAGE 6
    private static String[] getCommandLineArgumentsSixthStage(String [] argsInput){
        String[] results = new String[6];
        for(int i=0; i<argsInput.length; i++){
            if(argsInput[i].equals("-mode")){
                String mode = argsInput[i+1].isEmpty() || argsInput[i+1].isBlank() ? "enc" : argsInput[i+1];
                results[0] = mode;
            }else if(argsInput[i].equals("-key")){
                String key = argsInput[i+1];
                results[1] = key;
            }else if(argsInput[i].equals("-data")) {
                String data = argsInput[i + 1];
                results[2] = data;
            }else if(argsInput[i].equals("-in")){
                String in = argsInput[i + 1];
                results[3] = in;
            }else if(argsInput[i].equals("-out")){
                String out = argsInput[i + 1];
                results[4] = out;
            }else if(argsInput[i].equals("-alg")){
                String alg = argsInput[i + 1];
                results[5] = alg;
            }
        }
        return results;
    }
}
/* Product - Cypher */
abstract class Cypher {

    String input;
    String alg;
    int key;
    boolean isEncoding;// true <==> enc ; false <==> dec

    public Cypher(String input, String alg, int key, boolean isEncoding) {
        this.input = input;
        this.alg = alg;
        this.key = key;
        this.isEncoding =isEncoding;
    }

    abstract String generateOutput(/*String input, String alg, int key, boolean isEncoding*/) ;
}
   class CypherConcrete extends Cypher {
    CypherConcrete(String input, String alg, int key, boolean isEncoding){
        super(input, alg, key, isEncoding);
    }

    @Override
    String generateOutput(/*String input, String alg, int key, boolean isEncoding*/) {
        int actualKey = isEncoding ? key : -key;
        int l = input.length();
        char[] strCharArr = input.toCharArray();
        char[] output = new char[l];
        if(alg.equals("shift")){
            for(int i=0; i<l; i++){
                int current = (int) strCharArr[i];
                int code = 0;
                if(current>=97 && current<=122) {
                    int intermediary = current - 97 + actualKey >= 0 ? (current - 97 + actualKey)%26 : (26+(2*(current - 97) -((current - 97 - actualKey)%26)))%26 ;
                    code = intermediary + 97;
                    output[i] = (char) (code);
                }else{
                    output[i] = strCharArr[i];
                }
            }

        }else{
            for(int i=0; i<l; i++){
                int current = (int) strCharArr[i];
                int code = current + actualKey;
                output[i] = (char) (code);
            }

        }

        return new String(output);
    }

}
class CypherFactory {


    public static Cypher make(String input, String alg, int key, boolean isEncoding) {
        return new CypherConcrete(input, alg, key, isEncoding);
    }
}