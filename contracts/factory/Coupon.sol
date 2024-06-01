// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

contract CouponFactory {
    function getAddressLength(uint contractIndex) private pure returns (uint8) {
        uint8 length = 0;
        uint index = contractIndex;

        // Calculate the number of digits in the contractIndex
        while (index != 0) {
            index /= 10;
            length++;
        }

        // Handle the case when the index is 0
        if (length == 0) {
            length = 1;
        }

        return length;
    }

    /**
     * Generate coupon token from contract address index and token ID index
     */
    function generateCouponToken(
        uint256 contractAddressIndex, // Index of the contract address
        uint256 tokenIdIndex // Index of the token ID
    ) public pure returns (string memory) {
        // contract address length is depents on the contract address index
        // for example, if the contract address index is 1, then the contract address length is 1
        // if the contract address index is 10, then the contract address length is 2
        // if the contract address index is 100, then the contract address length is 3
        uint8 contractAddressLength = getAddressLength(contractAddressIndex);

        // tokenAddress length is depents on the token address index
        // for example, if the token address index is 1, then the token address length is 1
        // if the token address index is 10, then the token address length is 2
        // if the token address index is 100, then the token address length is 3
        uint8 tokenIdLength = getAddressLength(tokenIdIndex);

        return
            string(
                abi.encodePacked(
                    _toTwoDigitString(1),
                    _toTwoDigitString(contractAddressLength),
                    _toValueString(contractAddressIndex, contractAddressLength),
                    _toTwoDigitString(2),
                    _toTwoDigitString(tokenIdLength),
                    _toValueString(0, tokenIdLength)
                )
            );
    }

    function parseCouponToken(
        string memory couponToken
    ) public pure returns (uint256 contractAddressIndex, uint256 tokenIdValue) {
        bytes memory tokenBytes = bytes(couponToken);
        require(tokenBytes.length >= 10, "Invalid coupon token length");

        uint8 contractAddressLength = _parseTwoDigitValue(tokenBytes, 2);
        contractAddressIndex = _parseValue(
            tokenBytes,
            4,
            contractAddressLength
        );

        uint8 tokenIdLength = _parseTwoDigitValue(
            tokenBytes,
            6 + contractAddressLength
        );
        tokenIdValue = _parseValue(
            tokenBytes,
            8 + contractAddressLength,
            tokenIdLength
        );
    }

    function _toTwoDigitString(
        uint8 value
    ) internal pure returns (string memory) {
        bytes memory buffer = new bytes(2);
        buffer[0] = bytes1(uint8(48 + value / 10));
        buffer[1] = bytes1(uint8(48 + (value % 10)));
        return string(buffer);
    }

    /**
     * Generate a string from a value with a fixed length
     * For example, if the value is 123 and the length is 5, then the output is "00123"
     * If the value is 1 and the length is 3, then the output is "001"
     */
    function _toValueString(
        uint256 value,
        uint8 withLength
    ) internal pure returns (string memory) {
        bytes memory buffer = new bytes(withLength);
        for (uint8 i = 0; i < withLength; i++) {
            buffer[withLength - i - 1] = bytes1(uint8(48 + (value % 10)));
            value /= 10;
        }
        return string(buffer);
    }

    function _parseTwoDigitValue(
        bytes memory data,
        uint256 offset
    ) internal pure returns (uint8) {
        require(
            data.length >= offset + 2,
            "Invalid data length for two digit value"
        );
        return
            uint8(
                (uint8(data[offset]) - 48) * 10 + (uint8(data[offset + 1]) - 48)
            );
    }

    function _parseValue(
        bytes memory data,
        uint256 offset,
        uint8 length
    ) internal pure returns (uint256) {
        require(
            data.length >= offset + length,
            "Invalid data length for value"
        );
        uint256 value;
        for (uint256 i = 0; i < length; i++) {
            value = value * 10 + (uint8(data[offset + i]) - 48);
        }
        return value;
    }
}
