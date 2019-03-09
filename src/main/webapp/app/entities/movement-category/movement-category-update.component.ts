import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMovementCategory } from 'app/shared/model/movement-category.model';
import { MovementCategoryService } from './movement-category.service';
import { ISport } from 'app/shared/model/sport.model';
import { SportService } from 'app/entities/sport';

@Component({
    selector: 'jhi-movement-category-update',
    templateUrl: './movement-category-update.component.html'
})
export class MovementCategoryUpdateComponent implements OnInit {
    movementCategory: IMovementCategory;
    isSaving: boolean;

    sports: ISport[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected movementCategoryService: MovementCategoryService,
        protected sportService: SportService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movementCategory }) => {
            this.movementCategory = movementCategory;
        });
        this.sportService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISport[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISport[]>) => response.body)
            )
            .subscribe((res: ISport[]) => (this.sports = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.movementCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.movementCategoryService.update(this.movementCategory));
        } else {
            this.subscribeToSaveResponse(this.movementCategoryService.create(this.movementCategory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovementCategory>>) {
        result.subscribe((res: HttpResponse<IMovementCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSportById(index: number, item: ISport) {
        return item.id;
    }
}
