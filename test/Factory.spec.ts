import { expect } from "chai";
import hre from "hardhat";
import {
  CreateCouponOptsStruct,
  NFTContract,
  RedeemCouponOptsStruct,
} from "../typechain-types/contracts/nft/Nft1155.sol/NFTContract";
import { ListAllCouponsOptsStruct } from "../typechain-types/contracts/factory/Factory.sol/NFTCouponFactory";

interface TestCase<Arg> {
  name: string;
  args: Arg;
}

describe("Factory", () => {
  describe("createCoupon", () => {
    describe("single create", () => {
      interface Arg {
        deployer(): Promise<any>;
        creator(): Promise<any>;
        initialOwners(): Promise<any[]>;
        opts: CreateCouponOptsStruct;
        expectedCouponId?: string;
        expectedRevert?: string;
      }

      const testCases: TestCase<Arg>[] = [
        {
          name: "should be able to create coupon",
          args: {
            deployer: async () => {
              const [owner] = await hre.ethers.getSigners();
              return owner;
            },
            creator: async () => {
              const [owner] = await hre.ethers.getSigners();
              return owner;
            },
            initialOwners: async () => {
              const [owner] = await hre.ethers.getSigners();
              return [owner];
            },
            opts: {
              creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
              author: "a",
              supply: 10,
              name: "Hello",
              desc: "World",
              fieldId: "1",
              price: "10",
              currency: "cny",
              metadata: {
                approvedMerchant: [],
                approvedPayment: [],
                couponType: "1",
                couponSubtitle: "subtitle",
                couponDetails: "",
                url: "https://google.com",
                expirationTime: 0,
                expirationStartTime: 0,
                rule: {
                  value: 1,
                  claimLimit: 1,
                  isTransfer: false,
                },
                redeemState: 0,
                approveTime: 0,
                approveDuration: 0,
              },
            },
            expectedCouponId: "01010020200",
          },
        },
      ];

      testCases.forEach(({ name, args }) => {
        it(name, async () => {
          const { creator, initialOwners, opts, expectedCouponId } = args;

          const creatorSigner = await creator();
          const initialOwnerSigners = await initialOwners();

          const FactoryContract = await hre.ethers.getContractFactory(
            "NFTCouponFactory"
          );

          const NftContract = await hre.ethers.getContractFactory(
            "NFTContract"
          );

          const nftContract = await NftContract.deploy([]);
          await nftContract.waitForDeployment();

          const factory = await FactoryContract.deploy(
            initialOwnerSigners.map((signer) => signer.address)
          );
          await factory.waitForDeployment();

          await nftContract.approve(await factory.getAddress());
          await factory.addContract(nftContract);

          const response = await factory
            .connect(creatorSigner)
            .createCoupon(opts);
          const result = await response.wait();
          expect(response).to.be.ok;

          const couponCreatedEvent = result?.logs[0] as any;
          expect(couponCreatedEvent.args[0]).to.equal(expectedCouponId);
        });
      });
    });

    describe("multiple create", () => {
      interface Arg {
        createNumber: number;
      }

      const testCases: TestCase<Arg>[] = [
        {
          name: "should be able to create multiple coupons",
          args: {
            createNumber: 3,
          },
        },
      ];

      testCases.forEach(({ name, args }) => {
        it(name, async () => {
          const FactoryContract = await hre.ethers.getContractFactory(
            "NFTCouponFactory"
          );

          const factory = await FactoryContract.deploy([]);
          await factory.waitForDeployment();

          const createOpts = {
            creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
            author: "a",
            supply: 10,
            name: "Hello",
            desc: "World",
            fieldId: "1",
            price: "10",
            currency: "cny",
            metadata: {
              approvedMerchant: [],
              approvedPayment: [],
              couponType: "1",
              couponSubtitle: "subtitle",
              couponDetails: "",
              url: "https://google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 1,
                claimLimit: 1,
                isTransfer: false,
              },
              redeemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          };
          // simulates add multiple nft contracts
          for (let i = 0; i < args.createNumber; i++) {
            const NftContract = await hre.ethers.getContractFactory(
              "NFTContract"
            );
            const nft = await NftContract.deploy([]);
            await nft.waitForDeployment();
            await nft.approve(await factory.getAddress());
            await factory.addContract(nft);
          }

          for (let i = 0; i < args.createNumber; i++) {
            const response = await factory.createCoupon(createOpts);
            expect(response).to.be.ok;
          }
        });
      });
    });
  });

  describe("mintCoupon (mint)", () => {
    interface Arg {
      creator(): Promise<any>;
      initialOwners(): Promise<any[]>;
      transferSender(): Promise<any>;
      receiver(): Promise<any>;
      opts: CreateCouponOptsStruct;
      mintCouponId: string;
      expectedCouponId?: string;
      expectedRevert?: string;
    }

    const testCases: TestCase<Arg>[] = [
      {
        name: "should be able to transfer coupon",
        args: {
          creator: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          transferSender: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          initialOwners: async () => {
            const [owner] = await hre.ethers.getSigners();
            return [owner];
          },
          receiver: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return address1;
          },
          opts: {
            creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
            author: "a",
            supply: 10,
            name: "Hello",
            desc: "World",
            fieldId: "1",
            price: "10",
            currency: "cny",
            metadata: {
              approvedMerchant: [],
              approvedPayment: [],
              couponType: "1",
              couponSubtitle: "subtitle",
              couponDetails: "",
              url: "https://google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 1,
                claimLimit: 1,
                isTransfer: false,
              },
              redeemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          },
          expectedCouponId: "01010020200",
          mintCouponId: "01010020200",
        },
      },
      {
        name: "should revert if coupon does not exist",
        args: {
          creator: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          transferSender: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          initialOwners: async () => {
            const [owner] = await hre.ethers.getSigners();
            return [owner];
          },
          receiver: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return address1;
          },
          opts: {
            creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
            author: "a",
            supply: 10,
            name: "Hello",
            desc: "World",
            fieldId: "1",
            price: "10",
            currency: "cny",
            metadata: {
              approvedMerchant: [],
              approvedPayment: [],
              couponType: "1",
              couponSubtitle: "subtitle",
              couponDetails: "",
              url: "https://google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 1,
                claimLimit: 1,
                isTransfer: false,
              },
              redeemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          },
          expectedRevert:
            "40401: NFT contract for the given coupon id not found",
          mintCouponId: "01011020201",
        },
      },
      {
        name: "Only whitelisted user can transfer coupon",
        args: {
          creator: async () => {
            const [owner, address2] = await hre.ethers.getSigners();
            return address2;
          },
          transferSender: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          initialOwners: async () => {
            const [owner] = await hre.ethers.getSigners();
            return [owner];
          },
          receiver: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return address1;
          },
          opts: {
            creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
            author: "a",
            supply: 10,
            name: "Hello",
            desc: "World",
            fieldId: "1",
            price: "10",
            currency: "cny",
            metadata: {
              approvedMerchant: [],
              approvedPayment: [],
              couponType: "1",
              couponSubtitle: "subtitle",
              couponDetails: "",
              url: "https://google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 1,
                claimLimit: 1,
                isTransfer: false,
              },
              redeemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          },
          expectedRevert:
            "40301: Only whitelisted users can call this function",
          mintCouponId: "01010020201",
        },
      },
    ];

    testCases.forEach(({ name, args }) => {
      it(name, async () => {
        const { creator, initialOwners, opts, expectedCouponId, mintCouponId } =
          args;
        const creatorSigner = await creator();
        const transferSenderSigner = await args.transferSender();
        const initialOwnerSigners = await initialOwners();

        const NftContract = await hre.ethers.getContractFactory("NFTContract");
        const FactoryContract = await hre.ethers.getContractFactory(
          "NFTCouponFactory"
        );

        const nft = await NftContract.deploy([]);
        await nft.waitForDeployment();

        const factory = await FactoryContract.deploy(
          initialOwnerSigners.map((signer) => signer.address)
        );
        await factory.waitForDeployment();
        await nft.approve(await factory.getAddress());
        await factory.addContract(nft);

        const response = await factory
          .connect(transferSenderSigner)
          .createCoupon(opts);
        expect(response).to.be.ok;
        const receiverSigner = await args.receiver();

        if (args.expectedRevert) {
          await expect(
            factory.connect(creatorSigner).mintCoupon({
              couponId: mintCouponId,
              receiverAddr: receiverSigner.address,
            })
          ).to.be.revertedWith(args.expectedRevert);
          return;
        }

        const responseTransfer = await factory
          .connect(creatorSigner)
          .mintCoupon({
            couponId: mintCouponId,
            receiverAddr: receiverSigner.address,
          });
        const resultTransfer = await responseTransfer.wait();
        expect(responseTransfer).to.be.ok;

        const couponTransferEvent = resultTransfer?.logs[1] as any;
        expect(couponTransferEvent.args[0]).to.equal(expectedCouponId);
        expect(couponTransferEvent.args[1]).to.equal(creatorSigner.address);
        expect(couponTransferEvent.args[2]).to.equal(receiverSigner.address);
      });
    });
  });

  describe("redeemCoupon", () => {
    interface Arg {
      creator(): Promise<any>;
      initialOwners(): Promise<any[]>;
      receiver(): Promise<any>;
      redeemSender(): Promise<any>;
      opts: CreateCouponOptsStruct;
      redeemCouponId: string;
      expectedCouponId?: string;
      expectedRevert?: string;
    }

    const testCases: TestCase<Arg>[] = [
      {
        name: "should be able to redeem coupon",
        args: {
          creator: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          redeemSender: async () => {
            const [owner] = await hre.ethers.getSigners();
            return owner;
          },
          initialOwners: async () => {
            const [owner] = await hre.ethers.getSigners();
            return [owner];
          },
          receiver: async () => {
            const [owner, address1] = await hre.ethers.getSigners();
            return address1;
          },
          opts: {
            creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
            author: "a",
            supply: 10,
            name: "Hello",
            desc: "World",
            fieldId: "1",
            price: "10",
            currency: "cny",
            metadata: {
              approvedMerchant: [],
              approvedPayment: [],
              couponType: "1",
              couponSubtitle: "subtitle",
              couponDetails: "",
              url: "https://google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 1,
                claimLimit: 1,
                isTransfer: false,
              },
              redeemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          },
          expectedCouponId: "01010020200",
          redeemCouponId: "01010020200",
        },
      },
    ];

    testCases.forEach(({ name, args }) => {
      it(name, async () => {
        const {
          creator,
          initialOwners,
          opts,
          expectedCouponId,
          redeemCouponId,
        } = args;
        const creatorSigner = await creator();
        const transferSenderSigner = await args.creator();
        const initialOwnerSigners = await initialOwners();
        const redeemSenderSigner = await args.redeemSender();
        const receiverSigner = await args.receiver();

        const NftContract = await hre.ethers.getContractFactory("NFTContract");
        const FactoryContract = await hre.ethers.getContractFactory(
          "NFTCouponFactory"
        );

        const factory = await FactoryContract.deploy(
          initialOwnerSigners.map((signer) => signer.address)
        );
        const nft = await NftContract.deploy([]);
        await nft.waitForDeployment();
        await factory.waitForDeployment();

        await nft.approve(await factory.getAddress());
        await factory.addContract(nft);

        await factory.connect(transferSenderSigner).createCoupon(opts);
        await factory.connect(creatorSigner).mintCoupon({
          couponId: redeemCouponId,
          receiverAddr: receiverSigner.address,
        });
        const message = "hello";
        const signedMessage = await receiverSigner.signMessage(message);

        const redeemOpts: RedeemCouponOptsStruct = {
          couponId: redeemCouponId,
          paymentAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
          paymentSignature: "0x",
          paymentMessage: "0x",
          paymentNonce: 0,
          userAddress: receiverSigner.address,
          userSignature: signedMessage,
          userMessage: message,
          userNonce: 0,
          approveTime: 0,
          approveDuration: 0,
        };

        if (args.expectedRevert) {
          await expect(
            factory.connect(redeemSenderSigner).redeemCoupon(redeemOpts)
          ).to.be.revertedWith(args.expectedRevert);
          return;
        }

        await factory.connect(redeemSenderSigner).redeemCoupon(redeemOpts);
      });
    });
  });

  describe("listByOwner", () => {
    describe("multiple coupon", () => {
      interface Arg {
        userAddress: () => Promise<string>;
        expectedNumber: number;
      }

      const testCases: TestCase<Arg>[] = [
        {
          name: "should return empty when user doesn't own any nft",
          args: {
            userAddress: async () => {
              const [owner] = await hre.ethers.getSigners();
              return owner.address;
            },
            expectedNumber: 0,
          },
        },
        {
          name: "should return 1 when user owns 1 nft",
          args: {
            userAddress: async () => {
              const [owner] = await hre.ethers.getSigners();
              return owner.address;
            },
            expectedNumber: 1,
          },
        },
        {
          name: "should return correct number when user owns multiple nfts",
          args: {
            userAddress: async () => {
              const [owner] = await hre.ethers.getSigners();
              return owner.address;
            },
            expectedNumber: 3,
          },
        },
      ];

      testCases.forEach(({ name, args }) => {
        it(name, async () => {
          const { userAddress, expectedNumber } = args;

          const FactoryContract = await hre.ethers.getContractFactory(
            "NFTCouponFactory"
          );

          const createOpts = {
            creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
            author: "a",
            supply: 10,
            name: "Hello",
            desc: "World",
            fieldId: "1",
            price: "10",
            currency: "cny",
            metadata: {
              approvedMerchant: [],
              approvedPayment: [],
              couponType: "1",
              couponSubtitle: "subtitle",
              couponDetails: "",
              url: "https://google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 1,
                claimLimit: 1,
                isTransfer: false,
              },
              redeemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          };

          const factory = await FactoryContract.deploy([]);
          await factory.waitForDeployment();

          for (let i = 0; i < args.expectedNumber; i++) {
            const NftContract = await hre.ethers.getContractFactory(
              "NFTContract"
            );
            const nft = await NftContract.deploy([]);
            await nft.waitForDeployment();

            await nft.approve(await factory.getAddress());
            await factory.addContract(nft);

            await factory.createCoupon(createOpts);
            await factory.mintCoupon({
              couponId: `0101${i}020200`,
              receiverAddr: await userAddress(),
            });
          }

          const listOpts: ListAllCouponsOptsStruct = {
            userAddress: await userAddress(),
            page: 0,
            pageSize: 0,
          };
          const response = await factory.listAllCoupons(listOpts);
          expect(response.length).to.equal(expectedNumber);
          for (let i = 0; i < expectedNumber; i++) {
            expect(response[i].couponId).to.equal(`0101${i}020200`);
          }
        });
      });
    });

    describe("single coupon", () => {
      interface Arg {
        userAddress: () => Promise<string>;
        numberOfClaims: number;
        supply: number;
        expectedTokens: string[];
      }

      const testCases: TestCase<Arg>[] = [
        {
          name: "should return empty when user doesn't own any nft",
          args: {
            userAddress: async () => {
              const [owner] = await hre.ethers.getSigners();
              return owner.address;
            },
            supply: 100,
            numberOfClaims: 10,
            expectedTokens: [
              "010100203000",
              "010100203001",
              "010100203002",
              "010100203003",
              "010100203004",
              "010100203005",
              "010100203006",
              "010100203007",
              "010100203008",
              "010100203009",
            ],
          },
        },
        {
          name: "should return empty when user doesn't own any nft",
          args: {
            userAddress: async () => {
              const [owner] = await hre.ethers.getSigners();
              return owner.address;
            },
            supply: 1,
            numberOfClaims: 1,
            expectedTokens: ["0101002010"],
          },
        },
      ];

      testCases.forEach(({ name, args }) => {
        it(name, async () => {
          const { userAddress, supply, expectedTokens, numberOfClaims } = args;
          const signers = await hre.ethers.getSigners();

          const FactoryContract = await hre.ethers.getContractFactory(
            "NFTCouponFactory"
          );

          const createOpts = {
            creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
            author: "a",
            supply: supply,
            name: "Hello",
            desc: "World",
            fieldId: "1",
            price: "10",
            currency: "cny",
            metadata: {
              approvedMerchant: [],
              approvedPayment: [],
              couponType: "1",
              couponSubtitle: "subtitle",
              couponDetails: "",
              url: "https://google.com",
              expirationTime: 0,
              expirationStartTime: 0,
              rule: {
                value: 1,
                claimLimit: 1,
                isTransfer: false,
              },
              redeemState: 0,
              approveTime: 0,
              approveDuration: 0,
            },
          };

          const factory = await FactoryContract.deploy([]);
          await factory.waitForDeployment();

          const NftContract = await hre.ethers.getContractFactory(
            "NFTContract"
          );
          const nft = await NftContract.deploy([]);
          await nft.waitForDeployment();

          await nft.approve(await factory.getAddress());
          await factory.addContract(nft);
          await factory.createCoupon(createOpts);

          for (let i = 0; i < numberOfClaims; i++) {
            await factory.mintCoupon({
              couponId: `0101002020${i}`,
              receiverAddr: await userAddress(),
            });
          }
          const listOpts: ListAllCouponsOptsStruct = {
            userAddress: await userAddress(),
            page: 0,
            pageSize: 0,
          };

          for (const expectedToken of expectedTokens) {
            const response = await factory.listAllCoupons(listOpts);
            expect(response.length).to.equal(numberOfClaims);
            expect(response.find((r) => r.couponId === expectedToken)).not.to.be
              .undefined;
            expect(
              response.every((r) => r.creatorAddress === signers[0].address)
            ).to.be.true;
          }
        });
      });
    });

    describe("transer and then list", () => {
      it("should be able to transfer and list", async () => {
        const [owner, user1, user2] = await hre.ethers.getSigners();
        const FactoryContract = await hre.ethers.getContractFactory(
          "NFTCouponFactory"
        );

        const createOpts = {
          creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
          author: "a",
          supply: 100,
          name: "Hello",
          desc: "World",
          fieldId: "1",
          price: "10",
          currency: "cny",
          metadata: {
            approvedMerchant: [],
            approvedPayment: [],
            couponType: "1",
            couponSubtitle: "subtitle",
            couponDetails: "",
            url: "https://google.com",
            expirationTime: 0,
            expirationStartTime: 0,
            rule: {
              value: 1,
              claimLimit: 1,
              isTransfer: true,
            },
            redeemState: 0,
            approveTime: 0,
            approveDuration: 0,
          },
        };

        const factory = await FactoryContract.deploy([]);
        await factory.waitForDeployment();

        const NftContract = await hre.ethers.getContractFactory("NFTContract");
        const nft = await NftContract.deploy([]);
        await nft.waitForDeployment();

        await nft.approve(await factory.getAddress());
        await factory.addContract(nft);

        await factory.createCoupon(createOpts);
        // user1 mint 2 coupons and send to user1
        await factory.mintCoupon({
          couponId: `01010020200`,
          receiverAddr: user1.address,
        });
        await factory.mintCoupon({
          couponId: `01010020201`,
          receiverAddr: user1.address,
        });

        const listOpts: ListAllCouponsOptsStruct = {
          userAddress: user1.address,
          page: 0,
          pageSize: 0,
        };
        const response = await factory.listAllCoupons(listOpts);
        expect(response.length).to.equal(2);

        // user1 transfer the coupon to user2
        // get the nft address by coupon id
        const nftAddress = await factory.getNftAddressByCouponId(`01010020200`);
        const nftFromAddress = nft.attach(nftAddress.nftAddress) as NFTContract;
        await nftFromAddress
          .connect(user1)
          .transfer(user2.address, nftAddress.tokenId);

        // list the coupon for user2
        const listOptsUser2: ListAllCouponsOptsStruct = {
          userAddress: user2.address,
          page: 0,
          pageSize: 0,
        };
        const responseUser2 = await factory.listAllCoupons(listOptsUser2);
        expect(responseUser2.length).to.equal(1);
        expect(responseUser2[0].ownerAddr).to.equal(user2.address);

        // list the coupon for user1
        const responseUser1 = await factory.listAllCoupons(listOpts);
        expect(responseUser1.length).to.equal(1);
        expect(responseUser1[0].ownerAddr).to.equal(user1.address);
      });

      it("should be able to multiple transfers and list", async () => {
        const [owner, user1, user2, user3] = await hre.ethers.getSigners();
        const FactoryContract = await hre.ethers.getContractFactory(
          "NFTCouponFactory"
        );

        const createOpts = {
          creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
          author: "a",
          supply: 100,
          name: "Hello",
          desc: "World",
          fieldId: "1",
          price: "10",
          currency: "cny",
          metadata: {
            approvedMerchant: [],
            approvedPayment: [],
            couponType: "1",
            couponSubtitle: "subtitle",
            couponDetails: "",
            url: "https://google.com",
            expirationTime: 0,
            expirationStartTime: 0,
            rule: {
              value: 1,
              claimLimit: 1,
              isTransfer: true,
            },
            redeemState: 0,
            approveTime: 0,
            approveDuration: 0,
          },
        };

        const factory = await FactoryContract.deploy([]);
        await factory.waitForDeployment();

        const NftContract = await hre.ethers.getContractFactory("NFTContract");
        const nft = await NftContract.deploy([]);
        await nft.waitForDeployment();

        await nft.approve(await factory.getAddress());
        await factory.addContract(nft);

        await factory.createCoupon(createOpts);
        // user1 mint 2 coupons and send to user1
        await factory.mintCoupon({
          couponId: `01010020200`,
          receiverAddr: user1.address,
        });
        await factory.mintCoupon({
          couponId: `01010020201`,
          receiverAddr: user1.address,
        });
        await factory.mintCoupon({
          couponId: `01010020202`,
          receiverAddr: user1.address,
        });
        await factory.mintCoupon({
          couponId: `01010020203`,
          receiverAddr: user1.address,
        });

        const listOpts: ListAllCouponsOptsStruct = {
          userAddress: user1.address,
          page: 0,
          pageSize: 0,
        };
        const response = await factory.listAllCoupons(listOpts);
        expect(response.length).to.equal(4);

        // user1 transfer the coupon to user2
        // get the nft address by coupon id
        const nftAddress = await factory.getNftAddressByCouponId(`01010020200`);
        const nftAddress2 = await factory.getNftAddressByCouponId(
          `01010020201`
        );
        const nftAddress3 = await factory.getNftAddressByCouponId(
          `01010020202`
        );
        const nftAddress4 = await factory.getNftAddressByCouponId(
          `01010020203`
        );

        const nftFromAddress = nft.attach(nftAddress.nftAddress) as NFTContract;
        await nftFromAddress
          .connect(user1)
          .transfer(user2.address, nftAddress.tokenId);

        await nftFromAddress
          .connect(user1)
          .transfer(user2.address, nftAddress2.tokenId);

        // list the coupon for user2
        const listOptsUser2: ListAllCouponsOptsStruct = {
          userAddress: user2.address,
          page: 0,
          pageSize: 0,
        };
        const responseUser2 = await factory.listAllCoupons(listOptsUser2);
        expect(responseUser2.length).to.equal(2);

        // list the coupon for user1
        const responseUser1 = await factory.listAllCoupons(listOpts);
        expect(responseUser1.length).to.equal(2);

        // user2 transfer the coupon to user1
        const nftFromAddress2 = nft.attach(
          nftAddress2.nftAddress
        ) as NFTContract;
        await nftFromAddress2
          .connect(user2)
          .transfer(user1.address, nftAddress2.tokenId);

        // list the coupon for user2
        const responseUser2AfterTransfer = await factory.listAllCoupons(
          listOptsUser2
        );
        expect(responseUser2AfterTransfer.length).to.equal(1);

        // list the coupon for user1
        const responseUser1AfterTransfer = await factory.listAllCoupons(
          listOpts
        );
        expect(responseUser1AfterTransfer.length).to.equal(3);

        // user2 transfer the coupon to user3
        await nftFromAddress2
          .connect(user2)
          .transfer(user3.address, nftAddress.tokenId);

        // list the coupon for user2
        const responseUser2AfterTransfer2 = await factory.listAllCoupons(
          listOptsUser2
        );
        expect(responseUser2AfterTransfer2.length).to.equal(0);

        // list the coupon for user1
        const responseUser1AfterTransfer2 = await factory.listAllCoupons(
          listOpts
        );
        expect(responseUser1AfterTransfer2.length).to.equal(3);

        // list the coupon for user3
        const listOptsUser3: ListAllCouponsOptsStruct = {
          userAddress: user3.address,
          page: 0,
          pageSize: 0,
        };
        const responseUser3 = await factory.listAllCoupons(listOptsUser3);
        expect(responseUser3.length).to.equal(1);

        // user1 transfer the coupon to user3
        await nftFromAddress
          .connect(user1)
          .transfer(user3.address, nftAddress2.tokenId);

        // list the coupon for user2
        const responseUser2AfterTransfer3 = await factory.listAllCoupons(
          listOptsUser2
        );
        expect(responseUser2AfterTransfer3.length).to.equal(0);

        // list the coupon for user1
        const responseUser1AfterTransfer3 = await factory.listAllCoupons(
          listOpts
        );
        expect(responseUser1AfterTransfer3.length).to.equal(2);

        // list the coupon for user3
        const responseUser3AfterTransfer = await factory.listAllCoupons(
          listOptsUser3
        );
        expect(responseUser3AfterTransfer.length).to.equal(2);
      });
    });
  });

  describe("getNftAddressByCouponId", () => {
    it("should be able to get contract by coupon id", async () => {
      const [owner, user1, user2] = await hre.ethers.getSigners();
      const FactoryContract = await hre.ethers.getContractFactory(
        "NFTCouponFactory"
      );

      const createOpts = {
        creatorAddress: "0x5fdF6784aEa0c6BAfe91c606158470827c17811b",
        author: "a",
        supply: 10,
        name: "Hello",
        desc: "World",
        fieldId: "1",
        price: "10",
        currency: "cny",
        metadata: {
          approvedMerchant: [],
          approvedPayment: [],
          couponType: "1",
          couponSubtitle: "subtitle",
          couponDetails: "",
          url: "https://google.com",
          expirationTime: 0,
          expirationStartTime: 0,
          rule: {
            value: 1,
            claimLimit: 1,
            isTransfer: true,
          },
          redeemState: 0,
          approveTime: 0,
          approveDuration: 0,
        },
      };

      const factory = await FactoryContract.deploy([]);
      await factory.waitForDeployment();

      const NftContract = await hre.ethers.getContractFactory("NFTContract");
      const nft = await NftContract.deploy([]);
      await nft.waitForDeployment();

      await nft.approve(await factory.getAddress());
      await factory.addContract(nft);

      // create one coupon
      await factory.createCoupon(createOpts);
      // mint a coupon and send to user1
      await factory.mintCoupon({
        couponId: `0101002010`,
        receiverAddr: user1.address,
      });

      // use factory to get nft address by coupon id
      // response contains nft address and token id
      const response = await factory.getNftAddressByCouponId(`0101002010`);
      // create the nft contract from the address
      const nftFromAddress = nft.attach(response.nftAddress) as NFTContract;
      // get the nft detail by token id
      const nftDetail = await nftFromAddress.getById(response.tokenId);
      expect(nftDetail.couponId).to.equal(response.tokenId);

      // transfer the nft to user2 on behalf of user1
      await nftFromAddress
        .connect(user1)
        .transfer(user2.address, response.tokenId);

      // check if user2 has the nft
      const user2Balance = await nftFromAddress.balanceOf(
        user2.address,
        response.tokenId
      );
      expect(user2Balance).to.equal(1);
    });
  });
});
