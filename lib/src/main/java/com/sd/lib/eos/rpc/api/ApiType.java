package com.sd.lib.eos.rpc.api;

public enum ApiType
{
    GetInfo,
    GetBlock,
    GetAccount,
    GetCurrencyBalance,
    GetActions,
    GetTransaction,
    GetKeyAccounts,
    GetTableRows,
    GetCode,

    AbiJsonToBin,
    AbiBinToJson,
    PushTransaction,
}
