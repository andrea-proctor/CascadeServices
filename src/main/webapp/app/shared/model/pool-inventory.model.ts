export const enum Language {
  FRENCH = 'FRENCH',
  ENGLISH = 'ENGLISH',
  SPANISH = 'SPANISH'
}

export interface IPoolInventory {
  id?: number;
  date?: string;
  partnumber?: string;
  partdescription?: string;
  qtyin?: number;
  qtyout?: number;
  language?: Language;
}

export class PoolInventory implements IPoolInventory {
  constructor(
    public id?: number,
    public date?: string,
    public partnumber?: string,
    public partdescription?: string,
    public qtyin?: number,
    public qtyout?: number,
    public language?: Language
  ) {}
}
