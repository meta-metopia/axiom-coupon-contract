package org.example;

import org.example.contract.Factory;
import org.example.dto.SignMessageExampleDto;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

public class Example {
    private final String rpcUrl;
    private final String contractAddress;

    private final String privateKey;

    public Example(String privateKey, String rpcUrl, String contractAddress) {
        this.privateKey = privateKey;
        this.rpcUrl = rpcUrl;
        this.contractAddress = contractAddress;
    }

    /**
     * Sign a message using a private key
     *
     * @param privateKey The private key to sign the message with.
     * @param message    The message to sign.
     */
    SignMessageExampleDto signMessageExample(String privateKey, String message) {
        Credentials credentials = Credentials.create(privateKey);
        byte[] messageHash = message.getBytes();

        // Sign the message
        Sign.SignatureData signature = Sign.signPrefixedMessage(messageHash, credentials.getEcKeyPair());
        return new SignMessageExampleDto(Keys.toChecksumAddress(credentials.getAddress()), "0x" + Utils.getSignatureString(signature));
    }

    Factory getFactory() {
        var web3j = Web3j.build(new HttpService(this.rpcUrl));
        return Factory.load(contractAddress, web3j, Credentials.create(privateKey), new DefaultGasProvider());
    }

    Credentials getCredentials(String privateKey) {
        return Credentials.create(privateKey);
    }


    void getCouponDetailById(String couponId) throws Exception {
        var factory = getFactory();
        var response = factory.getCouponById(couponId).send();
        System.out.println("Coupon Detail: " + response.couponId);
    }

    void redeemCoupon(String couponId, String receiverPrivateKey) throws Exception {
        var factory = getFactory();
        var message = "hello";
        var signature = signMessageExample(message, receiverPrivateKey);
        var credentials = getCredentials(receiverPrivateKey);


        var response = factory.redeemCoupon(new Factory.RedeemCouponOpts(
                new Utf8String(couponId),
                signature.walletAddress(),
                signature.signature(),
                "",
                "",
                signature.walletAddress(),
                signature.signature(),
                message,
                "1",
                0,
                0

        )).send();
        System.out.println("Coupon redeemed: " + response.receipt);
    }
}
