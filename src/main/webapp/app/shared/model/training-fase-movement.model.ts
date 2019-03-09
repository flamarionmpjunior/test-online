import { Moment } from 'moment';
import { ITrainingFase } from 'app/shared/model/training-fase.model';
import { IMovement } from 'app/shared/model/movement.model';

export interface ITrainingFaseMovement {
    id?: number;
    name?: Moment;
    name?: ITrainingFase;
    name?: IMovement;
}

export class TrainingFaseMovement implements ITrainingFaseMovement {
    constructor(public id?: number, public name?: Moment, public name?: ITrainingFase, public name?: IMovement) {}
}
