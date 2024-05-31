import json
import os

print("Generating ABI files...")
print(f"Current directory: {os.getcwd()}")

with open("./artifacts/contracts/factory/Factory.sol/NFTCouponFactory.json") as nft_abi:
    nft_abi = json.load(nft_abi)
    # create folder if not exists
    if not os.path.exists("./artifacts/abi"):
        os.makedirs("./artifacts/abi")

    # create file
    with open("./artifacts/abi/factory.abi.json", "w") as output_nft_abi:
        output_nft_abi.write(json.dumps(nft_abi["abi"], indent=4))


print("ABI files generated successfully!")