export interface ITraining {
    id?: number;
    name?: string;
    description?: string;
}

export class Training implements ITraining {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
