export interface StorageClass<T> {
  /**
   * Initialize the storage class.
   */
  initialize(): Promise<void>;
  /**
   * Set a value in the storage.
   */
  set<D>(key: keyof T, value: D): Promise<void>;
  /**
   * Get a value from the storage.
   */
  get<D>(key: keyof T): Promise<D | null>;
}
