import { ITraining } from 'app/shared/model/training.model';
import { IFase } from 'app/shared/model/fase.model';

export interface ITrainingFase {
    id?: number;
    name?: string;
    description?: string;
    name?: ITraining;
    name?: IFase;
}

export class TrainingFase implements ITrainingFase {
    constructor(public id?: number, public name?: string, public description?: string, public name?: ITraining, public name?: IFase) {}
}
