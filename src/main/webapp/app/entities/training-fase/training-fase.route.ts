import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TrainingFase } from 'app/shared/model/training-fase.model';
import { TrainingFaseService } from './training-fase.service';
import { TrainingFaseComponent } from './training-fase.component';
import { TrainingFaseDetailComponent } from './training-fase-detail.component';
import { TrainingFaseUpdateComponent } from './training-fase-update.component';
import { TrainingFaseDeletePopupComponent } from './training-fase-delete-dialog.component';
import { ITrainingFase } from 'app/shared/model/training-fase.model';

@Injectable({ providedIn: 'root' })
export class TrainingFaseResolve implements Resolve<ITrainingFase> {
    constructor(private service: TrainingFaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITrainingFase> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TrainingFase>) => response.ok),
                map((trainingFase: HttpResponse<TrainingFase>) => trainingFase.body)
            );
        }
        return of(new TrainingFase());
    }
}

export const trainingFaseRoute: Routes = [
    {
        path: '',
        component: TrainingFaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TrainingFaseDetailComponent,
        resolve: {
            trainingFase: TrainingFaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TrainingFaseUpdateComponent,
        resolve: {
            trainingFase: TrainingFaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TrainingFaseUpdateComponent,
        resolve: {
            trainingFase: TrainingFaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trainingFasePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TrainingFaseDeletePopupComponent,
        resolve: {
            trainingFase: TrainingFaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TrainingFases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
