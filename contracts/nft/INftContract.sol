// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

interface INFTContract {
    /**
     * Mint NFT tokens
     */
    function mint(
        address account,
        uint256 id,
        uint256 amount,
        bytes memory data
    ) external;

    /**
     * Transfer tokens from one address to another
     */
    function transfer(
        address from,
        address to,
        uint256 id,
        uint256 amount
    ) external;

    /**
     * Reedeem NFT using token Id. Only owner can call this function
     * @param id Token Id
     */
    function reedeem(uint256 id) external;

    /**
     * List all NFTs owned by an address
     */
    function listByOwner(
        address owner
    ) external view returns (uint256[] memory);

    /**
     * Get NFT by Id
     */
    function getById(
        uint256 id
    )
        external
        view
        returns (uint256, uint256, address, uint256, uint256, string memory);
}
