import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFaseCategory } from 'app/shared/model/fase-category.model';
import { AccountService } from 'app/core';
import { FaseCategoryService } from './fase-category.service';

@Component({
    selector: 'jhi-fase-category',
    templateUrl: './fase-category.component.html'
})
export class FaseCategoryComponent implements OnInit, OnDestroy {
    faseCategories: IFaseCategory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected faseCategoryService: FaseCategoryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.faseCategoryService
            .query()
            .pipe(
                filter((res: HttpResponse<IFaseCategory[]>) => res.ok),
                map((res: HttpResponse<IFaseCategory[]>) => res.body)
            )
            .subscribe(
                (res: IFaseCategory[]) => {
                    this.faseCategories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFaseCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFaseCategory) {
        return item.id;
    }

    registerChangeInFaseCategories() {
        this.eventSubscriber = this.eventManager.subscribe('faseCategoryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
