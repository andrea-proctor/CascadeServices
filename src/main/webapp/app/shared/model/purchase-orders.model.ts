export interface IPurchaseOrders {
  id?: number;
  site?: string;
  supervisor?: string;
  poNumber?: string;
  partNumber?: string;
  partDescription?: string;
  qtyReplaced?: number;
  replacementCostEach?: string;
  freightCharge?: string;
  totalCharge?: string;
  notes?: string;
}

export class PurchaseOrders implements IPurchaseOrders {
  constructor(
    public id?: number,
    public site?: string,
    public supervisor?: string,
    public poNumber?: string,
    public partNumber?: string,
    public partDescription?: string,
    public qtyReplaced?: number,
    public replacementCostEach?: string,
    public freightCharge?: string,
    public totalCharge?: string,
    public notes?: string
  ) {}
}
