import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFase } from 'app/shared/model/fase.model';
import { FaseService } from './fase.service';
import { IFaseCategory } from 'app/shared/model/fase-category.model';
import { FaseCategoryService } from 'app/entities/fase-category';

@Component({
    selector: 'jhi-fase-update',
    templateUrl: './fase-update.component.html'
})
export class FaseUpdateComponent implements OnInit {
    fase: IFase;
    isSaving: boolean;

    fasecategories: IFaseCategory[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected faseService: FaseService,
        protected faseCategoryService: FaseCategoryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fase }) => {
            this.fase = fase;
        });
        this.faseCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFaseCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFaseCategory[]>) => response.body)
            )
            .subscribe((res: IFaseCategory[]) => (this.fasecategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fase.id !== undefined) {
            this.subscribeToSaveResponse(this.faseService.update(this.fase));
        } else {
            this.subscribeToSaveResponse(this.faseService.create(this.fase));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFase>>) {
        result.subscribe((res: HttpResponse<IFase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFaseCategoryById(index: number, item: IFaseCategory) {
        return item.id;
    }
}
