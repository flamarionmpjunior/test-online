import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMovementCategory } from 'app/shared/model/movement-category.model';
import { AccountService } from 'app/core';
import { MovementCategoryService } from './movement-category.service';

@Component({
    selector: 'jhi-movement-category',
    templateUrl: './movement-category.component.html'
})
export class MovementCategoryComponent implements OnInit, OnDestroy {
    movementCategories: IMovementCategory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected movementCategoryService: MovementCategoryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.movementCategoryService
            .query()
            .pipe(
                filter((res: HttpResponse<IMovementCategory[]>) => res.ok),
                map((res: HttpResponse<IMovementCategory[]>) => res.body)
            )
            .subscribe(
                (res: IMovementCategory[]) => {
                    this.movementCategories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMovementCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMovementCategory) {
        return item.id;
    }

    registerChangeInMovementCategories() {
        this.eventSubscriber = this.eventManager.subscribe('movementCategoryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
