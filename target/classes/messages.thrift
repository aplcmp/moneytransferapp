namespace java com.thecompany.moneytransfer.messages

struct CurrencyAmount {
  1: i64 value
  2: i16 pennies
}

struct CurrencyTransferRequest {
  1: string sourceAccountId
  2: string targetAccountId
  3: string targetUserName
  4: CurrencyAmount amount
  5: string transferReason
}

enum CurrencyTransferResult {
  OK = 0,
  FAIL = 1
}

struct CurrencyTransferResponse {
  1: CurrencyTransferResult status
  2: optional string description
  3: CurrencyAmount sourceCharged
  4: CurrencyAmount targetDeposit
}

exception IllegalOperation {
  1: string reason
}