export interface StorageClass<T> {
  /**
   * Initialize the storage class.
   */
  initialize(): Promise<void>;
  /**
   * Set a value in the storage.
   */
  set<D>(key: string, value: D): Promise<void>;
  /**
   * Get a value from the storage.
   */
  get<D>(key: string): Promise<D | null>;
}
