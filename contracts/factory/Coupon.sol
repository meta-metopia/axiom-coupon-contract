// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

interface ICouponUtils {
    function generateCouponToken(
        uint8 contractAddressIndex,
        uint8 contractAddressLength,
        uint256 contractAddressValue,
        uint8 tokenIdIndex,
        uint8 tokenIdLength,
        uint256 tokenIdValue
    ) external pure returns (string memory);

    function parseCouponToken(
        string memory couponToken
    )
        external
        pure
        returns (
            uint8 contractAddressIndex,
            uint256 contractAddressValue,
            uint8 tokenIdIndex,
            uint256 tokenIdValue
        );
}

contract CouponUtils is ICouponUtils {
    function generateCouponToken(
        uint8 contractAddressIndex, // Index of the contract address
        uint8 contractAddressLength, // Length of the contract address value
        uint256 contractAddressValue, // Value of the contract address
        uint8 tokenIdIndex, // Index of the token ID
        uint8 tokenIdLength, // Length of the token ID value
        uint256 tokenIdValue // Value of the token ID
    ) public pure returns (string memory) {
        // Ensure lengths are correct
        require(
            contractAddressLength == 2,
            "Contract address length must be 2"
        );
        require(tokenIdLength == 2, "Token ID length must be 2");

        // Format the components into the coupon token
        return
            string(
                abi.encodePacked(
                    _toTwoDigitString(contractAddressIndex),
                    _toTwoDigitString(contractAddressLength),
                    _toValueString(contractAddressValue),
                    _toTwoDigitString(tokenIdIndex),
                    _toTwoDigitString(tokenIdLength),
                    _toValueString(tokenIdValue)
                )
            );
    }

    function parseCouponToken(
        string memory couponToken
    )
        public
        pure
        returns (
            uint8 contractAddressIndex,
            uint256 contractAddressValue,
            uint8 tokenIdIndex,
            uint256 tokenIdValue
        )
    {
        bytes memory tokenBytes = bytes(couponToken);
        require(tokenBytes.length >= 10, "Invalid coupon token length");

        contractAddressIndex = _parseTwoDigitValue(tokenBytes, 0);
        uint8 contractAddressLength = _parseTwoDigitValue(tokenBytes, 2);
        contractAddressValue = _parseValue(
            tokenBytes,
            4,
            contractAddressLength
        );

        tokenIdIndex = _parseTwoDigitValue(
            tokenBytes,
            4 + contractAddressLength
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

    function _toValueString(
        uint256 value
    ) internal pure returns (string memory) {
        if (value == 0) {
            return "0";
        }
        uint256 temp = value;
        uint256 digits;
        while (temp != 0) {
            digits++;
            temp /= 10;
        }
        bytes memory buffer = new bytes(digits);
        while (value != 0) {
            digits -= 1;
            buffer[digits] = bytes1(uint8(48 + (value % 10)));
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
