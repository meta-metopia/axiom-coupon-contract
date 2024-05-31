// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "./IFactory.sol";
import "./Coupon.sol";
import "../nft/INftContract.sol";
import "../nft/Nft1155.sol";
import "../access/WhiteListed.sol";
import "./ICoupon.sol";

contract NFTCouponFactory is INFTCouponFactory, Ownable, WhiteListed {
    /**
     * Mapping of coupon contracts id to their address
     */
    mapping(string => address) private couponContracts;
    address[] private initialOwners;

    ICouponFactory private couponFactory;

    constructor(
        address[] memory _initialOwners
    ) Ownable(msg.sender) WhiteListed(_initialOwners) {
        approve(msg.sender);
        initialOwners = _initialOwners;
    }

    function setICouponFactory(
        ICouponFactory _couponFactory
    ) external onlyOwner {
        couponFactory = _couponFactory;
    }

    function createCoupon(
        CreateCouponOpts memory createCouponOpts
    )
        external
        override
        onlyWhileListedUsers
        returns (CreateCouponResponse memory response)
    {
        NFTContract nftContract = new NFTContract(
            createCouponOpts,
            initialOwners
        );
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
