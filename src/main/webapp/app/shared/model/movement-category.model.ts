import { ISport } from 'app/shared/model/sport.model';

export interface IMovementCategory {
    id?: number;
    name?: string;
    description?: string;
    name?: ISport;
}

export class MovementCategory implements IMovementCategory {
    constructor(public id?: number, public name?: string, public description?: string, public name?: ISport) {}
}
