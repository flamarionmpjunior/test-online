import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestOnlineSharedModule } from 'app/shared';
import {
    FaseComponent,
    FaseDetailComponent,
    FaseUpdateComponent,
    FaseDeletePopupComponent,
    FaseDeleteDialogComponent,
    faseRoute,
    fasePopupRoute
} from './';

const ENTITY_STATES = [...faseRoute, ...fasePopupRoute];

@NgModule({
    imports: [TestOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FaseComponent, FaseDetailComponent, FaseUpdateComponent, FaseDeleteDialogComponent, FaseDeletePopupComponent],
    entryComponents: [FaseComponent, FaseUpdateComponent, FaseDeleteDialogComponent, FaseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestOnlineFaseModule {}
