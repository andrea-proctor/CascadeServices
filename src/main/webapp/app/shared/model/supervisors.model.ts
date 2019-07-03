export interface ISupervisors {
  id?: number;
  firstName?: string;
  lastName?: string;
  site?: string;
}

export class Supervisors implements ISupervisors {
  constructor(public id?: number, public firstName?: string, public lastName?: string, public site?: string) {}
}
