import { expect } from "chai";
import hre from "hardhat";
import {
  CreateCouponOptsStruct,
  RedeemCouponOptsStruct,
} from "../typechain-types/contracts/nft/Nft1155.sol/NFTContract";
import dayjs from "dayjs";
import { Signer } from "ethers";

describe("Factory", () => {
  it("should be able to create factory", async () => {
    const factoryContract = await hre.ethers.getContractFactory(
      "NFTCouponFactory"
    );
    const [owner] = await hre.ethers.getSigners();
    const factory = await factoryContract.deploy([owner.address]);
    await factory.waitForDeployment();

    const opts: CreateCouponOptsStruct = {
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
        reedemState: 0,
        approveTime: 0,
        approveDuration: 0,
      },
    };
    const response = await factory.createCoupon(opts);
    expect(response).to.be.ok;
  });
});
