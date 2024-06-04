import { ethers } from "hardhat";
import { Contract } from "./contract/contract";
import { ContractConfig, Owner } from "./contract/contract.config.interface";
import { StorageClass } from "./storage/storage.interface";
import { consola } from "consola";

/**
 * Command line interface for interacting with the contract
 */
export class CommandLine {
  constructor(
    private readonly contract: Contract,
    private readonly storage: StorageClass<ContractConfig>
  ) {}

  async initialize() {
    await this.contract.initialize();
    await this.storage.initialize();
  }

  async run() {
    while (true) {
      try {
        const command = await consola.prompt("Enter a command", {
          type: "select",
          options: [
            "set-tag",
            "set-owner",
            "deploy-contract",
            "add-user",
            "config-contract-address",
            "exit",
          ],
        });

        switch (command) {
          case "set-tag":
            await this.setTag();
            break;
          case "set-owner":
            await this.setOwner();
            break;
          case "deploy-contract":
            await this.deployContract();
            break;
          case "config-contract-address":
            await this.configContractAddress();
            break;
          case "add-user":
            await this.approveNewUser();
            break;
          case "exit":
            return;
          default:
            return process.exit(0);
        }
      } catch (error) {
        consola.error("Error running command", error);
        process.exit(1);
      }
    }
  }

  async setTag() {
    const tag = await consola.prompt("Enter a tag for the owner");
    const previousTag = (await this.storage.get<string[]>("tags")) ?? [];
    await this.storage.set("tags", [...previousTag, tag]);
  }

  async configContractAddress() {
    const contractAddress = await consola.prompt("Enter contract address");
    await this.storage.set("contractAddress", contractAddress);
  }

  async setOwner() {
    const previousTags = (await this.storage.get<string[]>("tags")) ?? [];
    if (previousTags.length === 0) {
      consola.error("No tags found. Please add a tag first.");
      return;
    }
    const tag = await consola.prompt("Select a tag for the owner", {
      type: "select",
      options: previousTags,
    });
    const ownerWalletAddress = await consola.prompt(
      "Enter owner's wallet address"
    );

    const newOwner: Owner = {
      name: tag,
      address: ownerWalletAddress as string,
    };
    if (!newOwner.address) {
      consola.error("Invalid wallet address");
      return;
    }

    if (!ethers.isAddress(newOwner.address)) {
      consola.error("Invalid wallet address");
      return;
    }

    const previousOwners = (await this.storage.get<Owner[]>("owners")) ?? [];

    const existingOwner = previousOwners.find(
      (owner) => owner.address === newOwner.address
    );
    if (existingOwner) {
      consola.error("Owner already exists");
      return;
    }

    await this.storage.set<Owner[]>("owners", [...previousOwners, newOwner]);
  }

  async deployContract() {
    const numberOfInitialContracts = parseInt(
      (await consola.prompt("Enter the number of initial contracts")) as string,
      10
    );
    const initalOwners = (await this.storage.get<Owner[]>("owners")) ?? [];
    const confirmed = await consola.prompt(
      `Do you want to deploy the contract with ${initalOwners.length} owners and ${numberOfInitialContracts} contracts?`,
      {
        type: "confirm",
        initial: false,
      }
    );
    if (!confirmed) {
      return;
    }
    const contractAddress = await this.contract.deployFactoryWithContracts(
      numberOfInitialContracts,
      initalOwners.map((owner) => owner.address)
    );
    await this.storage.set("contractAddress", contractAddress);
  }

  async approveNewUser() {
    const tags = (await this.storage.get<string[]>("tags")) ?? [];
    if (tags.length === 0) {
      consola.error("No tags found. Please add a tag first.");
      return;
    }
    const contractAddress = await this.storage.get<string>("contractAddress");
    if (!contractAddress) {
      consola.error("No contract found. Please deploy a contract first.");
      return;
    }

    const tag = await consola.prompt("Select a tag for the owner", {
      type: "select",
      options: tags,
    });
    const ownerWalletAddress = await consola.prompt(
      "Enter owner's wallet address"
    );

    const newOwner: Owner = {
      name: tag,
      address: ownerWalletAddress as string,
    };
    if (!newOwner.address) {
      consola.error("Invalid wallet address");
      return;
    }

    if (!ethers.isAddress(newOwner.address)) {
      consola.error("Invalid wallet address");
      return;
    }

    const previousOwners = (await this.storage.get<Owner[]>("owners")) ?? [];

    const existingOwner = previousOwners.find(
      (owner) => owner.address === newOwner.address
    );
    if (existingOwner) {
      consola.error("Owner already exists");
      return;
    }

    await this.contract.addApprovedUser(contractAddress, newOwner.address);
    await this.storage.set<Owner[]>("owners", [...previousOwners, newOwner]);
  }

  async createCoupon() {
    const contractAddress = await this.storage.get<string>("contractAddress");
    if (!contractAddress) {
      consola.error("No contract found. Please deploy a contract first.");
      return;
    }

    await this.contract.createCoupon(contractAddress);
  }
}
