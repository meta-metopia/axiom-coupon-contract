import hre from "hardhat";

async function deployFactoryWithContracts(num: number) {
  const factory = await hre.ethers.getContractFactory("NFTCouponFactory");
  const initialOwners = [
    //eft
    "0xfF6377b2a70F75b9Fd5A454a14B857049E31Dc1a",
    //mylink
    "0x4275297E78d1Bf77c42964Fe87A5DCca38853F27",
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

(async () => {
  const numberOfPredefinedContracts = 10;
  await deployFactoryWithContracts(numberOfPredefinedContracts);
})();
