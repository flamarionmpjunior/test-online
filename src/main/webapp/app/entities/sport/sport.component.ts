import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISport } from 'app/shared/model/sport.model';
import { AccountService } from 'app/core';
import { SportService } from './sport.service';

@Component({
    selector: 'jhi-sport',
    templateUrl: './sport.component.html'
})
export class SportComponent implements OnInit, OnDestroy {
    sports: ISport[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected sportService: SportService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.sportService
            .query()
            .pipe(
                filter((res: HttpResponse<ISport[]>) => res.ok),
                map((res: HttpResponse<ISport[]>) => res.body)
            )
            .subscribe(
                (res: ISport[]) => {
                    this.sports = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISport) {
        return item.id;
    }

    registerChangeInSports() {
        this.eventSubscriber = this.eventManager.subscribe('sportListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
