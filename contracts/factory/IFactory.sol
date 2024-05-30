// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "../dto/createCoupon.sol";
import "../dto/transferCoupon.sol";
import "../dto/listCoupons.sol";
import "../dto/getCouponById.sol";
import "../dto/redeemCoupon.sol";

interface INFTCouponFactory {
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
