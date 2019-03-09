import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';
import { AccountService } from 'app/core';
import { TrainingFaseMovementService } from './training-fase-movement.service';

@Component({
    selector: 'jhi-training-fase-movement',
    templateUrl: './training-fase-movement.component.html'
})
export class TrainingFaseMovementComponent implements OnInit, OnDestroy {
    trainingFaseMovements: ITrainingFaseMovement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected trainingFaseMovementService: TrainingFaseMovementService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.trainingFaseMovementService
            .query()
            .pipe(
                filter((res: HttpResponse<ITrainingFaseMovement[]>) => res.ok),
                map((res: HttpResponse<ITrainingFaseMovement[]>) => res.body)
            )
            .subscribe(
                (res: ITrainingFaseMovement[]) => {
                    this.trainingFaseMovements = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTrainingFaseMovements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITrainingFaseMovement) {
        return item.id;
    }

    registerChangeInTrainingFaseMovements() {
        this.eventSubscriber = this.eventManager.subscribe('trainingFaseMovementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
