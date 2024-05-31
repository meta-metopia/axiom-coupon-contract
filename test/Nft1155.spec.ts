import { expect } from "chai";
import hre from "hardhat";
import {
  CreateCouponOptsStruct,
  RedeemCouponOptsStruct,
} from "../typechain-types/contracts/nft/Nft1155.sol/NFTContract";
import dayjs from "dayjs";
import { Signer } from "ethers";

interface TestCase<T> {
  name: string;
  args: T;
}

const now = dayjs();

async function signMessage(message: string, signer: Signer) {
  const result = await signer.signMessage(message);
  return result;
}

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
            creatorAddress: "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
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
            creatorAddress: "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
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
          expectRevertMessage: "40001: Supply limit reached",
          opts: {
            creatorAddress: "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
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
      {
        name: "should not be able to mint a token when over the supply",
        args: {
          minter: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          mintTo: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return [address1.address, 20];
          },
          initialOwners: async () => {
            const [owner] = await hre.ethers.getSigners();
            return [owner.address];
          },
          expectRevertMessage: "40003: Id is greater than supply limit",
          opts: {
            creatorAddress: "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
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
        },
      },
      {
        name: "should not be able to mint a token without being the minter",
        args: {
          minter: async () => {
            const [owner, address1, address2] = await hre.ethers.getSigners();
            return address2;
          },
          mintTo: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return [address1.address, 20];
          },
          initialOwners: async () => {
            const [owner] = await hre.ethers.getSigners();
            return [owner.address];
          },
          expectRevertMessage:
            "40301: Only whitelisted users can call this function",
          opts: {
            creatorAddress: "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
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
        },
      },
      {
        name: "should be able to mint a token when the minter is the owner",
        args: {
          minter: async () => {
            const [owner, address1, address2] = await hre.ethers.getSigners();
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
          expectBalance: 1,
          opts: {
            creatorAddress: "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
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
        },
      },
      {
        name: "should be able to mint a token when the minter is the whitelisted user",
        args: {
          minter: async () => {
            const [owner, address1, address2] = await hre.ethers.getSigners();
            return address2;
          },
          mintTo: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return [address1.address, 1];
          },
          initialOwners: async () => {
            const [owner, address1, address2] = await hre.ethers.getSigners();
            return [address2.address];
          },
          expectBalance: 1,
          opts: {
            creatorAddress: "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
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

        if (args.expectRevertMessage) {
          await expect(
            contract.connect(signer).mint(mintToAddress, tokenId)
          ).to.be.revertedWith(args.expectRevertMessage);
        } else {
          const response = await contract
            .connect(signer)
            .mint(mintToAddress, tokenId);
          expect(response).to.be.ok;
          expect(await contract.balanceOf(mintToAddress, tokenId)).to.be.equal(
            args.expectBalance
          );
        }
      });
    });
  });

  describe("Redeem", () => {
    interface Args {
      opts: CreateCouponOptsStruct;
      redeemOpts: () => Promise<RedeemCouponOptsStruct>;
      mintSigner: () => Promise<Signer>;
      mintTo: () => Promise<[string, number]>;
      redeemSigner: () => Promise<Signer>;
      initialOwners: () => Promise<string[]>;
      expectRevertMessage?: string;
      expectStatus?: number;
    }

    const testCases: TestCase<Args>[] = [
      {
        name: "should be able to redeem a token when the redeem signer is the owner",
        args: {
          initialOwners: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return [owner.address, address1.address];
          },
          mintSigner: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          mintTo: async () => {
            const [owner, address1, address2] = await hre.ethers.getSigners();
            return [address2.address, 1];
          },
          redeemSigner: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return address1;
          },
          redeemOpts: async () => {
            const [owner, address1, address2] = await hre.ethers.getSigners();
            const message = "hello";
            const signature = await signMessage(message, address2);

            const opts: RedeemCouponOptsStruct = {
              couponId: "",
              paymentAddress: address2.address,
              paymentSignature: signature,
              paymentMessage: "",
              paymentNonce: 0,
              userAddress: address2.address,
              userSignature: signature,
              userMessage: message,
              userNonce: 1,
              approveTime: 0,
              approveDuration: 0,
            };
            return opts;
          },
          expectStatus: 0,
          opts: {
            creatorAddress: "0xD7b241DF11d12FFB5f9DFfF0C2c5357C36c9B206",
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
        },
      },
    ];

    testCases.forEach(({ name, args }) => {
      it(name, async () => {
        const nft = await hre.ethers.getContractFactory("NFTContract");
        const initialOwners = await args.initialOwners();
        const contract = await nft.deploy(args.opts, initialOwners);

        const signer = await args.mintSigner();
        const [mintToAddress, tokenId] = await args.mintTo();
        const response = await contract
          .connect(signer)
          .mint(mintToAddress, tokenId);
        expect(response).to.be.ok;

        const redeemSigner = await args.redeemSigner();
        const redeemOpts = await args.redeemOpts();
        if (args.expectRevertMessage) {
          await expect(
            contract.connect(redeemSigner).redeem(tokenId, redeemOpts)
          ).to.be.revertedWith(args.expectRevertMessage);
        } else {
          const response = await contract
            .connect(redeemSigner)
            .redeem(tokenId, redeemOpts);

          expect(response).to.be.ok;

          const nft = await contract.getById(tokenId);
          expect(nft.metadata.reedemState).to.be.equal(args.expectStatus);
        }
      });
    });
  });
});
