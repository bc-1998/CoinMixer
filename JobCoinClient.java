import java.io.IOException;

public class JobCoinClient {

    private String accountAddress; //client's primary wallet's address
    private String[] recipientAddresses; //client's empty addresses provided to receive deposit increments.
    private String depositAddress; //unique deposit address for the client.


    public JobCoinClient(String[] addresses, String depositAddress, String accountAddress){
        this.recipientAddresses = addresses;
        this.depositAddress = depositAddress;
        this.accountAddress = accountAddress;
    }

    public String[] getRecipientAddresses(){
        return recipientAddresses;
    }

    public String getDepositAddress(){
        return depositAddress;
    }

    public String getAccountAddress(){
        return accountAddress;
    }

    /*
     * deposit coin to deposit account.
     * @amount amount of coins to be deposited
     */
    public void depositJobCoin(String amount) throws IOException {
        Mixer.deposit(new String[]{accountAddress, depositAddress, amount});
    }
}
