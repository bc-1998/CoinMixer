import java.io.IOException;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        try {
            //prompt user to input new, unused Jobcoin addresses
            String line = Util.promptForClientAddressInput();
            String[] addresses = line.split(",");
            System.out.println(Arrays.toString(addresses));

            //prompt user to enter primary wallet's address.
            String accountAddress = Util.promptForAccountAddress();

            //prompt user to enter amount of Jobcoins to deposit
            var amount = Util.promptForClientTransferAmount();
            String depositAddress = Mixer.getDepositAddress();

            //display unique deposit address for user to deposit fund.
            Util.promptForDepositAddress(depositAddress);
            JobCoinClient jcc = new JobCoinClient(addresses, depositAddress, accountAddress);
            jcc.depositJobCoin(amount);

            //withdraw deposited fund to previously provided unused addresses
            Mixer.withdraw(jcc);

            System.out.println("Mixer deposit completed.");

        } catch (TerminatedException | IOException ex) {
            System.out.println(ex);
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
