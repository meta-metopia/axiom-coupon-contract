import { buildModule } from "@nomicfoundation/hardhat-ignition/modules";

const LockModule = buildModule("FactoryModule", (m) => {
  const factory = m.contract("NFTCouponFactory", [[]]);

  return {
    factory,
  };
});

export default LockModule;
