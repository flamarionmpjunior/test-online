import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FaseCategory } from 'app/shared/model/fase-category.model';
import { FaseCategoryService } from './fase-category.service';
import { FaseCategoryComponent } from './fase-category.component';
import { FaseCategoryDetailComponent } from './fase-category-detail.component';
import { FaseCategoryUpdateComponent } from './fase-category-update.component';
import { FaseCategoryDeletePopupComponent } from './fase-category-delete-dialog.component';
import { IFaseCategory } from 'app/shared/model/fase-category.model';

@Injectable({ providedIn: 'root' })
export class FaseCategoryResolve implements Resolve<IFaseCategory> {
    constructor(private service: FaseCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFaseCategory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FaseCategory>) => response.ok),
                map((faseCategory: HttpResponse<FaseCategory>) => faseCategory.body)
            );
        }
        return of(new FaseCategory());
    }
}

export const faseCategoryRoute: Routes = [
    {
        path: '',
        component: FaseCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FaseCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FaseCategoryDetailComponent,
        resolve: {
            faseCategory: FaseCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FaseCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FaseCategoryUpdateComponent,
        resolve: {
            faseCategory: FaseCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FaseCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FaseCategoryUpdateComponent,
        resolve: {
            faseCategory: FaseCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FaseCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const faseCategoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FaseCategoryDeletePopupComponent,
        resolve: {
            faseCategory: FaseCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FaseCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
