import { expect } from "chai";
import hre from "hardhat";
import { CreateCouponOptsStruct } from "../typechain-types/contracts/nft/Nft1155.sol/NFTContract";
import dayjs from "dayjs";

interface TestCase<T> {
  name: string;
  args: T;
}

const now = dayjs();

describe("Nft1155", () => {
  describe("Constructor", () => {
    interface Args {
      opts: CreateCouponOptsStruct;
      numOwners: number;
    }

    const testCases: TestCase<Args>[] = [
      {
        name: "should be able to deploy the contract without initial owners",
        args: {
          opts: {
            creatorAddress: "",
            author: "hello",
            supply: 10,
            name: "test",
            desc: "test desc",
            fieldId: "1",
            price: "10",
            currency: "CNY",
            metadata: {
              approvedMerchant: [
                {
                  approvedMerchantName: "test",
                  approvedMerchantAddr:
                    "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
                },
              ],
              approvedPayment: [
                {
                  approvedPaymentName: "test",
                  approvedPaymentAddr:
                    "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
                },
              ],
              couponType: "1",
              couponSubtitle: "Subtitle",
              couponDetails: "Some",
              url: "https://www.google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 0,
                claimLimit: 0,
                isTransfer: false,
              },
              reedemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          },
          numOwners: 0,
        },
      },
      {
        name: "should be able to deploy the contract with initial owners",
        args: {
          opts: {
            creatorAddress: "",
            author: "hello",
            supply: 10,
            name: "test",
            desc: "test desc",
            fieldId: "1",
            price: "10",
            currency: "CNY",
            metadata: {
              approvedMerchant: [
                {
                  approvedMerchantName: "test",
                  approvedMerchantAddr:
                    "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
                },
              ],
              approvedPayment: [
                {
                  approvedPaymentName: "test",
                  approvedPaymentAddr:
                    "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
                },
              ],
              couponType: "1",
              couponSubtitle: "Subtitle",
              couponDetails: "Some",
              url: "https://www.google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 0,
                claimLimit: 0,
                isTransfer: false,
              },
              reedemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          },
          numOwners: 3,
        },
      },
    ];

    testCases.forEach(({ name, args }) => {
      it(name, async () => {
        const nft = await hre.ethers.getContractFactory("NFTContract");
        const addresses: string[] = [];
        const signers = await hre.ethers.getSigners();
        for (let i = 0; i < args.numOwners; i++) {
          addresses.push(signers[i].address);
        }

        const contract = await nft.deploy(args.opts, signers);
        const response = await contract.waitForDeployment();
        expect(response).to.be.ok;
      });
    });
  });

  describe("Mint", () => {
    interface Args {
      opts: CreateCouponOptsStruct;
      initialOwners: () => Promise<string[]>;
      minter: () => Promise<any>;
      mintTo: () => Promise<[string, number]>;
      expectRevertMessage?: string;
      expectBalance?: number;
    }

    const testCases: TestCase<Args>[] = [
      {
        name: "should not be able to mint a token when over the supply",
        args: {
          minter: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          mintTo: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return [address1.address, 1];
          },
          initialOwners: async () => {
            const [owner] = await hre.ethers.getSigners();
            return [owner.address];
          },
          expectRevertMessage: "ERC1155: mint to the zero address",
          opts: {
            creatorAddress: "",
            author: "hello",
            supply: 0,
            name: "test",
            desc: "test desc",
            fieldId: "1",
            price: "10",
            currency: "CNY",
            metadata: {
              approvedMerchant: [
                {
                  approvedMerchantName: "test",
                  approvedMerchantAddr:
                    "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
                },
              ],
              approvedPayment: [
                {
                  approvedPaymentName: "test",
                  approvedPaymentAddr:
                    "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
                },
              ],
              couponType: "1",
              couponSubtitle: "Subtitle",
              couponDetails: "Some",
              url: "https://www.google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 0,
                claimLimit: 0,
                isTransfer: false,
              },
              reedemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          },
        },
      },
    ];

    testCases.forEach(({ name, args }) => {
      it(name, async () => {
        const nft = await hre.ethers.getContractFactory("NFTContract");
        const initialOwners = await args.initialOwners();
        const contract = await nft.deploy(args.opts, initialOwners);

        const signer = await args.minter();
        const [mintToAddress, tokenId] = await args.mintTo();

        const response = await contract
          .connect(signer)
          .mint(mintToAddress, tokenId);
        expect(response).to.be.ok;

        if (args.expectRevertMessage) {
          await expect(response).to.be.revertedWith(args.expectRevertMessage);
        } else {
          expect(await contract.balanceOf(mintToAddress, tokenId)).to.be.equal(
            args.expectBalance
          );
        }
      });
    });
  });
});
