import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';
import { TrainingFaseMovementService } from './training-fase-movement.service';
import { ITrainingFase } from 'app/shared/model/training-fase.model';
import { TrainingFaseService } from 'app/entities/training-fase';
import { IMovement } from 'app/shared/model/movement.model';
import { MovementService } from 'app/entities/movement';

@Component({
    selector: 'jhi-training-fase-movement-update',
    templateUrl: './training-fase-movement-update.component.html'
})
export class TrainingFaseMovementUpdateComponent implements OnInit {
    trainingFaseMovement: ITrainingFaseMovement;
    isSaving: boolean;

    trainingfases: ITrainingFase[];

    movements: IMovement[];
    name: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected trainingFaseMovementService: TrainingFaseMovementService,
        protected trainingFaseService: TrainingFaseService,
        protected movementService: MovementService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ trainingFaseMovement }) => {
            this.trainingFaseMovement = trainingFaseMovement;
            this.name = this.trainingFaseMovement.name != null ? this.trainingFaseMovement.name.format(DATE_TIME_FORMAT) : null;
        });
        this.trainingFaseService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITrainingFase[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITrainingFase[]>) => response.body)
            )
            .subscribe((res: ITrainingFase[]) => (this.trainingfases = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.movementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMovement[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMovement[]>) => response.body)
            )
            .subscribe((res: IMovement[]) => (this.movements = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.trainingFaseMovement.name = this.name != null ? moment(this.name, DATE_TIME_FORMAT) : null;
        if (this.trainingFaseMovement.id !== undefined) {
            this.subscribeToSaveResponse(this.trainingFaseMovementService.update(this.trainingFaseMovement));
        } else {
            this.subscribeToSaveResponse(this.trainingFaseMovementService.create(this.trainingFaseMovement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingFaseMovement>>) {
        result.subscribe(
            (res: HttpResponse<ITrainingFaseMovement>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTrainingFaseById(index: number, item: ITrainingFase) {
        return item.id;
    }

    trackMovementById(index: number, item: IMovement) {
        return item.id;
    }
}
