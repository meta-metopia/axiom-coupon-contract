import { CommandLine } from "./cmd";
import { Contract } from "./contract/contract";
import { LocalStorage } from "./storage/local.storage";
import { UpstashStorage } from "./storage/upstash.storage";

(async () => {
  const localStorage = new LocalStorage("storage_data.json");
  const upstashStorage = new UpstashStorage();
  const contract = new Contract();

  const cmd = new CommandLine(contract, upstashStorage);
  await cmd.initialize();
  await cmd.run();
})();
