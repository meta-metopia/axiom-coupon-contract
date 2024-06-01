// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/access/Ownable.sol";

import "./IFactory.sol";
import "./Coupon.sol";
import "../nft/INftContract.sol";
import "../access/WhiteListed.sol";

contract NFTCouponFactory is
    INFTCouponFactory,
    Ownable,
    WhiteListed,
    CouponFactory
{
    /**
     * Mapping of coupon contracts id to their address
     * this is used to keep track of all the coupon contracts created.
     */
    mapping(uint256 => INFTContract) private couponContracts;

    /**
     * Mapping of contract address to their id
     * this is used to keep track of all the coupon contracts created.
     */
    mapping(uint256 => INFTContract) private addedContracts;

    uint256 private createdContractIndexCounter = 0;
    uint256 private addedContractIndexCounter = 0;

    address[] private initialOwners;

    constructor(
        address[] memory _initialOwners
    ) Ownable(msg.sender) WhiteListed(_initialOwners) {
        approve(msg.sender);
        initialOwners = _initialOwners;

        initialOwners.push(msg.sender);
        initialOwners.push(address(this));
    }

    function addContract(INFTContract _contract) external onlyOwner {
        addedContracts[addedContractIndexCounter] = _contract;
        addedContractIndexCounter++;
    }

    function createCoupon(
        CreateCouponOpts memory createCouponOpts
    )
        external
        override
        onlyWhileListedUsers
        returns (CreateCouponResponse memory response)
    {
        INFTContract nftContract = addedContracts[createdContractIndexCounter];
        address contractAddress = address(nftContract);
        require(
            address(contractAddress) != address(0),
            "40601: Factory contract currently doesn't hold any available NFT contract or exceeded the limit of NFT contract creation"
        );

        // Initialize the nft contract with metadata and initial supply
        nftContract.initialize(createCouponOpts, initialOwners);
        string memory couponId = generateCouponToken(
            createdContractIndexCounter,
            createCouponOpts.supply
        );

        couponContracts[createdContractIndexCounter] = nftContract;
        createdContractIndexCounter++;

        emit CouponCreated(couponId, contractAddress);
        return CreateCouponResponse("");
    }

    function transferCoupon(
        TransferCouponOpts memory transferCouponOptions
    ) external override onlyWhileListedUsers {
        (
            uint256 _contractAddressIndex,
            uint256 _tokenIdValue
        ) = parseCouponToken(transferCouponOptions.couponId);

        INFTContract nftContract = addedContracts[_contractAddressIndex];
        address contractAddress = address(nftContract);
        require(
            address(contractAddress) != address(0),
            "40401: NFT contract for the given coupon id not found"
        );

        nftContract.mint(transferCouponOptions.receiverAddr, _tokenIdValue);
        emit CouponTransferred(
            transferCouponOptions.couponId,
            msg.sender,
            transferCouponOptions.receiverAddr
        );
    }

    function listAllCoupons(
        ListAllCouponsOpts memory listAllCouponsOpts
    ) external view override returns (GetCouponByIdResponse[] memory items) {
        uint256 totalItems = 0;

        for (uint256 i = 0; i < createdContractIndexCounter; i++) {
            INFTContract nftContract = couponContracts[i];
            address contractAddress = address(nftContract);
            require(
                address(contractAddress) != address(0),
                "40401: NFT contract for the given coupon id not found"
            );

            GetTokenByIdResponse[] memory response = nftContract.listByOwner(
                listAllCouponsOpts.userAddress
            );

            for (uint256 j = 0; j < response.length; j++) {
                GetTokenByIdResponse memory token = response[j];

                string memory couponId = generateCouponToken(i, token.couponId);
                GetCouponByIdResponse memory item = GetCouponByIdResponse(
                    couponId,
                    token.creatorAddress,
                    token.author,
                    token.mintTime,
                    token.ownerAddr,
                    token.ownerTime,
                    token.supply,
                    token.name,
                    token.desc,
                    token.fieldId,
                    token.currency,
                    token.metadata
                );
                items[totalItems] = item;
                totalItems++;
            }
        }
    }

    function getCouponById(
        string memory couponId
    ) external view override returns (GetCouponByIdResponse memory) {
        (
            uint256 _contractAddressIndex,
            uint256 _tokenIdValue
        ) = parseCouponToken(couponId);
        INFTContract nftContract = addedContracts[_contractAddressIndex];
        address contractAddress = address(nftContract);
        require(
            address(contractAddress) != address(0),
            "40401: NFT contract for the given coupon id not found"
        );

        GetTokenByIdResponse memory token = nftContract.getById(_tokenIdValue);
        GetCouponByIdResponse memory response = GetCouponByIdResponse(
            couponId,
            token.creatorAddress,
            token.author,
            token.mintTime,
            token.ownerAddr,
            token.ownerTime,
            token.supply,
            token.name,
            token.desc,
            token.fieldId,
            token.currency,
            token.metadata
        );

        return response;
    }

    function redeemCoupon(
        RedeemCouponOpts memory redeemCouponOpts
    ) external override {
        (
            uint256 _contractAddressIndex,
            uint256 _tokenIdValue
        ) = parseCouponToken(redeemCouponOpts.couponId);

        INFTContract nftContract = addedContracts[_contractAddressIndex];
        address contractAddress = address(nftContract);
        require(
            address(contractAddress) != address(0),
            "40401: NFT contract for the given coupon id not found"
        );

        nftContract.redeem(_tokenIdValue, redeemCouponOpts);
        emit CouponRedeemed(redeemCouponOpts.couponId, msg.sender);
    }
}
