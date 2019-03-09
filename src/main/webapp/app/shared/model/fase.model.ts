import { IFaseCategory } from 'app/shared/model/fase-category.model';

export interface IFase {
    id?: number;
    name?: string;
    description?: string;
    name?: IFaseCategory;
}

export class Fase implements IFase {
    constructor(public id?: number, public name?: string, public description?: string, public name?: IFaseCategory) {}
}
