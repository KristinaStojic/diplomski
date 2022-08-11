import { SafeResourceUrl } from '@angular/platform-browser';
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
    emailReport: Boolean = false
    returnReceipt: Boolean = false
    email: String
    shipmentType: String
    letterType: String
    code: String
    sender: Client
    shipmentStatus: String
    totalPrice: number
}
