// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "./IFactory.sol";
import "./Coupon.sol";
import "../nft/INftContract.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "../nft/Nft1155.sol";
import "../access/WhiteListed.sol";

contract NFTCouponFactory is INFTCouponFactory, Ownable, WhiteListed {
    ICouponUtils private couponUtils;

    constructor(
        address initialOwner,
        address[] memory _initialOwners
    ) Ownable(initialOwner) WhiteListed(_initialOwners) {
        couponUtils = new CouponUtils();
    }

    /**
     * Set the coupon utils contract
     */
    function setCouponUtils(ICouponUtils _couponUtils) public onlyOwner {
        couponUtils = _couponUtils;
    }

    function createCoupon(
        CreateCouponOpts memory createCouponOpts
    )
        external
        override
        onlyWhileListedUsers
        returns (CreateCouponResponse memory response)
    {
        // Generate the coupon token

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
