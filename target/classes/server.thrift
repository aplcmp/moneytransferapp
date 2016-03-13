include "messages.thrift"

namespace java com.thecompany.moneytransfer

service MoneyTransfer {
    messages.CurrencyTransferResponse transfer(1: messages.CurrencyTransferRequest request)
    messages.CurrencyAmount getBalance(1: string accountId) throws (1: messages.IllegalOperation exc),
}