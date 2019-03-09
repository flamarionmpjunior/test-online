import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MovementCategory } from 'app/shared/model/movement-category.model';
import { MovementCategoryService } from './movement-category.service';
import { MovementCategoryComponent } from './movement-category.component';
import { MovementCategoryDetailComponent } from './movement-category-detail.component';
import { MovementCategoryUpdateComponent } from './movement-category-update.component';
import { MovementCategoryDeletePopupComponent } from './movement-category-delete-dialog.component';
import { IMovementCategory } from 'app/shared/model/movement-category.model';

@Injectable({ providedIn: 'root' })
export class MovementCategoryResolve implements Resolve<IMovementCategory> {
    constructor(private service: MovementCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovementCategory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MovementCategory>) => response.ok),
                map((movementCategory: HttpResponse<MovementCategory>) => movementCategory.body)
            );
        }
        return of(new MovementCategory());
    }
}

export const movementCategoryRoute: Routes = [
    {
        path: '',
        component: MovementCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovementCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MovementCategoryDetailComponent,
        resolve: {
            movementCategory: MovementCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovementCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MovementCategoryUpdateComponent,
        resolve: {
            movementCategory: MovementCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovementCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MovementCategoryUpdateComponent,
        resolve: {
            movementCategory: MovementCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovementCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movementCategoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MovementCategoryDeletePopupComponent,
        resolve: {
            movementCategory: MovementCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovementCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
