package org.example


import org.example.contract.Factory
import org.example.dto.SignMessageExampleDto
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.Sign
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Numeric
import java.math.BigInteger


class SmartContractController(
    private val privateKey: String,
    private val rpcUrl: String,
    private val contractAddress: String
) {


    private fun getSignatureString(data: Sign.SignatureData): String {
        val r = Numeric.toHexStringNoPrefix(data.r)
        val s = Numeric.toHexStringNoPrefix(data.s)
        val v = Numeric.toHexStringNoPrefix(data.v)

        return r + s + v
    }

    private fun signMessage(privateKey: String, message: String): SignMessageExampleDto {
        val credentials = Credentials.create(privateKey)
        val messageHash = message.toByteArray()

        // Sign the message
        val signature = Sign.signPrefixedMessage(messageHash, credentials.ecKeyPair)
        val hexString = "0x" + getSignatureString(signature)
        return SignMessageExampleDto(
            Keys.toChecksumAddress(credentials.address),
            hexString,
            Numeric.hexStringToByteArray(hexString)
        )
    }

    private val factory: Factory by lazy {
        val web3j = Web3j.build(HttpService(this.rpcUrl))
        val provider = StaticGasProvider(BigInteger.valueOf(1000000000000L), BigInteger.valueOf(9000000))
        Factory.load(
            contractAddress,
            web3j,
            Credentials.create(privateKey),
            provider
        )
    }


    private fun getCredentials(privateKey: String): Credentials {
        return Credentials.create(privateKey)
    }

    @Throws(Exception::class)
    fun getCouponDetailById(couponId: String) {
        val response = factory.getCouponById(couponId).send()
        println("Coupon Detail: " + response.couponId)
    }


    @Throws(Exception::class)
    fun mintCoupon(couponId: String, receiverPrivateKey: String) {

        val credentials = getCredentials(receiverPrivateKey)
        val opts = Factory.MintCouponOpts(couponId, credentials.address)
        val response = factory.mintCoupon(opts).send()

        println("Mint Coupon: " + response.status)

    }

    @Throws(Exception::class)
    fun redeemCoupon(couponId: String, receiverPrivateKey: String) {

        val message = "hello"
        val signature = signMessage(message = message, privateKey = receiverPrivateKey)
        val credentials = getCredentials(receiverPrivateKey)

        val opts = Factory.RedeemCouponOpts(
            couponId,
            credentials.address,
            signature.signatureBytes,
            "",
            BigInteger.ZERO,
            credentials.address,
            signature.signatureBytes,
            message,
            BigInteger.ZERO,
            BigInteger.ZERO,
            BigInteger.ZERO
        )
        val response = factory.redeemCoupon(opts).send()
        println("Redeem Coupon: " + response.status)

    }
}