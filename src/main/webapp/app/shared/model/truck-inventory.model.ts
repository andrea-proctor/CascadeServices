export interface ITruckInventory {
  id?: number;
  date?: string;
  truckNumber?: string;
  site?: string;
  partNumber?: string;
  partDescription?: string;
  category?: string;
  qtyOnHand?: number;
  qtyOut?: number;
}

export class TruckInventory implements ITruckInventory {
  constructor(
    public id?: number,
    public date?: string,
    public truckNumber?: string,
    public site?: string,
    public partNumber?: string,
    public partDescription?: string,
    public category?: string,
    public qtyOnHand?: number,
    public qtyOut?: number
  ) {}
}
