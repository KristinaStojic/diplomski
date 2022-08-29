import { Address } from './address';
import { Client } from './client';
export class Payoff {
    id: String
    client: Client
    address: Address
    amount: String
    paidOff: Boolean
    payoffType: String
    clientAddress: String
    amountCurrency: String
    currency: String = ""
    counterWorker: String
}
