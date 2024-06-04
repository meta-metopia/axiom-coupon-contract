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
    bool private _isInitialized = false;

    mapping(uint256 => RedeemState) private _reedemState;
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

    modifier onlyInitialized() {
        require(_isInitialized, "40000: Contract not initialized");
        _;
    }

    /**
     * Constructor to create a new NFT
     * @param _initialOwners - Initial owners of the NFT
     */
    constructor(
        address[] memory _initialOwners
    ) Ownable(msg.sender) ERC1155("") WhiteListed(_initialOwners) {
        approve(msg.sender);
    }

    function initialize(
        CreateCouponOpts memory _createCouponOpts,
        address[] memory _additionalOwners
    ) external onlyWhileListedUsers {
        for (uint256 i = 0; i < _additionalOwners.length; i++) {
            approve(_additionalOwners[i]);
        }
        _isInitialized = true;
        _setURI(_createCouponOpts.metadata.url);
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

    function totalSupply() public view onlyInitialized returns (uint256) {
        return _supply;
    }

    function transfer(address to, uint256 id) external onlyInitialized {
        require(
            balanceOf(msg.sender, id) > 0,
            "40004: User does not own the NFT"
        );
        require(
            _reedemState[id] == RedeemState.NOT_REDEEMED,
            "40005: NFT already redeemed"
        );

        require(
            _owners[id] == msg.sender,
            "40006: User is not the owner and cannot transfer the NFT on behalf of the owner"
        );

        require(
            _metadata.rule.isTransfer,
            "40007: Transfer not allowed due to rule restrictions"
        );

        safeTransferFrom(msg.sender, to, id, 1, "");
        _ownedNFTs[to].push(id);
        _owners[id] = to;

        // remove owned NFT from the sender
        uint256[] memory ownedNFTs = _ownedNFTs[msg.sender];
        uint256[] memory newOwnedNFTs = new uint256[](ownedNFTs.length - 1);
        uint256 index = 0;
        for (uint256 i = 0; i < ownedNFTs.length; i++) {
            if (ownedNFTs[i] != id) {
                newOwnedNFTs[index] = ownedNFTs[i];
                index++;
            }
        }

        _ownedNFTs[msg.sender] = newOwnedNFTs;
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

        _metadata.approvedMerchant = _approvedMerchant;
        _metadata.approvedPayment = _approvedPayment;
    }

    /**
     * Mint a new NFT to the user with the given id
     */
    function mint(
        address to,
        uint256 id
    ) external onlyWhileListedUsers onlyInitialized {
        require(_currentSupply < _supply, "40001: Supply limit reached");
        require(_owners[id] == address(0), "40002: NFT already minted");
        require(id < _supply, "40003: Id is greater than supply limit");

        _mint(to, id, 1, "");
        _reedemState[id] = RedeemState.NOT_REDEEMED;
        _ownedNFTs[to].push(id);
        _mintTime[id] = block.timestamp;
        _owners[id] = to;
        _currentSupply += 1;
    }

    /**
     * Reedeem NFT using token Id. Only whitelisted users can redeem NFT
     */
    function redeem(
        uint256 id,
        RedeemCouponOpts memory _redeemCouponOpts
    ) external onlyWhileListedUsers onlyInitialized {
        address userAddress = _redeemCouponOpts.userAddress;
        require(
            recoverSigner(
                _redeemCouponOpts.userSignature,
                _redeemCouponOpts.userMessage
            ) == userAddress,
            "40101: Invalid user signature"
        );

        require(
            balanceOf(userAddress, id) > 0,
            "40004: User does not own the NFT"
        );
        require(
            _reedemState[id] == RedeemState.NOT_REDEEMED,
            "40005: NFT already redeemed"
        );

        _reedemState[id] = RedeemState.REDEEMED;
    }

    function listByOwner(
        address owner
    ) external view onlyInitialized returns (GetTokenByIdResponse[] memory) {
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
    ) external view onlyInitialized returns (GetTokenByIdResponse memory) {
        address owner = _owners[id];
        uint256 mintTime = _mintTime[id];

        require(owner != address(0), "40402: Given token id not found");

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
