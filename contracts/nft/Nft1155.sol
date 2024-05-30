// // SPDX-License-Identifier: MIT
// pragma solidity ^0.8.19;
// import "@openzeppelin/contracts/token/ERC1155/ERC1155.sol";
// import "./INftContract.sol";

// enum NFTStatus {
//     Active,
//     Redeemed
// }

// contract NFTContract is ERC1155, INFTContract {
//     using Counters for Counters.Counter;
//     Counters.Counter private _tokenIds;
//     mapping(uint256 => NFTStatus) public nftStatus;

//     constructor(
//         string memory name,
//         string memory symbol
//     ) ERC1155(name, symbol) {}

//     function mint(
//         address account,
//         uint256 id,
//         uint256 amount,
//         bytes memory data
//     ) public onlyOwner {
//         _mint(account, id, amount, data);
//     }

//     /**
//      * Transfer tokens from one address to another
//      */
//     function transfer(
//         address from,
//         address to,
//         uint256 id,
//         uint256 amount
//     ) public onlyOwner {
//         safeTransferFrom(from, to, id, amount, "");
//     }

//     function reedeem(uint256 id) public onlyOwner {
//         require(nftStatus[id] == NFTStatus.Active, "NFT is not active");
//         nftStatus[id] = NFTStatus.Redeemed;
//     }

//     function approve(address to, uint256 id, uint256 amount) public onlyOwner {
//         setApprovalForAll(to, true);
//     }
// }
