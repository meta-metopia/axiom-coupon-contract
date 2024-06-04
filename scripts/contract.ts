import hre from "hardhat";
import {
  NFTContract,
  NFTCouponFactory,
  NFTCouponFactory__factory,
} from "../typechain-types";
import Progress from "ts-progress";

export class Contract {
  private factory?: NFTCouponFactory__factory;
  private initialOwners = [
    //eft
    "0xfF6377b2a70F75b9Fd5A454a14B857049E31Dc1a",
    //mylink
    "0x4275297E78d1Bf77c42964Fe87A5DCca38853F27",
    "0x6FDa8F1087CBA6D0c30fECC907aB869Fe2F45740",
    "0x9814372208dCA62d52DF171de2B8Cc406275A35a",
    "0x368484712e5382282CcE52589bE85780901778F2",
    "0x6A689Ca550CA3c460C7033D10B92963259BdE2D6",
  ];

  constructor() {}

  async initialize() {
    this.factory = await hre.ethers.getContractFactory("NFTCouponFactory");
  }

  async deployFactoryWithContracts(num: number) {
    const progress = Progress.create({
      total: num + 1,
      title: "Deploying NFTCouponFactory with contracts",
    });
    const nftCouponFactory = (await this.factory!.deploy(
      this.initialOwners
    )) as NFTCouponFactory;

    await nftCouponFactory.waitForDeployment();
    progress.update();

    for (let i = 0; i < num; i++) {
      progress.update();
      const nftCoupon = await hre.ethers.getContractFactory("NFTContract");
      const nftCouponContract = await nftCoupon.deploy(this.initialOwners);
      await nftCouponContract.waitForDeployment();

      await nftCouponContract.approve(await nftCouponFactory.getAddress());
      await nftCouponFactory.addContract(nftCouponContract);
    }

    console.log(
      "NFTCouponFactory deployed to:",
      await nftCouponFactory.getAddress()
    );
    console.log(
      "Available contracts:",
      await nftCouponFactory.getAvailableContractsCount()
    );

    return await nftCouponFactory.getAddress();
  }

  async createCoupon(contract: string) {
    const nftCouponFactory = this.factory!.attach(contract) as NFTCouponFactory;
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
    await response.wait();
  }

  async addMoreNft(num: number, contract: string) {
    const nftCouponFactory = this.factory!.attach(contract) as NFTCouponFactory;
    const progress = Progress.create({
      total: num,
      title: "Adding more NFT to NFTCouponFactory",
    });

    for (let i = 0; i < num; i++) {
      progress.update();
      const nftCoupon = await hre.ethers.getContractFactory("NFTContract");
      const nftCouponContract = await nftCoupon.deploy([]);
      await nftCouponContract.waitForDeployment();

      await nftCouponContract.approve(await nftCouponFactory.getAddress());
      await nftCouponFactory.addContract(nftCouponContract);
    }
  }

  async addApprovedUser(contract: string, address: string) {
    const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
    const nftCouponFactory = factory.attach(contract) as NFTContract;

    await nftCouponFactory.approve(address);
    console.log("User added to approved list");
  }
}
