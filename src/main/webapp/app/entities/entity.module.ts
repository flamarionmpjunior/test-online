import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'sport',
                loadChildren: './sport/sport.module#TestOnlineSportModule'
            },
            {
                path: 'movement-category',
                loadChildren: './movement-category/movement-category.module#TestOnlineMovementCategoryModule'
            },
            {
                path: 'movement',
                loadChildren: './movement/movement.module#TestOnlineMovementModule'
            },
            {
                path: 'fase',
                loadChildren: './fase/fase.module#TestOnlineFaseModule'
            },
            {
                path: 'fase-category',
                loadChildren: './fase-category/fase-category.module#TestOnlineFaseCategoryModule'
            },
            {
                path: 'training',
                loadChildren: './training/training.module#TestOnlineTrainingModule'
            },
            {
                path: 'training-fase',
                loadChildren: './training-fase/training-fase.module#TestOnlineTrainingFaseModule'
            },
            {
                path: 'training-fase-movement',
                loadChildren: './training-fase-movement/training-fase-movement.module#TestOnlineTrainingFaseMovementModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestOnlineEntityModule {}
