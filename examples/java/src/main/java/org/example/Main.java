package org.example;

import org.example.contract.Factory;
import org.example.dto.CreateRandomWalletExampleDto;
import org.example.dto.GetBalanceDto;
import org.example.dto.MnemonicExampleDto;
import org.example.dto.SignMessageExampleDto;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

class Utils {
    static String getSignatureString(Sign.SignatureData data) {
        String r = Numeric.toHexStringNoPrefix(data.getR());
        String s = Numeric.toHexStringNoPrefix(data.getS());
        String v = Numeric.toHexStringNoPrefix(data.getV());

        return r + s + v;
    }
}


public class Main {


    public static void main(String[] args) throws Exception {
        try {
            //from env
            String privateKey = System.getenv("PRIVATE_KEY");
            String rpcUrl = System.getenv("RPC_URL");
            String contractAddress = System.getenv("CONTRACT_ADDRESS");

            System.out.println("RPC URL: " + rpcUrl);
            System.out.println("Contract Address: " + contractAddress);

            var example = new Example(privateKey, rpcUrl, contractAddress);
            example.getCouponDetailById("01010020500000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}