import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import static java.lang.Math.round;

public class Mixer {

    public static String getDepositAddress(){
        String depositAddress = UUID.randomUUID().toString();
        return depositAddress;
    }

    public static void withdraw(JobCoinClient jc) throws Exception{
        var amount = foundDepositAmount(jc);
        System.out.println("amount is " + amount);
        if (amount > 0.0){
            String[] depositAddress = jc.getRecipientAddresses();
            var indAmount = amount/depositAddress.length;
            System.out.println("indAmount is " + indAmount);
            int i = 0;
            for (; i<depositAddress.length-1; i++) {
                deposit(new String[]{jc.getDepositAddress(), depositAddress[i], ""+indAmount});
                amount -= indAmount;
            }
            System.out.println("last amount is " + Math.floor(amount));
            deposit(new String[]{jc.getDepositAddress(), depositAddress[i], ""+ Math.floor(amount)});
        }
    }

    private static double foundDepositAmount(JobCoinClient jc) throws Exception {

       // Properties prop = Util.readPropertiesFile("apis.properties");
        //System.out.println("address api: "+ prop.getProperty("address"));

        String s = "https://jobcoin.gemini.com/coziness-underhand/api/addresses/";
        s += URLEncoder.encode(jc.getAccountAddress(), "UTF-8");
        URL url = new URL(s);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONParser jsonParser = new JSONParser();
        JSONObject obj = (JSONObject) jsonParser.parse(br);

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting firstName and lastName
        String balance = (String) jo.get("balance");

        System.out.println(balance);

        // getting transactions
        JSONArray transactions = (JSONArray) jo.get("transactions");

        // iterating phoneNumbers
        Iterator itr1 = transactions.iterator();
        Iterator<Map.Entry> itr2;
        var amount = 0.0;
        while (itr1.hasNext()) {
            itr2 = ((Map) itr1.next()).entrySet().iterator();
            while (itr2.hasNext()) {
                Map.Entry pair = itr2.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
                if (pair.getKey().toString().trim().equals("amount")){
                    amount = Double.parseDouble(pair.getValue().toString());
                }
                if (pair.getKey().toString().trim().equals("toAddress") && pair.getValue().toString().trim().equals(jc.getDepositAddress())){
                    System.out.println("Found!");
                    return amount;
                }

            }
        }
        return 0.0;
    }

    /*
     * deposit user's coins to uniquely generated address
     * @args arguments expected by gemini's API for sending Jobcoins
     */
    public static void deposit(String[] args) throws IOException {
        //Properties prop = Util.readPropertiesFile("apis.properties");
        var urlString = "http://jobcoin.gemini.com/coziness-underhand/api/transactions";
        var urlParameters = "fromAddress="+args[0]+"&toAddress="+args[1]+"&amount="+args[2];
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection con = null;
        try {
            var url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            var wr = new DataOutputStream(con.getOutputStream());
            wr.write(postData);

            var br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            String line;
            var content = new StringBuilder();

            while ((line = br.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            System.out.println(content.toString());
        } catch(IOException e){
            System.out.println("Deposit failed - please make sure there are enough coins " + e.getMessage());
        } finally{
            con.disconnect();
        }
    }
}
