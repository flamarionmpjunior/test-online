import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fase } from 'app/shared/model/fase.model';
import { FaseService } from './fase.service';
import { FaseComponent } from './fase.component';
import { FaseDetailComponent } from './fase-detail.component';
import { FaseUpdateComponent } from './fase-update.component';
import { FaseDeletePopupComponent } from './fase-delete-dialog.component';
import { IFase } from 'app/shared/model/fase.model';

@Injectable({ providedIn: 'root' })
export class FaseResolve implements Resolve<IFase> {
    constructor(private service: FaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFase> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Fase>) => response.ok),
                map((fase: HttpResponse<Fase>) => fase.body)
            );
        }
        return of(new Fase());
    }
}

export const faseRoute: Routes = [
    {
        path: '',
        component: FaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FaseDetailComponent,
        resolve: {
            fase: FaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FaseUpdateComponent,
        resolve: {
            fase: FaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FaseUpdateComponent,
        resolve: {
            fase: FaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fasePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FaseDeletePopupComponent,
        resolve: {
            fase: FaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
