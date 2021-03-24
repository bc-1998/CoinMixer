import java.io.*;
import java.util.Properties;

public class Util {

    /*
     * utility function to prompt user to input new, unused Jobcoin addresses
     */
    public static String promptForClientAddressInput() throws IOException, TerminatedException {
        var line = "";
        try {
            while (true) {
                String prompt = "Please enter a comma-separated list of new, unused Jobcoin addresses where your mixed Jobcoins will be sent.";
                System.out.println(prompt);
                // Enter data using BufferReader
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                // Reading data using readLine
                line = reader.readLine();
                // Printing the read line
                System.out.println(line);

                if ("".equals(line)) {
                    System.out.println("You must specify empty addresses to mix into!");
                } else if ("quit".equalsIgnoreCase("line")) {
                    throw new TerminatedException("Jobcoin mixer app has been terminated.");
                }
                break;
            }
        } catch (TerminatedException ce) {
            System.out.println("Quitting...");
            System.out.print(ce.toString());
        } finally {
            return line;
        }
    }

    /*
     * utility function to prompt user to enter Jobcoin amount to be deposited.
     */
    public static String promptForClientTransferAmount() throws IOException {
        var line = "";
        var amount = 0.0;
        while (true) {
            String prompt = "Please enter number of Jobcoin to be deposited: ";
            System.out.println(prompt);
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            // Reading data using readLine
            line = reader.readLine();
            // Printing the read line
            System.out.println(line);

            try {
                if (!("".equals(line))) {
                    amount = Double.valueOf(line).doubleValue();
                    if (amount < 0.0)
                        System.out.println("The entered amount has to be greater than 0");
                    else
                        break;
                }
            } catch (NumberFormatException ne) {
                System.out.println(ne.toString());
            }
        }
        return "" + amount;
    }

    /*
     * utility function to display unique deposit address for user to transfer fund.
     */
    public static void promptForDepositAddress(String depositAddress) {
        System.out.println("The deposit address is " + depositAddress);
        String promptSend = "You may now send Jobcoins to address " + depositAddress + ". They will be mixed and sent to your destination addresses.";
        System.out.println(promptSend);
    }

    /*
     * utility function to prompt user to enter primary wallet's address.
     */
    public static String promptForAccountAddress() throws IOException {
        var line = "";
        while (true) {
            String prompt = "Please enter your primary wallet's address: ";
            System.out.println(prompt);
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            // Reading data using readLine
            line = reader.readLine();

            if ("".equals(line)) {
                System.out.println("You must specify your primary wallet's address");
            } else {
                System.out.println(line);
                break;
            }
        }
        return line;
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }
}

class TerminatedException extends Exception{
    private String str;

    TerminatedException(String str) {
        this.str=str;
    }
    public String toString(){
        return ("CompletedException Occurred: "+str) ;
    }
}

