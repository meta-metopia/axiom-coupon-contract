// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract WhiteListed {
    constructor(address[] memory initialOwners) {
        for (uint256 i = 0; i < initialOwners.length; i++) {
            whileListedUsers[initialOwners[i]] = true;
        }
    }

    /**
     * @dev Mapping of whitelisted users.
     */
    mapping(address => bool) private whileListedUsers;

    /**
     * Modifier to check if the user is whitelisted
     */
    modifier onlyWhileListedUsers() {
        require(
            isWhiteListed(msg.sender),
            "40301: Only whitelisted users can call this function"
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

    function isWhiteListed(address user) public view returns (bool) {
        return whileListedUsers[user];
    }
}
