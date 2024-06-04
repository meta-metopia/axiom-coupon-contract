# 使用Demo

## Introduction

电脑上使用的`java`环境是java 22。在生成abi的java文件时候需要至少达到`java 17`或者更高的版本。

## Getting Started
1. 安装依赖
```
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // required web3j dependency
    implementation ("org.web3j:core:4.12.0")
}
```


2. 安装Web3j
```
curl -L get.web3j.io | sh && source ~/.web3j/source.sh
```

3. 生成Solidity的java wrapper
```shell
web3j generate solidity -a=./abi.json -o=./src/main/java -p=org.example.contract
```

## Usage

查看src 文件下的代码来看如何使用私钥签名以及如何调用合约方法。


## 添加环境变量

在目录下添加`.env`文件，添加以下内容：

```shell
RECEIVER_PRIVATE_KEY=
RPC_URL=
CONTRACT_ADDRESS=
PRIVATE_KEY=
```

之后运行`src/main/java/org/example/Main.kt`文件即可。