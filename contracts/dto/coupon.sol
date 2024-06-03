// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;

struct Coupon {
    string id; // ID of the coupon
    Metadata metadata; // Metadata of the coupon
}

struct ApprovedMerchant {
    address approvedMerchantAddr; // Address of the merchant
    string approvedMerchantName; // Name of the merchant
}

struct ApprovedPayment {
    address approvedPaymentAddr; // Address of the payment channel
    string approvedPaymentName; // Name of the payment channel
}

enum RedeemState {
    REDEEMED,
    NOT_REDEEMED
}

struct Metadata {
    ApprovedMerchant[] approvedMerchant; // List of merchants allowed to redeem the coupon
    ApprovedPayment[] approvedPayment; // List of payment channels allowed to redeem the coupon
    string couponType; // Type of the coupon
    string couponSubtitle; // Subtitle of the coupon
    string couponDetails; // Additional details about the coupon
    string url; // URL to the coupon image
    uint256 expirationTime; // Expiration time of the coupon
    uint256 expirationStartTime; // Start time of the coupon validity
    Rule rule; // Redemption rules
    RedeemState redeemState; // State of the coupon
    uint256 approveTime; // Time when the coupon was approved
    uint256 approveDuration; // Duration of the approval
}

struct Rule {
    uint256 value; // Face value of the coupon
    uint256 claimLimit; // Limit on the number of coupons per person
    bool isTransfer; // Indicates if the coupon can be transferred
}
