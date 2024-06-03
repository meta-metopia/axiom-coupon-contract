import hre from "hardhat";
import { NFTContract } from "../typechain-types";
import { NFTCouponFactory } from "../typechain-types/factory/Factory.sol";

async function deployFactoryWithContracts(num: number) {
  const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
  const initialOwners = [
    //eft
    "0xfF6377b2a70F75b9Fd5A454a14B857049E31Dc1a",
    //mylink
    "0x4275297E78d1Bf77c42964Fe87A5DCca38853F27",
    "0x6FDa8F1087CBA6D0c30fECC907aB869Fe2F45740",
    "0x9814372208dCA62d52DF171de2B8Cc406275A35a",
    "0x368484712e5382282CcE52589bE85780901778F2",
  ];

  const nftCouponFactory = await factory.deploy(initialOwners);

  await nftCouponFactory.waitForDeployment();

  for (let i = 0; i < num; i++) {
    console.log("Deploying NFTContract", i);
    const nftCoupon = await hre.ethers.getContractFactory("NFTContract");
    const nftCouponContract = await nftCoupon.deploy(initialOwners);
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
async function addApprovedUser(contract: string, address: string) {
  const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
  const nftCouponFactory = factory.attach(contract) as NFTContract;

  await nftCouponFactory.approve(address);
  console.log("User added to approved list");
}

async function createCoupon(contract: string) {
  const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
  const nftCouponFactory = factory.attach(contract) as NFTCouponFactory;
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
      reedemState: 0,
      approveTime: 0,
      approveDuration: 0,
    },
  };
  const response = await nftCouponFactory.createCoupon(opts);
  const result = await response.wait();
  console.log(result?.logs);
}

(async () => {
  const numberOfPredefinedContracts = 10;
  await deployFactoryWithContracts(numberOfPredefinedContracts);

  // createCoupon("0x3Ba7c929F2B17CF096798A8d78E36667B2967816");
})();
