import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestOnlineSharedModule } from 'app/shared';
import {
    FaseCategoryComponent,
    FaseCategoryDetailComponent,
    FaseCategoryUpdateComponent,
    FaseCategoryDeletePopupComponent,
    FaseCategoryDeleteDialogComponent,
    faseCategoryRoute,
    faseCategoryPopupRoute
} from './';

const ENTITY_STATES = [...faseCategoryRoute, ...faseCategoryPopupRoute];

@NgModule({
    imports: [TestOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FaseCategoryComponent,
        FaseCategoryDetailComponent,
        FaseCategoryUpdateComponent,
        FaseCategoryDeleteDialogComponent,
        FaseCategoryDeletePopupComponent
    ],
    entryComponents: [
        FaseCategoryComponent,
        FaseCategoryUpdateComponent,
        FaseCategoryDeleteDialogComponent,
        FaseCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestOnlineFaseCategoryModule {}
