export interface IServiceOrders {
  id?: number;
  site?: string;
  supervisor?: string;
  soNumber?: string;
  partNumber?: string;
  partDescription?: string;
  repairCost?: string;
  freightCharge?: string;
  totalCharge?: string;
  notes?: string;
}

export class ServiceOrders implements IServiceOrders {
  constructor(
    public id?: number,
    public site?: string,
    public supervisor?: string,
    public soNumber?: string,
    public partNumber?: string,
    public partDescription?: string,
    public repairCost?: string,
    public freightCharge?: string,
    public totalCharge?: string,
    public notes?: string
  ) {}
}
