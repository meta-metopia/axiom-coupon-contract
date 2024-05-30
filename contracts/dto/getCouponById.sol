// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;

import "./coupon.sol";

struct GetCouponByIdResponse {
    string couponId;
    address creatorAddress;
    string author;
    uint256 mintTime;
    address ownerAddr;
    uint256 ownerTime;
    uint256 supply;
    string name;
    string desc;
    uint256 fieldId;
    string currency;
    Metadata metadata;
}

struct GetTokenByIdResponse {
    uint256 couponId;
    address creatorAddress;
    string author;
    uint256 mintTime;
    address ownerAddr;
    uint256 ownerTime;
    uint256 supply;
    string name;
    string desc;
    uint256 fieldId;
    string currency;
    Metadata metadata;
}
