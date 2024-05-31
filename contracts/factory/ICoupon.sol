// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;

interface ICouponFactory {
    function generateCouponToken(
        uint8 contractAddressIndex, // Index of the contract address
        uint8 tokenIdIndex // Index of the token ID
    ) external returns (string memory);
}
