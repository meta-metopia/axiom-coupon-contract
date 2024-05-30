// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "../dto/getCouponById.sol";
import "../dto/redeemCoupon.sol";

interface INFTContract {
    /**
     * Transfer tokens from one address to another
     */
    function mint(address to, uint256 id) external;

    /**
     * Reedeem NFT using token Id. Only owner can redeem NFT
     */
    function reedeem(
        uint256 id,
        RedeemCouponOpts memory redeemCouponOpts
    ) external;

    /**
     * List all NFTs owned by an address
     */
    function listByOwner(
        address owner
    ) external view returns (GetTokenByIdResponse[] memory);

    /**
     * Get NFT by Id
     */
    function getById(
        uint256 id
    ) external view returns (GetTokenByIdResponse memory);
}
