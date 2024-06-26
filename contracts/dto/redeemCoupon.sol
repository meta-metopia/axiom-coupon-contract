// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;

struct RedeemCouponOpts {
    string couponId; // ID of the coupon to be redeemed
    address paymentAddress; // Address of the payment channel
    bytes paymentSignature; // Signature from the payment channel's private key
    string paymentMessage; // Original message signed by the payment channel
    uint256 paymentNonce; // Nonce for the payment channel signature
    address userAddress; // Address of the user
    bytes userSignature; // Signature from the user's private key
    string userMessage; // Original message signed by the user
    uint256 userNonce; // Nonce for the user's signature
    uint256 approveTime; // Time when the coupon was approved
    uint256 approveDuration; // Duration of the approval
}
