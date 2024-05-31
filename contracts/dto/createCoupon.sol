// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;

import "./coupon.sol";

struct CreateCouponOpts {
    address creatorAddress; // Address of the issuer
    string author; // Name of the issuer
    uint256 supply; // Number of coupons to be issued
    string name; // Name of the coupon
    string desc; // Description of the coupon
    uint256 fieldId; // ID returned from file upload
    uint256 price; // Value of the coupon in points
    string currency; // Type of currency
    Metadata metadata; // Additional metadata
}

struct CreateCouponResponse {
    string nftIdBegin; // ID of the first coupon
}
