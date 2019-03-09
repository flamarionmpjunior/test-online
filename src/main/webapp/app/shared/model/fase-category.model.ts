export interface IFaseCategory {
    id?: number;
    name?: string;
    description?: string;
    gameScore?: boolean;
}

export class FaseCategory implements IFaseCategory {
    constructor(public id?: number, public name?: string, public description?: string, public gameScore?: boolean) {
        this.gameScore = this.gameScore || false;
    }
}
