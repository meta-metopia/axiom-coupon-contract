import hre from "hardhat";
import {
  NFTContract,
  NFTCouponFactory,
  NFTCouponFactory__factory,
} from "../../typechain-types";
import Progress from "ts-progress";
import { StorageClass } from "../storage/storage.interface";
import consola from "consola";

export class Contract {
  private factory?: NFTCouponFactory__factory;

  constructor() {}

  async initialize() {
    this.factory = await hre.ethers.getContractFactory("NFTCouponFactory");
  }

  async deployFactoryWithContracts(num: number, initialOwners: string[]) {
    const progress = Progress.create({
      total: num + 1,
      title: "Deploying NFTCouponFactory with contracts",
    });
    const nftCouponFactory = (await this.factory!.deploy(
      initialOwners
    )) as NFTCouponFactory;

    await nftCouponFactory.waitForDeployment();
    progress.update();

    for (let i = 0; i < num; i++) {
      progress.update();
      const nftCoupon = await hre.ethers.getContractFactory("NFTContract");
      const nftCouponContract = await nftCoupon.deploy(initialOwners);
      await nftCouponContract.waitForDeployment();

      await nftCouponContract.approve(await nftCouponFactory.getAddress());
      await nftCouponFactory.addContract(nftCouponContract);
    }

    consola.success(
      "NFTCouponFactory deployed to:",
      await nftCouponFactory.getAddress()
    );
    consola.success(
      "Available contracts:",
      await nftCouponFactory.getAvailableContractsCount()
    );

    return await nftCouponFactory.getAddress();
  }

  async createCoupon(contract: string) {
    try {
      consola.info("Creating coupon to address:", contract);
      const nftCouponFactory = this.factory!.attach(
        contract
      ) as NFTCouponFactory;
      const [owner] = await hre.ethers.getSigners();

      const opts = {
        creatorAddress: owner.address,
        author: "a",
        supply: 10000,
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
      const response = await nftCouponFactory.createCoupon(opts as any);

      const availableCoupon =
        await nftCouponFactory.getAvailableContractsCount();
      const result = await response.wait();

      consola.success("Coupon created");
      consola.info("Available coupons remaining:", availableCoupon);
      const logs = result?.logs.filter(
        (log: any) => log.fragment.name === "CouponCreated"
      ) as any[];

      consola.info("Coupon created with starting id:", logs[0].args[0]);
    } catch (e) {
      console.error("Error creating coupon", e);
    }
  }

  async addMoreNft(num: number, contract: string, initialOwners: string[]) {
    const nftCouponFactory = this.factory!.attach(contract) as NFTCouponFactory;
    const progress = Progress.create({
      total: num,
      title: "Adding more NFT to NFTCouponFactory",
    });

    for (let i = 0; i < num; i++) {
      progress.update();
      const nftCoupon = await hre.ethers.getContractFactory("NFTContract");
      const nftCouponContract = await nftCoupon.deploy(initialOwners);
      await nftCouponContract.waitForDeployment();

      await nftCouponContract.approve(await nftCouponFactory.getAddress());
      await nftCouponFactory.addContract(nftCouponContract);
    }
  }

  async addApprovedUser(contract: string, address: string) {
    const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
    const nftCouponFactory = factory.attach(contract) as NFTContract;

    await nftCouponFactory.approve(address);
    consola.success("User added to approved list");
  }

  async listNftByOwner(contract: string, address: string) {
    const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
    const nftCouponFactory = factory.attach(contract) as NFTContract;

    const nftList = await nftCouponFactory.listByOwner(address);
    consola.info("NFT list for user:", nftList);
  }

  async approveUser(contract: string, address: string) {
    const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
    const nftCouponFactory = factory.attach(contract) as NFTContract;

    await nftCouponFactory.approve(address);
    consola.success("User approved");
  }

  async isApproved(contract: string, address: string) {
    const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
    const nftCouponFactory = factory.attach(contract) as NFTContract;

    const isApproved = await nftCouponFactory.isWhiteListed(address);
    consola.info("User is approved:", isApproved);
  }

  async isApprovedInCouponNft(
    contract: string,
    couponId: string,
    address: string
  ) {
    const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
    const nftCouponFactory = factory.attach(contract) as NFTCouponFactory;

    const couponResponse = await nftCouponFactory.getNftAddressByCouponId(
      couponId
    );

    const nftContract = await hre.ethers.getContractFactory("NFTContract");
    const nftContractInstance = nftContract.attach(
      couponResponse.nftAddress
    ) as NFTContract;
    consola.info(
      "User is approved:",
      await nftContractInstance.isWhiteListed(address)
    );
  }
}
