package org.example

import io.github.cdimascio.dotenv.Dotenv

enum class Environment {
    DEVELOPMENT,
    PRODUCTION
}

fun main(args: Array<String>) {
    val dotenv: Dotenv =  Dotenv.configure().filename(".env").load()
    val contractAddress = dotenv.get("CONTRACT_ADDRESS")
    val rpcUrl = dotenv.get("RPC_URL")
    val privateKey = dotenv.get("PRIVATE_KEY")
    val receiverPrivateKey = dotenv.get("RECEIVER_PRIVATE_KEY")
    val controller =
        SmartContractController(contractAddress = contractAddress, rpcUrl = rpcUrl, privateKey = privateKey)

    val couponId = "01012020500103"
    controller.mintCoupon(couponId, receiverPrivateKey)
    controller.redeemCoupon(couponId, receiverPrivateKey)
}