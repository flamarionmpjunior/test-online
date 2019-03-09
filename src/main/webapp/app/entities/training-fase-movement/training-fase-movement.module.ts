import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestOnlineSharedModule } from 'app/shared';
import {
    TrainingFaseMovementComponent,
    TrainingFaseMovementDetailComponent,
    TrainingFaseMovementUpdateComponent,
    TrainingFaseMovementDeletePopupComponent,
    TrainingFaseMovementDeleteDialogComponent,
    trainingFaseMovementRoute,
    trainingFaseMovementPopupRoute
} from './';

const ENTITY_STATES = [...trainingFaseMovementRoute, ...trainingFaseMovementPopupRoute];

@NgModule({
    imports: [TestOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TrainingFaseMovementComponent,
        TrainingFaseMovementDetailComponent,
        TrainingFaseMovementUpdateComponent,
        TrainingFaseMovementDeleteDialogComponent,
        TrainingFaseMovementDeletePopupComponent
    ],
    entryComponents: [
        TrainingFaseMovementComponent,
        TrainingFaseMovementUpdateComponent,
        TrainingFaseMovementDeleteDialogComponent,
        TrainingFaseMovementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestOnlineTrainingFaseMovementModule {}
