// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "@openzeppelin/contracts/token/ERC1155/ERC1155.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

import "./INftContract.sol";
import "../dto/coupon.sol";
import "../dto/createCoupon.sol";
import "../access/WhiteListed.sol";
import "../access/Verifiable.sol";

contract NFTContract is
    ERC1155,
    Ownable,
    INFTContract,
    WhiteListed,
    MessageVerifiable
{
    Metadata private _metadata;
    ApprovedMerchant[] private _approvedMerchant;
    ApprovedPayment[] private _approvedPayment;
    uint256 private _supply;
    uint256 private _currentSupply;

    mapping(uint256 => ReedemState) private _reedemState;
    mapping(address => uint256[]) private _ownedNFTs;
    mapping(uint256 => uint256) private _mintTime;
    mapping(uint256 => address) private _owners;

    address private _creatorAddress;
    string private _author;
    uint256 private _fieldId;
    uint256 private _price;
    string private _currency;
    string private _name;
    string private _desc;

    /**
     * Constructor to create a new NFT
     * @param _createCouponOpts - CreateCouponOpts
     * @param _initialOwners - Initial owners of the NFT
     */
    constructor(
        CreateCouponOpts memory _createCouponOpts,
        address[] memory _initialOwners
    )
        Ownable(msg.sender)
        ERC1155(_createCouponOpts.metadata.url)
        WhiteListed(_initialOwners)
    {
        initializeMetadata(_createCouponOpts.metadata);
        _creatorAddress = msg.sender;
        _author = _createCouponOpts.author;
        _fieldId = _createCouponOpts.fieldId;
        _price = _createCouponOpts.price;
        _currency = _createCouponOpts.currency;
        _supply = _createCouponOpts.supply;
        _setURI(_createCouponOpts.metadata.url);
        _name = _createCouponOpts.name;
        _desc = _createCouponOpts.desc;
        _currentSupply = 0;
    }

    function totalSupply() public view returns (uint256) {
        return _supply;
    }

    /**
     * Initialize metadata for the NFT
     */
    function initializeMetadata(Metadata memory data) private {
        _metadata.approveDuration = data.approveDuration;
        _metadata.approveTime = data.approveTime;
        _metadata.couponDetails = data.couponDetails;
        _metadata.couponSubtitle = data.couponSubtitle;
        _metadata.couponType = data.couponType;
        _metadata.expirationStartTime = data.expirationStartTime;
        _metadata.expirationTime = data.expirationTime;
        _metadata.rule = data.rule;
        _metadata.url = data.url;

        for (uint256 i = 0; i < data.approvedMerchant.length; i++) {
            _approvedMerchant.push(data.approvedMerchant[i]);
        }

        for (uint256 i = 0; i < data.approvedPayment.length; i++) {
            _approvedPayment.push(data.approvedPayment[i]);
        }
    }

    /**
     * Mint a new NFT to the user with the given id
     */
    function mint(address to, uint256 id) external onlyWhileListedUsers {
        require(_currentSupply < _supply, "1001: Supply limit reached");
        require(_owners[id] == address(0), "1002: NFT already minted");

        _mint(to, id, 1, "");
        _reedemState[id] = ReedemState.NOT_REDEEMED;
        _ownedNFTs[to].push(id);
        _mintTime[id] = block.timestamp;
        _owners[id] = to;
        _currentSupply += 1;
    }

    /**
     * Reedeem NFT using token Id. Only whitelisted users can redeem NFT
     */
    function reedeem(
        uint256 id,
        RedeemCouponOpts memory _redeemCouponOpts
    ) external onlyWhileListedUsers {
        address userAddress = address(
            bytes20(bytes(_redeemCouponOpts.userAddress))
        );

        require(
            verify(
                userAddress,
                bytes(_redeemCouponOpts.userSignature),
                _redeemCouponOpts.userMessage
            ),
            "Invalid signature"
        );

        address user = recoverSigner(
            getEthSignedMessageHash(
                getMessageHash(_redeemCouponOpts.userMessage)
            ),
            bytes(_redeemCouponOpts.paymentSignature)
        );

        require(user == userAddress, "Invalid user signature");
        require(balanceOf(user, id) > 0, "User does not own the NFT");
        require(
            _reedemState[id] == ReedemState.NOT_REDEEMED,
            "NFT already redeemed"
        );

        _reedemState[id] = ReedemState.REDEEMED;
    }

    function listByOwner(
        address owner
    ) external view returns (GetTokenByIdResponse[] memory) {
        uint256[] memory nfts = _ownedNFTs[owner];
        GetTokenByIdResponse[] memory response = new GetTokenByIdResponse[](
            nfts.length
        );

        for (uint256 i = 0; i < nfts.length; i++) {
            GetTokenByIdResponse memory item = GetTokenByIdResponse({
                couponId: nfts[i],
                creatorAddress: _creatorAddress,
                author: _author,
                mintTime: _mintTime[nfts[i]],
                ownerAddr: owner,
                ownerTime: _mintTime[nfts[i]],
                supply: _supply,
                name: _name,
                desc: _desc,
                fieldId: _fieldId,
                currency: _currency,
                metadata: _metadata
            });

            response[i] = item;
        }

        return response;
    }

    function getById(
        uint256 id
    ) external view returns (GetTokenByIdResponse memory) {
        address owner = _owners[id];
        uint256 mintTime = _mintTime[id];

        return
            GetTokenByIdResponse({
                couponId: id,
                creatorAddress: _creatorAddress,
                author: _author,
                mintTime: mintTime,
                ownerAddr: owner,
                ownerTime: mintTime,
                supply: _supply,
                name: _name,
                desc: _desc,
                fieldId: _fieldId,
                currency: _currency,
                metadata: _metadata
            });
    }
}
