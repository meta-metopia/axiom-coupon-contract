package org.example.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.abi.datatypes.reflection.Parameterized;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.0.
 */
@SuppressWarnings("rawtypes")
public class Factory extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDCONTRACT = "addContract";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_CREATECOUPON = "createCoupon";

    public static final String FUNC_DISAPPROVE = "disapprove";

    public static final String FUNC_GENERATECOUPONTOKEN = "generateCouponToken";

    public static final String FUNC_GETAVAILABLECONTRACTSCOUNT = "getAvailableContractsCount";

    public static final String FUNC_GETCONTRACTBYINDEX = "getContractByIndex";

    public static final String FUNC_GETCOUPONBYID = "getCouponById";

    public static final String FUNC_GETNFTADDRESSBYCOUPONID = "getNftAddressByCouponId";

    public static final String FUNC_ISCOUPONIDVALID = "isCouponIdValid";

    public static final String FUNC_LISTALLCOUPONS = "listAllCoupons";

    public static final String FUNC_MINTCOUPON = "mintCoupon";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PARSECOUPONTOKEN = "parseCouponToken";

    public static final String FUNC_REDEEMCOUPON = "redeemCoupon";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event COUPONCREATED_EVENT = new Event("CouponCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event COUPONMINTED_EVENT = new Event("CouponMinted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event COUPONREDEEMED_EVENT = new Event("CouponRedeemed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event COUPONTRANSFERRED_EVENT = new Event("CouponTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected Factory(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Factory(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Factory(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Factory(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<CouponCreatedEventResponse> getCouponCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(COUPONCREATED_EVENT, transactionReceipt);
        ArrayList<CouponCreatedEventResponse> responses = new ArrayList<CouponCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CouponCreatedEventResponse typedResponse = new CouponCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.nftIdBegin = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.contractAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CouponCreatedEventResponse getCouponCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COUPONCREATED_EVENT, log);
        CouponCreatedEventResponse typedResponse = new CouponCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.nftIdBegin = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.contractAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<CouponCreatedEventResponse> couponCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCouponCreatedEventFromLog(log));
    }

    public Flowable<CouponCreatedEventResponse> couponCreatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(COUPONCREATED_EVENT));
        return couponCreatedEventFlowable(filter);
    }

    public static List<CouponMintedEventResponse> getCouponMintedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(COUPONMINTED_EVENT, transactionReceipt);
        ArrayList<CouponMintedEventResponse> responses = new ArrayList<CouponMintedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CouponMintedEventResponse typedResponse = new CouponMintedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.couponId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.trigger = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CouponMintedEventResponse getCouponMintedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COUPONMINTED_EVENT, log);
        CouponMintedEventResponse typedResponse = new CouponMintedEventResponse();
        typedResponse.log = log;
        typedResponse.couponId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.trigger = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<CouponMintedEventResponse> couponMintedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCouponMintedEventFromLog(log));
    }

    public Flowable<CouponMintedEventResponse> couponMintedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(COUPONMINTED_EVENT));
        return couponMintedEventFlowable(filter);
    }

    public static List<CouponRedeemedEventResponse> getCouponRedeemedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(COUPONREDEEMED_EVENT, transactionReceipt);
        ArrayList<CouponRedeemedEventResponse> responses = new ArrayList<CouponRedeemedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CouponRedeemedEventResponse typedResponse = new CouponRedeemedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.couponId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.trigger = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CouponRedeemedEventResponse getCouponRedeemedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COUPONREDEEMED_EVENT, log);
        CouponRedeemedEventResponse typedResponse = new CouponRedeemedEventResponse();
        typedResponse.log = log;
        typedResponse.couponId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.trigger = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<CouponRedeemedEventResponse> couponRedeemedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCouponRedeemedEventFromLog(log));
    }

    public Flowable<CouponRedeemedEventResponse> couponRedeemedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(COUPONREDEEMED_EVENT));
        return couponRedeemedEventFlowable(filter);
    }

    public static List<CouponTransferredEventResponse> getCouponTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(COUPONTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<CouponTransferredEventResponse> responses = new ArrayList<CouponTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CouponTransferredEventResponse typedResponse = new CouponTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.couponId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.trigger = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CouponTransferredEventResponse getCouponTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COUPONTRANSFERRED_EVENT, log);
        CouponTransferredEventResponse typedResponse = new CouponTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.couponId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.trigger = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<CouponTransferredEventResponse> couponTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCouponTransferredEventFromLog(log));
    }

    public Flowable<CouponTransferredEventResponse> couponTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(COUPONTRANSFERRED_EVENT));
        return couponTransferredEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addContract(String _contract) {
        final Function function = new Function(
                FUNC_ADDCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contract)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String user) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createCoupon(CreateCouponOpts createCouponOpts) {
        final Function function = new Function(
                FUNC_CREATECOUPON, 
                Arrays.<Type>asList(createCouponOpts), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> disapprove(String user) {
        final Function function = new Function(
                FUNC_DISAPPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> generateCouponToken(BigInteger contractAddressIndex,
            BigInteger tokenIdIndex) {
        final Function function = new Function(FUNC_GENERATECOUPONTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(contractAddressIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenIdIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getAvailableContractsCount() {
        final Function function = new Function(FUNC_GETAVAILABLECONTRACTSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getContractByIndex(BigInteger index) {
        final Function function = new Function(FUNC_GETCONTRACTBYINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<GetCouponByIdResponse> getCouponById(String couponId) {
        final Function function = new Function(FUNC_GETCOUPONBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(couponId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<GetCouponByIdResponse>() {}));
        return executeRemoteCallSingleValueReturn(function, GetCouponByIdResponse.class);
    }

    public RemoteFunctionCall<Tuple2<String, BigInteger>> getNftAddressByCouponId(String couponId) {
        final Function function = new Function(FUNC_GETNFTADDRESSBYCOUPONID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(couponId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<String, BigInteger>>(function,
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isCouponIdValid(String couponId) {
        final Function function = new Function(FUNC_ISCOUPONIDVALID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(couponId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<List> listAllCoupons(ListAllCouponsOpts listAllCouponsOpts) {
        final Function function = new Function(FUNC_LISTALLCOUPONS, 
                Arrays.<Type>asList(listAllCouponsOpts), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<GetCouponByIdResponse>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> mintCoupon(MintCouponOpts transferCouponOptions) {
        final Function function = new Function(
                FUNC_MINTCOUPON, 
                Arrays.<Type>asList(transferCouponOptions), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> parseCouponToken(String couponToken) {
        final Function function = new Function(FUNC_PARSECOUPONTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(couponToken)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> redeemCoupon(RedeemCouponOpts redeemCouponOpts) {
        final Function function = new Function(
                FUNC_REDEEMCOUPON, 
                Arrays.<Type>asList(redeemCouponOpts), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Factory load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Factory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Factory load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Factory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Factory load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Factory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Factory load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Factory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class ApprovedMerchant extends DynamicStruct {
        public String approvedMerchantAddr;

        public String approvedMerchantName;

        public ApprovedMerchant(String approvedMerchantAddr, String approvedMerchantName) {
            super(new org.web3j.abi.datatypes.Address(160, approvedMerchantAddr), 
                    new org.web3j.abi.datatypes.Utf8String(approvedMerchantName));
            this.approvedMerchantAddr = approvedMerchantAddr;
            this.approvedMerchantName = approvedMerchantName;
        }

        public ApprovedMerchant(Address approvedMerchantAddr, Utf8String approvedMerchantName) {
            super(approvedMerchantAddr, approvedMerchantName);
            this.approvedMerchantAddr = approvedMerchantAddr.getValue();
            this.approvedMerchantName = approvedMerchantName.getValue();
        }
    }

    public static class ApprovedPayment extends DynamicStruct {
        public String approvedPaymentAddr;

        public String approvedPaymentName;

        public ApprovedPayment(String approvedPaymentAddr, String approvedPaymentName) {
            super(new org.web3j.abi.datatypes.Address(160, approvedPaymentAddr), 
                    new org.web3j.abi.datatypes.Utf8String(approvedPaymentName));
            this.approvedPaymentAddr = approvedPaymentAddr;
            this.approvedPaymentName = approvedPaymentName;
        }

        public ApprovedPayment(Address approvedPaymentAddr, Utf8String approvedPaymentName) {
            super(approvedPaymentAddr, approvedPaymentName);
            this.approvedPaymentAddr = approvedPaymentAddr.getValue();
            this.approvedPaymentName = approvedPaymentName.getValue();
        }
    }

    public static class Rule extends StaticStruct {
        public BigInteger value;

        public BigInteger claimLimit;

        public Boolean isTransfer;

        public Rule(BigInteger value, BigInteger claimLimit, Boolean isTransfer) {
            super(new org.web3j.abi.datatypes.generated.Uint256(value), 
                    new org.web3j.abi.datatypes.generated.Uint256(claimLimit), 
                    new org.web3j.abi.datatypes.Bool(isTransfer));
            this.value = value;
            this.claimLimit = claimLimit;
            this.isTransfer = isTransfer;
        }

        public Rule(Uint256 value, Uint256 claimLimit, Bool isTransfer) {
            super(value, claimLimit, isTransfer);
            this.value = value.getValue();
            this.claimLimit = claimLimit.getValue();
            this.isTransfer = isTransfer.getValue();
        }
    }

    public static class CreateCouponResponse extends DynamicStruct {
        public String nftIdBegin;

        public CreateCouponResponse(String nftIdBegin) {
            super(new org.web3j.abi.datatypes.Utf8String(nftIdBegin));
            this.nftIdBegin = nftIdBegin;
        }

        public CreateCouponResponse(Utf8String nftIdBegin) {
            super(nftIdBegin);
            this.nftIdBegin = nftIdBegin.getValue();
        }
    }

    public static class ListAllCouponsOpts extends StaticStruct {
        public String userAddress;

        public BigInteger page;

        public BigInteger pageSize;

        public ListAllCouponsOpts(String userAddress, BigInteger page, BigInteger pageSize) {
            super(new org.web3j.abi.datatypes.Address(160, userAddress), 
                    new org.web3j.abi.datatypes.generated.Uint256(page), 
                    new org.web3j.abi.datatypes.generated.Uint256(pageSize));
            this.userAddress = userAddress;
            this.page = page;
            this.pageSize = pageSize;
        }

        public ListAllCouponsOpts(Address userAddress, Uint256 page, Uint256 pageSize) {
            super(userAddress, page, pageSize);
            this.userAddress = userAddress.getValue();
            this.page = page.getValue();
            this.pageSize = pageSize.getValue();
        }
    }

    public static class MintCouponOpts extends DynamicStruct {
        public String couponId;

        public String receiverAddr;

        public MintCouponOpts(String couponId, String receiverAddr) {
            super(new org.web3j.abi.datatypes.Utf8String(couponId), 
                    new org.web3j.abi.datatypes.Address(160, receiverAddr));
            this.couponId = couponId;
            this.receiverAddr = receiverAddr;
        }

        public MintCouponOpts(Utf8String couponId, Address receiverAddr) {
            super(couponId, receiverAddr);
            this.couponId = couponId.getValue();
            this.receiverAddr = receiverAddr.getValue();
        }
    }

    public static class RedeemCouponOpts extends DynamicStruct {
        public String couponId;

        public String paymentAddress;

        public byte[] paymentSignature;

        public String paymentMessage;

        public BigInteger paymentNonce;

        public String userAddress;

        public byte[] userSignature;

        public String userMessage;

        public BigInteger userNonce;

        public BigInteger approveTime;

        public BigInteger approveDuration;

        public RedeemCouponOpts(String couponId, String paymentAddress, byte[] paymentSignature,
                String paymentMessage, BigInteger paymentNonce, String userAddress,
                byte[] userSignature, String userMessage, BigInteger userNonce,
                BigInteger approveTime, BigInteger approveDuration) {
            super(new org.web3j.abi.datatypes.Utf8String(couponId), 
                    new org.web3j.abi.datatypes.Address(160, paymentAddress), 
                    new org.web3j.abi.datatypes.DynamicBytes(paymentSignature), 
                    new org.web3j.abi.datatypes.Utf8String(paymentMessage), 
                    new org.web3j.abi.datatypes.generated.Uint256(paymentNonce), 
                    new org.web3j.abi.datatypes.Address(160, userAddress), 
                    new org.web3j.abi.datatypes.DynamicBytes(userSignature), 
                    new org.web3j.abi.datatypes.Utf8String(userMessage), 
                    new org.web3j.abi.datatypes.generated.Uint256(userNonce), 
                    new org.web3j.abi.datatypes.generated.Uint256(approveTime), 
                    new org.web3j.abi.datatypes.generated.Uint256(approveDuration));
            this.couponId = couponId;
            this.paymentAddress = paymentAddress;
            this.paymentSignature = paymentSignature;
            this.paymentMessage = paymentMessage;
            this.paymentNonce = paymentNonce;
            this.userAddress = userAddress;
            this.userSignature = userSignature;
            this.userMessage = userMessage;
            this.userNonce = userNonce;
            this.approveTime = approveTime;
            this.approveDuration = approveDuration;
        }

        public RedeemCouponOpts(Utf8String couponId, Address paymentAddress,
                DynamicBytes paymentSignature, Utf8String paymentMessage, Uint256 paymentNonce,
                Address userAddress, DynamicBytes userSignature, Utf8String userMessage,
                Uint256 userNonce, Uint256 approveTime, Uint256 approveDuration) {
            super(couponId, paymentAddress, paymentSignature, paymentMessage, paymentNonce, userAddress, userSignature, userMessage, userNonce, approveTime, approveDuration);
            this.couponId = couponId.getValue();
            this.paymentAddress = paymentAddress.getValue();
            this.paymentSignature = paymentSignature.getValue();
            this.paymentMessage = paymentMessage.getValue();
            this.paymentNonce = paymentNonce.getValue();
            this.userAddress = userAddress.getValue();
            this.userSignature = userSignature.getValue();
            this.userMessage = userMessage.getValue();
            this.userNonce = userNonce.getValue();
            this.approveTime = approveTime.getValue();
            this.approveDuration = approveDuration.getValue();
        }
    }

    public static class Metadata extends DynamicStruct {
        public List<ApprovedMerchant> approvedMerchant;

        public List<ApprovedPayment> approvedPayment;

        public String couponType;

        public String couponSubtitle;

        public String couponDetails;

        public String url;

        public BigInteger expirationTime;

        public BigInteger expirationStartTime;

        public Rule rule;

        public BigInteger reedemState;

        public BigInteger approveTime;

        public BigInteger approveDuration;

        public Metadata(List<ApprovedMerchant> approvedMerchant,
                List<ApprovedPayment> approvedPayment, String couponType, String couponSubtitle,
                String couponDetails, String url, BigInteger expirationTime,
                BigInteger expirationStartTime, Rule rule, BigInteger reedemState,
                BigInteger approveTime, BigInteger approveDuration) {
            super(new org.web3j.abi.datatypes.DynamicArray<ApprovedMerchant>(ApprovedMerchant.class, approvedMerchant), 
                    new org.web3j.abi.datatypes.DynamicArray<ApprovedPayment>(ApprovedPayment.class, approvedPayment), 
                    new org.web3j.abi.datatypes.Utf8String(couponType), 
                    new org.web3j.abi.datatypes.Utf8String(couponSubtitle), 
                    new org.web3j.abi.datatypes.Utf8String(couponDetails), 
                    new org.web3j.abi.datatypes.Utf8String(url), 
                    new org.web3j.abi.datatypes.generated.Uint256(expirationTime), 
                    new org.web3j.abi.datatypes.generated.Uint256(expirationStartTime), 
                    rule, 
                    new org.web3j.abi.datatypes.generated.Uint8(reedemState), 
                    new org.web3j.abi.datatypes.generated.Uint256(approveTime), 
                    new org.web3j.abi.datatypes.generated.Uint256(approveDuration));
            this.approvedMerchant = approvedMerchant;
            this.approvedPayment = approvedPayment;
            this.couponType = couponType;
            this.couponSubtitle = couponSubtitle;
            this.couponDetails = couponDetails;
            this.url = url;
            this.expirationTime = expirationTime;
            this.expirationStartTime = expirationStartTime;
            this.rule = rule;
            this.reedemState = reedemState;
            this.approveTime = approveTime;
            this.approveDuration = approveDuration;
        }

        public Metadata(
                @Parameterized(type = ApprovedMerchant.class) DynamicArray<ApprovedMerchant> approvedMerchant,
                @Parameterized(type = ApprovedPayment.class) DynamicArray<ApprovedPayment> approvedPayment,
                Utf8String couponType, Utf8String couponSubtitle, Utf8String couponDetails,
                Utf8String url, Uint256 expirationTime, Uint256 expirationStartTime, Rule rule,
                Uint8 reedemState, Uint256 approveTime, Uint256 approveDuration) {
            super(approvedMerchant, approvedPayment, couponType, couponSubtitle, couponDetails, url, expirationTime, expirationStartTime, rule, reedemState, approveTime, approveDuration);
            this.approvedMerchant = approvedMerchant.getValue();
            this.approvedPayment = approvedPayment.getValue();
            this.couponType = couponType.getValue();
            this.couponSubtitle = couponSubtitle.getValue();
            this.couponDetails = couponDetails.getValue();
            this.url = url.getValue();
            this.expirationTime = expirationTime.getValue();
            this.expirationStartTime = expirationStartTime.getValue();
            this.rule = rule;
            this.reedemState = reedemState.getValue();
            this.approveTime = approveTime.getValue();
            this.approveDuration = approveDuration.getValue();
        }
    }

    public static class CreateCouponOpts extends DynamicStruct {
        public String creatorAddress;

        public String author;

        public BigInteger supply;

        public String name;

        public String desc;

        public BigInteger fieldId;

        public BigInteger price;

        public String currency;

        public Metadata metadata;

        public CreateCouponOpts(String creatorAddress, String author, BigInteger supply,
                String name, String desc, BigInteger fieldId, BigInteger price, String currency,
                Metadata metadata) {
            super(new org.web3j.abi.datatypes.Address(160, creatorAddress), 
                    new org.web3j.abi.datatypes.Utf8String(author), 
                    new org.web3j.abi.datatypes.generated.Uint256(supply), 
                    new org.web3j.abi.datatypes.Utf8String(name), 
                    new org.web3j.abi.datatypes.Utf8String(desc), 
                    new org.web3j.abi.datatypes.generated.Uint256(fieldId), 
                    new org.web3j.abi.datatypes.generated.Uint256(price), 
                    new org.web3j.abi.datatypes.Utf8String(currency), 
                    metadata);
            this.creatorAddress = creatorAddress;
            this.author = author;
            this.supply = supply;
            this.name = name;
            this.desc = desc;
            this.fieldId = fieldId;
            this.price = price;
            this.currency = currency;
            this.metadata = metadata;
        }

        public CreateCouponOpts(Address creatorAddress, Utf8String author, Uint256 supply,
                Utf8String name, Utf8String desc, Uint256 fieldId, Uint256 price,
                Utf8String currency, Metadata metadata) {
            super(creatorAddress, author, supply, name, desc, fieldId, price, currency, metadata);
            this.creatorAddress = creatorAddress.getValue();
            this.author = author.getValue();
            this.supply = supply.getValue();
            this.name = name.getValue();
            this.desc = desc.getValue();
            this.fieldId = fieldId.getValue();
            this.price = price.getValue();
            this.currency = currency.getValue();
            this.metadata = metadata;
        }
    }

    public static class GetCouponByIdResponse extends DynamicStruct {
        public String couponId;

        public String creatorAddress;

        public String author;

        public BigInteger mintTime;

        public String ownerAddr;

        public BigInteger ownerTime;

        public BigInteger supply;

        public String name;

        public String desc;

        public BigInteger fieldId;

        public String currency;

        public Metadata metadata;

        public GetCouponByIdResponse(String couponId, String creatorAddress, String author,
                BigInteger mintTime, String ownerAddr, BigInteger ownerTime, BigInteger supply,
                String name, String desc, BigInteger fieldId, String currency, Metadata metadata) {
            super(new org.web3j.abi.datatypes.Utf8String(couponId), 
                    new org.web3j.abi.datatypes.Address(160, creatorAddress), 
                    new org.web3j.abi.datatypes.Utf8String(author), 
                    new org.web3j.abi.datatypes.generated.Uint256(mintTime), 
                    new org.web3j.abi.datatypes.Address(160, ownerAddr), 
                    new org.web3j.abi.datatypes.generated.Uint256(ownerTime), 
                    new org.web3j.abi.datatypes.generated.Uint256(supply), 
                    new org.web3j.abi.datatypes.Utf8String(name), 
                    new org.web3j.abi.datatypes.Utf8String(desc), 
                    new org.web3j.abi.datatypes.generated.Uint256(fieldId), 
                    new org.web3j.abi.datatypes.Utf8String(currency), 
                    metadata);
            this.couponId = couponId;
            this.creatorAddress = creatorAddress;
            this.author = author;
            this.mintTime = mintTime;
            this.ownerAddr = ownerAddr;
            this.ownerTime = ownerTime;
            this.supply = supply;
            this.name = name;
            this.desc = desc;
            this.fieldId = fieldId;
            this.currency = currency;
            this.metadata = metadata;
        }

        public GetCouponByIdResponse(Utf8String couponId, Address creatorAddress, Utf8String author,
                Uint256 mintTime, Address ownerAddr, Uint256 ownerTime, Uint256 supply,
                Utf8String name, Utf8String desc, Uint256 fieldId, Utf8String currency,
                Metadata metadata) {
            super(couponId, creatorAddress, author, mintTime, ownerAddr, ownerTime, supply, name, desc, fieldId, currency, metadata);
            this.couponId = couponId.getValue();
            this.creatorAddress = creatorAddress.getValue();
            this.author = author.getValue();
            this.mintTime = mintTime.getValue();
            this.ownerAddr = ownerAddr.getValue();
            this.ownerTime = ownerTime.getValue();
            this.supply = supply.getValue();
            this.name = name.getValue();
            this.desc = desc.getValue();
            this.fieldId = fieldId.getValue();
            this.currency = currency.getValue();
            this.metadata = metadata;
        }
    }

    public static class CouponCreatedEventResponse extends BaseEventResponse {
        public String nftIdBegin;

        public String contractAddress;
    }

    public static class CouponMintedEventResponse extends BaseEventResponse {
        public String couponId;

        public String trigger;

        public String to;
    }

    public static class CouponRedeemedEventResponse extends BaseEventResponse {
        public String couponId;

        public String trigger;
    }

    public static class CouponTransferredEventResponse extends BaseEventResponse {
        public String couponId;

        public String trigger;

        public String to;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
