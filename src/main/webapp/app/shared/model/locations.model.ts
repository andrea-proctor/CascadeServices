export interface ILocations {
  id?: number;
  site?: string;
  street?: string;
  zipCode?: string;
  city?: string;
  state?: string;
}

export class Locations implements ILocations {
  constructor(
    public id?: number,
    public site?: string,
    public street?: string,
    public zipCode?: string,
    public city?: string,
    public state?: string
  ) {}
}
