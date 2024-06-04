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
public class Nft extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BALANCEOFBATCH = "balanceOfBatch";

    public static final String FUNC_DISAPPROVE = "disapprove";

    public static final String FUNC_GETBYID = "getById";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_LISTBYOWNER = "listByOwner";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RECOVERSIGNER = "recoverSigner";

    public static final String FUNC_REDEEM = "redeem";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SAFEBATCHTRANSFERFROM = "safeBatchTransferFrom";

    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_URI = "uri";

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TRANSFERBATCH_EVENT = new Event("TransferBatch", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    public static final Event TRANSFERSINGLE_EVENT = new Event("TransferSingle", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event URI_EVENT = new Event("URI", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected Nft(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Nft(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Nft(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Nft(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalForAllEventResponse> getApprovalForAllEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalForAllEventResponse getApprovalForAllEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log);
        ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
        typedResponse.log = log;
        typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalForAllEventFromLog(log));
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
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

    public static List<TransferBatchEventResponse> getTransferBatchEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFERBATCH_EVENT, transactionReceipt);
        ArrayList<TransferBatchEventResponse> responses = new ArrayList<TransferBatchEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferBatchEventResponse typedResponse = new TransferBatchEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.ids = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
            typedResponse.values = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferBatchEventResponse getTransferBatchEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFERBATCH_EVENT, log);
        TransferBatchEventResponse typedResponse = new TransferBatchEventResponse();
        typedResponse.log = log;
        typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.ids = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
        typedResponse.values = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
        return typedResponse;
    }

    public Flowable<TransferBatchEventResponse> transferBatchEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferBatchEventFromLog(log));
    }

    public Flowable<TransferBatchEventResponse> transferBatchEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERBATCH_EVENT));
        return transferBatchEventFlowable(filter);
    }

    public static List<TransferSingleEventResponse> getTransferSingleEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFERSINGLE_EVENT, transactionReceipt);
        ArrayList<TransferSingleEventResponse> responses = new ArrayList<TransferSingleEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferSingleEventResponse typedResponse = new TransferSingleEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferSingleEventResponse getTransferSingleEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFERSINGLE_EVENT, log);
        TransferSingleEventResponse typedResponse = new TransferSingleEventResponse();
        typedResponse.log = log;
        typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<TransferSingleEventResponse> transferSingleEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferSingleEventFromLog(log));
    }

    public Flowable<TransferSingleEventResponse> transferSingleEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERSINGLE_EVENT));
        return transferSingleEventFlowable(filter);
    }

    public static List<URIEventResponse> getURIEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(URI_EVENT, transactionReceipt);
        ArrayList<URIEventResponse> responses = new ArrayList<URIEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            URIEventResponse typedResponse = new URIEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static URIEventResponse getURIEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(URI_EVENT, log);
        URIEventResponse typedResponse = new URIEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<URIEventResponse> uRIEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getURIEventFromLog(log));
    }

    public Flowable<URIEventResponse> uRIEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(URI_EVENT));
        return uRIEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String user) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account, BigInteger id) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> balanceOfBatch(List<String> accounts, List<BigInteger> ids) {
        final Function function = new Function(FUNC_BALANCEOFBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(accounts, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> disapprove(String user) {
        final Function function = new Function(
                FUNC_DISAPPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<GetTokenByIdResponse> getById(BigInteger id) {
        final Function function = new Function(FUNC_GETBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<GetTokenByIdResponse>() {}));
        return executeRemoteCallSingleValueReturn(function, GetTokenByIdResponse.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(CreateCouponOpts _createCouponOpts,
            List<String> _additionalOwners) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(_createCouponOpts, 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_additionalOwners, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String account, String operator) {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.Address(160, operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<List> listByOwner(String owner) {
        final Function function = new Function(FUNC_LISTBYOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<GetTokenByIdResponse>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> mint(String to, BigInteger id) {
        final Function function = new Function(
                FUNC_MINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> recoverSigner(byte[] _signature, String _message) {
        final Function function = new Function(FUNC_RECOVERSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_signature), 
                new org.web3j.abi.datatypes.Utf8String(_message)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> redeem(BigInteger id,
            RedeemCouponOpts _redeemCouponOpts) {
        final Function function = new Function(
                FUNC_REDEEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id), 
                _redeemCouponOpts), 
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

    public RemoteFunctionCall<TransactionReceipt> safeBatchTransferFrom(String from, String to,
            List<BigInteger> ids, List<BigInteger> values, byte[] data) {
        final Function function = new Function(
                FUNC_SAFEBATCHTRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to,
            BigInteger id, BigInteger value, byte[] data) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.generated.Uint256(value), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator,
            Boolean approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Bool(approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger id) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(id)), 
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

    public RemoteFunctionCall<String> uri(BigInteger param0) {
        final Function function = new Function(FUNC_URI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static Nft load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Nft(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Nft load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Nft(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Nft load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Nft(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Nft load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Nft(contractAddress, web3j, transactionManager, contractGasProvider);
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

    public static class GetTokenByIdResponse extends DynamicStruct {
        public BigInteger couponId;

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

        public GetTokenByIdResponse(BigInteger couponId, String creatorAddress, String author,
                BigInteger mintTime, String ownerAddr, BigInteger ownerTime, BigInteger supply,
                String name, String desc, BigInteger fieldId, String currency, Metadata metadata) {
            super(new org.web3j.abi.datatypes.generated.Uint256(couponId), 
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

        public GetTokenByIdResponse(Uint256 couponId, Address creatorAddress, Utf8String author,
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

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String account;

        public String operator;

        public Boolean approved;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TransferBatchEventResponse extends BaseEventResponse {
        public String operator;

        public String from;

        public String to;

        public List<BigInteger> ids;

        public List<BigInteger> values;
    }

    public static class TransferSingleEventResponse extends BaseEventResponse {
        public String operator;

        public String from;

        public String to;

        public BigInteger id;

        public BigInteger value;
    }

    public static class URIEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String value;
    }
}
