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
    personalDelivery: Boolean = false
    SMSreport: Boolean = false
    returnReceipt: Boolean = false
    smsNumber: String
    shipmentType: String
    letterType: String
}
