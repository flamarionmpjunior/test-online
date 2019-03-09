import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITraining } from 'app/shared/model/training.model';
import { AccountService } from 'app/core';
import { TrainingService } from './training.service';

@Component({
    selector: 'jhi-training',
    templateUrl: './training.component.html'
})
export class TrainingComponent implements OnInit, OnDestroy {
    trainings: ITraining[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected trainingService: TrainingService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.trainingService
            .query()
            .pipe(
                filter((res: HttpResponse<ITraining[]>) => res.ok),
                map((res: HttpResponse<ITraining[]>) => res.body)
            )
            .subscribe(
                (res: ITraining[]) => {
                    this.trainings = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTrainings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITraining) {
        return item.id;
    }

    registerChangeInTrainings() {
        this.eventSubscriber = this.eventManager.subscribe('trainingListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
