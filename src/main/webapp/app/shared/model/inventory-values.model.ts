export interface IInventoryValues {
  id?: number;
  partNumber?: string;
  partDescription?: string;
  valueEach?: string;
}

export class InventoryValues implements IInventoryValues {
  constructor(public id?: number, public partNumber?: string, public partDescription?: string, public valueEach?: string) {}
}
