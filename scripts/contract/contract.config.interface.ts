export interface Owner {
  name: string;
  address: string;
}

export interface ContractConfig {
  owners: Owner[];
  contractAddress?: string;
  // stored tags for owner names
  tags: string[];
}
