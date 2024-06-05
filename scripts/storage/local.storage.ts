import { StorageClass } from "./storage.interface";
import fs from "fs";

/**
 * Local storage class implements a storage class
 * that will store the data locally in the user file system.
 */
export class LocalStorage<T> implements StorageClass<T> {
  constructor(private readonly filename: string) {}
  private data: Record<keyof T, any> = {} as any;

  async initialize(): Promise<void> {
    // read the file and load the data
    if (fs.existsSync(this.filename)) {
      const data = fs.readFileSync(this.filename, "utf-8");
      this.data = JSON.parse(data);
    } else {
      // create the file
      fs.writeFileSync(this.filename, "{}");
    }
  }

  async set<D>(key: keyof T, value: D) {
    this.data[key] = value;
    fs.writeFileSync(this.filename, JSON.stringify(this.data, null, 4));
  }

  async get<D>(key: keyof T): Promise<D | null> {
    return this.data[key] ?? null;
  }
}
