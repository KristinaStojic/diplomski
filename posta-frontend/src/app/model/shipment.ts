import { Address } from './address';
import { Client } from './client';
export class Shipment {
    id: String
    receiver: Client
    receiverAddress: Address
    client: Client
    clientAddress: Address
    date: String
    counterWorker: String
    weight: number
    value: number
    personalDelivery: Boolean
    SMSreport: Boolean
    returnReceipt: Boolean
}
