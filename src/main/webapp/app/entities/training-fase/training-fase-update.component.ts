import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITrainingFase } from 'app/shared/model/training-fase.model';
import { TrainingFaseService } from './training-fase.service';
import { ITraining } from 'app/shared/model/training.model';
import { TrainingService } from 'app/entities/training';
import { IFase } from 'app/shared/model/fase.model';
import { FaseService } from 'app/entities/fase';

@Component({
    selector: 'jhi-training-fase-update',
    templateUrl: './training-fase-update.component.html'
})
export class TrainingFaseUpdateComponent implements OnInit {
    trainingFase: ITrainingFase;
    isSaving: boolean;

    trainings: ITraining[];

    fases: IFase[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected trainingFaseService: TrainingFaseService,
        protected trainingService: TrainingService,
        protected faseService: FaseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ trainingFase }) => {
            this.trainingFase = trainingFase;
        });
        this.trainingService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITraining[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITraining[]>) => response.body)
            )
            .subscribe((res: ITraining[]) => (this.trainings = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.faseService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFase[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFase[]>) => response.body)
            )
            .subscribe((res: IFase[]) => (this.fases = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.trainingFase.id !== undefined) {
            this.subscribeToSaveResponse(this.trainingFaseService.update(this.trainingFase));
        } else {
            this.subscribeToSaveResponse(this.trainingFaseService.create(this.trainingFase));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingFase>>) {
        result.subscribe((res: HttpResponse<ITrainingFase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTrainingById(index: number, item: ITraining) {
        return item.id;
    }

    trackFaseById(index: number, item: IFase) {
        return item.id;
    }
}
