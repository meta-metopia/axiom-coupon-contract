import { expect } from "chai";
import hre from "hardhat";

describe("CouponFactory", () => {
  describe("createCoupon", () => {
    const testCases = [
      {
        tokenSupply: 1,
        contractIndex: 0,
        expectedCouponCode: "0101002010",
      },
      {
        tokenSupply: 10,
        contractIndex: 0,
        expectedCouponCode: "01010020200",
      },
      {
        tokenSupply: 100,
        contractIndex: 0,
        expectedCouponCode: "010100203000",
      },
      {
        tokenSupply: 100,
        contractIndex: 100,
        expectedCouponCode: "01031000203000",
      },
    ];

    testCases.forEach((testCase) =>
      it("should be able to generate correct coupon code", async () => {
        const CouponFactory = await hre.ethers.getContractFactory(
          "CouponFactory"
        );
        const coupon = await CouponFactory.deploy();
        await coupon.waitForDeployment();

        const couponCode = await coupon.generateCouponToken(
          testCase.contractIndex,
          testCase.tokenSupply
        );

        expect(couponCode).to.be.equal(testCase.expectedCouponCode);
      })
    );
  });

  describe("parseCoupon", () => {
    const testCases = [
      {
        couponCode: "0101002011",
        expectedContractIndex: 0,
        expectedTokenId: 1,
      },
      {
        couponCode: "01010020211",
        expectedContractIndex: 0,
        expectedTokenId: 11,
      },
      {
        couponCode: "010100203002",
        expectedContractIndex: 0,
        expectedTokenId: 2,
      },
      {
        couponCode: "01031010203044",
        expectedContractIndex: 101,
        expectedTokenId: 44,
      },
    ];

    testCases.forEach((testCase) =>
      it("should be able to parse correct coupon code", async () => {
        const CouponFactory = await hre.ethers.getContractFactory(
          "CouponFactory"
        );
        const coupon = await CouponFactory.deploy();
        await coupon.waitForDeployment();

        const { contractAddressIndex, tokenIdValue } =
          await coupon.parseCouponToken(testCase.couponCode);

        expect(contractAddressIndex).to.be.equal(
          testCase.expectedContractIndex
        );
        expect(tokenIdValue).to.be.equal(testCase.expectedTokenId);
      })
    );
  });
});
