import { CommandLine } from "./cmd";
import { Contract } from "./contract/contract";
import { LocalStorage } from "./storage/local.storage";

(async () => {
  const localStorage = new LocalStorage("storage_data.json");
  const contract = new Contract();

  const cmd = new CommandLine(contract, localStorage);
  await cmd.initialize();
  await cmd.run();
})();
