import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';
import { TrainingFaseMovementService } from './training-fase-movement.service';
import { TrainingFaseMovementComponent } from './training-fase-movement.component';
import { TrainingFaseMovementDetailComponent } from './training-fase-movement-detail.component';
import { TrainingFaseMovementUpdateComponent } from './training-fase-movement-update.component';
import { TrainingFaseMovementDeletePopupComponent } from './training-fase-movement-delete-dialog.component';
import { ITrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';

@Injectable({ providedIn: 'root' })
export class TrainingFaseMovementResolve implements Resolve<ITrainingFaseMovement> {
    constructor(private service: TrainingFaseMovementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITrainingFaseMovement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TrainingFaseMovement>) => response.ok),
                map((trainingFaseMovement: HttpResponse<TrainingFaseMovement>) => trainingFaseMovement.body)
            );
        }
        return of(new TrainingFaseMovement());
    }
}

export const trainingFaseMovementRoute: Routes = [
    {
        path: '',
        component: TrainingFaseMovementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFaseMovements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TrainingFaseMovementDetailComponent,
        resolve: {
            trainingFaseMovement: TrainingFaseMovementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFaseMovements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TrainingFaseMovementUpdateComponent,
        resolve: {
            trainingFaseMovement: TrainingFaseMovementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFaseMovements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TrainingFaseMovementUpdateComponent,
        resolve: {
            trainingFaseMovement: TrainingFaseMovementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFaseMovements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trainingFaseMovementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TrainingFaseMovementDeletePopupComponent,
        resolve: {
            trainingFaseMovement: TrainingFaseMovementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFaseMovements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
