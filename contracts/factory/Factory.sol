// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "./IFactory.sol";

contract NFTCouponFactory is INFTCouponFactory {
    function createCoupon(
        CreateCouponOpts memory createCouponOpts
    ) external override returns (CreateCouponResponse memory response) {
        // Default implementation with default return value
        return CreateCouponResponse("");
    }

    function transferCoupon(
        TransferCouponOpts memory transferCouponOptions
    ) external override {
        // Default implementation, does nothing
    }

    function listAllCoupons(
        ListAllCouponsOpts memory listAllCouponsOpts
    ) external view override returns (GetCouponByIdResponse[] memory items) {
        // Default implementation with default return value
        GetCouponByIdResponse[] memory items;
        return items;
    }

    function getCouponById(
        string memory couponId
    ) external view override returns (GetCouponByIdResponse memory) {
        GetCouponByIdResponse memory response;
        return response;
        // Initialize with default values
    }

    function redeemCoupon(
        RedeemCouponOpts memory redeemCouponOpts
    ) external override {}
}
