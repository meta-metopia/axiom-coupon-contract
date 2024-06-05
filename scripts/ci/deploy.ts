import { Contract } from "../contract/contract";
import { Owner } from "../contract/contract.config.interface";
import { UpstashStorage } from "../storage/upstash.storage";

(async () => {
  const upstashStorage = new UpstashStorage();
  const contract = new Contract();

  await upstashStorage.initialize();
  await contract.initialize();

  const initialOwners = (await upstashStorage.get("owners")) as Owner[];
  if (!initialOwners) {
    console.log("No owners found, initializing contract with default owner");
    process.exit(1);
  }

  const address = await contract.deployFactoryWithContracts(
    20,
    initialOwners.map((owner) => owner.address)
  );
  await upstashStorage.set("contractAddress", address);
})();
