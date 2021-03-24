Coin Mixer

This is a Coin Mixer, a way to maintain user's privacy on blockchain network.

Jave Files -

Main.java
JobCoinClient.java
Mixer.java
Uitl.java
Executable Jar File -
CoinMixer.jar

To Execute -
$ java -jar CoinMixer.jar

Example - Alice deposits 4 Jobcoins to be later distributed to 3 empty addresses tt, yy, uu
As a result, tt and yy will get 1.3333333333333333 Jobcoins, uu will get 1 JobCoin. 0.3333333333333333 will be fee. 

$ java -jar CoinMixer.jar
Please enter a comma-separated list of new, unused Jobcoin addresses where your mixed Jobcoins will be sent.

tt, yy, uu
[tt, yy, uu]

Please enter your primary wallet's address:
Alice

Please enter number of Jobcoin to be deposited:
4

The deposit address is 445ff9cc-b9e4-4874-acfe-bd4f156d2d90

You may now send Jobcoins to address 445ff9cc-b9e4-4874-acfe-bd4f156d2d90. They will be mixed and sent to your destination addresses.

{"status":"OK"}

amount is 4.0
indAmount is 1.3333333333333333
{"status":"OK"}

{"status":"OK"}

{"status":"OK"}

Mixer deposit completed.
