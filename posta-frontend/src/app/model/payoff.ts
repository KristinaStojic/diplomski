import { Client } from './client';
export class Payoff {
    id: String
    client: Client
    amount: number
    paidOff: Boolean
    payoffType: String
    clientAddress: String
}
