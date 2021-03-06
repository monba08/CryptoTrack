import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;


public class Accounts {

    public static ArrayList<String> loginArray = new ArrayList<>();

    private static Accounts accountInstance = null;
    public static Accounts getAccountInstance() {
        if(accountInstance == null)
            accountInstance=new Accounts();
        return accountInstance;
    }
    private Accounts(){} //Private constructor


    public void createNewAccount(String registNaam,String registPass,String coinNaam, int valueC, int quantityCoin) throws IOException {
        String name = registNaam;
        String pass =registPass;
        Coin.getCoinInstance().setNameCoin(coinNaam);
        Coin.getCoinInstance().setQuantityCoin(quantityCoin);

        String userFile = name + ".txt"; //Tekstbestand aanmaken met als 'naam' de naam van de gebruiker.
        File fout = new File(userFile);
        FileOutputStream fos = new FileOutputStream(fout, true); //Append parameter, zodat oude data niet verdwijnt. Boolean argument
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        //Info wegschrijven naar bestand van de gebruiker.
        bw.write(name);
        bw.write(" ");
        bw.write(pass);
        bw.write(" ");
        bw.write(coinNaam);
        bw.write(" ");
        bw.write(Integer.toString(valueC));
        bw.write(" ");
        bw.write(Integer.toString(quantityCoin));
        bw.write(" ");

        //Hiermee zullen de daily values van een coin worden weggeschreven.
        bw.write("\n");
        bw.write(coinNaam);
        bw.write(" ");
        bw.write(Integer.toString(valueC)+" EndOfLine");
        bw.close();
        fos.close();
    }



    public boolean logIn(String userName, String password) throws IOException {
        boolean login = false;

        File fileList= new File("list.txt"); //List.txt checken of dat de ingegeven naam bestaat of niet.
        Scanner scanFile = new Scanner(fileList);
        String currentLine="";
        boolean check=false;
        while(scanFile.hasNextLine() && !check) {
            currentLine=scanFile.nextLine();
            if (currentLine.contains(userName)) {
                //List.txt moet gecheckt worden.
                System.out.println("User exists: " + userName);
                check = true;
            }
        }
        scanFile.close();

        if (check) {
            String userFile1 = userName + ".txt";

            BufferedReader fileIn = new BufferedReader(new FileReader(userFile1));
            String line = fileIn.readLine();
            String parts[] = line.split(" ");
            String user = parts[0];
            int gr = parts.length;
            System.out.println("Grootte is: " + gr);
            if (userName.equals(user)) {
                String pass = parts[1];
                if (password.equals(pass)) {
                    login = true;
                }

                if (login) {
                    System.out.print("Wilkommen\n");
                    loginArray.clear();
                    //Gegevens gebruiker in een arraylist steken.
                    if (parts.length == 2 && !(parts.length >= 5)) {
                        loginArray.add(parts[0]);
                        loginArray.add(parts[1]);
                    } else if (parts.length == 5) {
                        loginArray.add(parts[0]);
                        loginArray.add(parts[1]);
                        loginArray.add(parts[2]);
                        loginArray.add(parts[3]);
                        loginArray.add(parts[4]);
                    } else if (parts.length >= 8) {
                        for (int j = 0; j < parts.length; j++) {
                            loginArray.add(parts[j]);

                        }
                    }
                    for (String st : loginArray) {
                        System.out.println("LoginArray: " + st);
                    }
                    //User.txt wordt beschouwd als de huidige gebruiker die is ingelogd. Dus we schrijven de gegevens opnieuw weg maar deze keer naar User.txt
                    String currentUser = loginArray.get(0);
                    System.out.println("Currentuser:" + currentUser);
                    FileWriter writer = new FileWriter("User.txt");
                    Writer output = new BufferedWriter(writer);


                    for (String stringuser : loginArray) {
                        output.write(stringuser);
                        output.write(" ");
                    }
                    output.close();
                    login= true;
                } else {
                    System.out.print("User doesn't exist");
                    login= false;
                }
            }
        }
        else {
            System.out.println("User not found");
        }
        return login;
    }
}
