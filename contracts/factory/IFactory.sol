// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "../dto/createCoupon.sol";
import "../dto/transferCoupon.sol";
import "../dto/listCoupons.sol";
import "../dto/getCouponById.sol";
import "../dto/redeemCoupon.sol";

interface INFTCouponFactory {
    /**
     * Event emitted when a coupon is created
     * @param nftIdBegin Begin coupon ID of the NFT
     * @param contractAddress Address of the coupon contract
     */
    event CouponCreated(string nftIdBegin, address contractAddress);
    /**
     * Event emitted when a coupon is transferred
     * @param couponId ID of the coupon
     * @param trigger Who triggers the transfer
     * @param to Address of the recipient
     */
    event CouponTransferred(string couponId, address trigger, address to);

    /**
     * Event emitted when a coupon is redeemed
     * @param couponId ID of the coupon
     * @param trigger Who triggers the redemption
     */
    event CouponRedeemed(string couponId, address trigger);

    function createCoupon(
        CreateCouponOpts memory createCouponOpts
    ) external returns (CreateCouponResponse memory response);

    function transferCoupon(
        TransferCouponOpts memory transferCouponOptions
    ) external;

    function listAllCoupons(
        ListAllCouponsOpts memory listAllCouponsOpts
    ) external view returns (GetCouponByIdResponse[] memory items);

    function getCouponById(
        string memory couponId
    ) external view returns (GetCouponByIdResponse memory);

    function redeemCoupon(RedeemCouponOpts memory redeemCouponOpts) external;
}
