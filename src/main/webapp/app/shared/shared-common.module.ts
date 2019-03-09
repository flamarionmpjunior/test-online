import { NgModule } from '@angular/core';

import { TestOnlineSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TestOnlineSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TestOnlineSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TestOnlineSharedCommonModule {}
