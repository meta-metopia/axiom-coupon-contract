// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "../dto/getCouponById.sol";
import "../dto/redeemCoupon.sol";
import "../dto/createCoupon.sol";

interface INFTContract {
    function totalSupply() external view returns (uint256);

    /**
     * Transfer tokens from one address to another
     */
    function mint(address to, uint256 id) external;

    /**
     * Reedeem NFT using token Id. Only owner can redeem NFT
     */
    function redeem(
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

    /**
     * Initialize the NFT contract with the given options
     * @param _createCouponOpts CreateCouponOpts
     * @param _additionalOwners  Additional owners of the NFT
     */
    function initialize(
        CreateCouponOpts memory _createCouponOpts,
        address[] memory _additionalOwners
    ) external;
}
