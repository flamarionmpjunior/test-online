import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestOnlineSharedModule } from 'app/shared';
import {
    TrainingFaseComponent,
    TrainingFaseDetailComponent,
    TrainingFaseUpdateComponent,
    TrainingFaseDeletePopupComponent,
    TrainingFaseDeleteDialogComponent,
    trainingFaseRoute,
    trainingFasePopupRoute
} from './';

const ENTITY_STATES = [...trainingFaseRoute, ...trainingFasePopupRoute];

@NgModule({
    imports: [TestOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TrainingFaseComponent,
        TrainingFaseDetailComponent,
        TrainingFaseUpdateComponent,
        TrainingFaseDeleteDialogComponent,
        TrainingFaseDeletePopupComponent
    ],
    entryComponents: [
        TrainingFaseComponent,
        TrainingFaseUpdateComponent,
        TrainingFaseDeleteDialogComponent,
        TrainingFaseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestOnlineTrainingFaseModule {}
