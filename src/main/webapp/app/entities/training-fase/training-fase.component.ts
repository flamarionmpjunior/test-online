import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITrainingFase } from 'app/shared/model/training-fase.model';
import { AccountService } from 'app/core';
import { TrainingFaseService } from './training-fase.service';

@Component({
    selector: 'jhi-training-fase',
    templateUrl: './training-fase.component.html'
})
export class TrainingFaseComponent implements OnInit, OnDestroy {
    trainingFases: ITrainingFase[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected trainingFaseService: TrainingFaseService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.trainingFaseService
            .query()
            .pipe(
                filter((res: HttpResponse<ITrainingFase[]>) => res.ok),
                map((res: HttpResponse<ITrainingFase[]>) => res.body)
            )
            .subscribe(
                (res: ITrainingFase[]) => {
                    this.trainingFases = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTrainingFases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITrainingFase) {
        return item.id;
    }

    registerChangeInTrainingFases() {
        this.eventSubscriber = this.eventManager.subscribe('trainingFaseListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
