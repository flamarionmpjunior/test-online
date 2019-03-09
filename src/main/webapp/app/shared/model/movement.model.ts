import { IMovementCategory } from 'app/shared/model/movement-category.model';

export interface IMovement {
    id?: number;
    name?: string;
    abreviation?: string;
    description?: string;
    webLink?: string;
    name?: IMovementCategory;
}

export class Movement implements IMovement {
    constructor(
        public id?: number,
        public name?: string,
        public abreviation?: string,
        public description?: string,
        public webLink?: string,
        public name?: IMovementCategory
    ) {}
}
