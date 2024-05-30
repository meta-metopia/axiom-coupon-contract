// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract WhiteListed {
    constructor(address[] memory initialOwners) {
        for (uint256 i = 0; i < initialOwners.length; i++) {
            whileListedUsers[initialOwners[i]] = true;
        }
    }

    /**
     * @dev Mapping of whitelisted users. And only whitelisted users can create coupons
     */
    mapping(address => bool) private whileListedUsers;

    /**
     * Modifier to check if the user is whitelisted
     */
    modifier onlyWhileListedUsers() {
        require(
            whileListedUsers[msg.sender],
            "Only whitelisted users can create coupons"
        );
        _;
    }

    /**
     * Approve will add user to whitelist
     */
    function approve(address user) public {
        whileListedUsers[user] = true;
    }

    /**
     * Disapprove will remove user from whitelist
     */
    function disapprove(address user) public {
        whileListedUsers[user] = false;
    }
}
