import { HardhatUserConfig } from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";
import "solidity-coverage";
import dotenv from "dotenv";

dotenv.config();

const { PRIVATE_KEY } = process.env;

const config: HardhatUserConfig = {
  solidity: {
    version: "0.8.20",
    settings: {
      optimizer: {
        enabled: true,
        runs: 10000,
      },
    },
  },
  networks: {
    gemini: {
      url: "https://rpc5.gemini.axiomesh.io",
      accounts: PRIVATE_KEY ? [process.env.PRIVATE_KEY!] : [],
    },
  },
};

export default config;
