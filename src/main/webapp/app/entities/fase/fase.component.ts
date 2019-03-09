import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFase } from 'app/shared/model/fase.model';
import { AccountService } from 'app/core';
import { FaseService } from './fase.service';

@Component({
    selector: 'jhi-fase',
    templateUrl: './fase.component.html'
})
export class FaseComponent implements OnInit, OnDestroy {
    fases: IFase[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected faseService: FaseService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.faseService
            .query()
            .pipe(
                filter((res: HttpResponse<IFase[]>) => res.ok),
                map((res: HttpResponse<IFase[]>) => res.body)
            )
            .subscribe(
                (res: IFase[]) => {
                    this.fases = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFase) {
        return item.id;
    }

    registerChangeInFases() {
        this.eventSubscriber = this.eventManager.subscribe('faseListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
