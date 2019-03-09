import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IFaseCategory } from 'app/shared/model/fase-category.model';
import { FaseCategoryService } from './fase-category.service';

@Component({
    selector: 'jhi-fase-category-update',
    templateUrl: './fase-category-update.component.html'
})
export class FaseCategoryUpdateComponent implements OnInit {
    faseCategory: IFaseCategory;
    isSaving: boolean;

    constructor(protected faseCategoryService: FaseCategoryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ faseCategory }) => {
            this.faseCategory = faseCategory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.faseCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.faseCategoryService.update(this.faseCategory));
        } else {
            this.subscribeToSaveResponse(this.faseCategoryService.create(this.faseCategory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFaseCategory>>) {
        result.subscribe((res: HttpResponse<IFaseCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
