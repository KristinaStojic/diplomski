import { Address } from './address';
import { Client } from './client';
export class Payment {
    id: String
    receiver: Client
    receiverAddress: Address
    receiverAddressFull: String
    clientName: String
    clientAddress: Address
    purpose: String
    paymentCode: String
    receiverAccount: String
    model: String
    referenceNumber: String
    counterWorker: String
    client: Client
    clientAddressFull: String
    amount: number
    currency: String
    date: String
    receivingPlace: String
}
