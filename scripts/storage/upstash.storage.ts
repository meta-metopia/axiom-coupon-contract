import { Signer } from "ethers";
import { ContractConfig } from "../contract/contract.config.interface";
import { StorageClass } from "./storage.interface";
import { ethers } from "hardhat";
import { Redis } from "@upstash/redis";

export class UpstashStorage implements StorageClass<ContractConfig> {
  private signer?: Signer;
  private keyPrefix?: string;
  private redis?: Redis;

  async initialize(): Promise<void> {
    const [owner] = await ethers.getSigners();
    this.signer = owner;
    this.keyPrefix = `contract:${await owner.getAddress()}:`;
    this.redis = new Redis({
      url: process.env.UPSTASH_REDIS_REST_URL,
      token: process.env.UPSTASH_REDIS_REST_TOKEN,
    });
  }

  async set<D>(key: keyof ContractConfig, value: D): Promise<void> {
    let setValue: any;
    if (typeof value === "object") {
      setValue = JSON.stringify(value);
    } else {
      setValue = value;
    }

    await this.redis?.set(`${this.keyPrefix}${key.toString()}`, setValue);
  }

  async get<D>(key: keyof ContractConfig): Promise<D | null> {
    return (await this.redis?.get<D>(
      `${this.keyPrefix}${key.toString()}`
    )) as any;
  }
}
