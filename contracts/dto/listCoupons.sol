// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;

struct ListAllCouponsOpts {
    string userAddress; // Address of the user
    uint256 page; // Page number for pagination
    uint256 pageSize; // Number of items per page
}
