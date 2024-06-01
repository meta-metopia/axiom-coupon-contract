// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "hardhat/console.sol";
import "@openzeppelin/contracts/utils/cryptography/ECDSA.sol";
import "@openzeppelin/contracts/utils/cryptography/MessageHashUtils.sol";

contract MessageVerifiable {
    using ECDSA for bytes32;

    function recoverSigner(
        bytes memory _signature,
        string memory _message
    ) public pure returns (address) {
        bytes32 messageHash = MessageHashUtils.toEthSignedMessageHash(
            bytes(_message)
        );
        address recovered = messageHash.recover(_signature);
        return recovered;
    }
}
