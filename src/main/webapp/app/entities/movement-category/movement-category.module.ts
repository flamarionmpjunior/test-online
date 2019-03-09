import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestOnlineSharedModule } from 'app/shared';
import {
    MovementCategoryComponent,
    MovementCategoryDetailComponent,
    MovementCategoryUpdateComponent,
    MovementCategoryDeletePopupComponent,
    MovementCategoryDeleteDialogComponent,
    movementCategoryRoute,
    movementCategoryPopupRoute
} from './';

const ENTITY_STATES = [...movementCategoryRoute, ...movementCategoryPopupRoute];

@NgModule({
    imports: [TestOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MovementCategoryComponent,
        MovementCategoryDetailComponent,
        MovementCategoryUpdateComponent,
        MovementCategoryDeleteDialogComponent,
        MovementCategoryDeletePopupComponent
    ],
    entryComponents: [
        MovementCategoryComponent,
        MovementCategoryUpdateComponent,
        MovementCategoryDeleteDialogComponent,
        MovementCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestOnlineMovementCategoryModule {}
