import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMovement } from 'app/shared/model/movement.model';
import { MovementService } from './movement.service';
import { IMovementCategory } from 'app/shared/model/movement-category.model';
import { MovementCategoryService } from 'app/entities/movement-category';

@Component({
    selector: 'jhi-movement-update',
    templateUrl: './movement-update.component.html'
})
export class MovementUpdateComponent implements OnInit {
    movement: IMovement;
    isSaving: boolean;

    movementcategories: IMovementCategory[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected movementService: MovementService,
        protected movementCategoryService: MovementCategoryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movement }) => {
            this.movement = movement;
        });
        this.movementCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMovementCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMovementCategory[]>) => response.body)
            )
            .subscribe(
                (res: IMovementCategory[]) => (this.movementcategories = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.movement.id !== undefined) {
            this.subscribeToSaveResponse(this.movementService.update(this.movement));
        } else {
            this.subscribeToSaveResponse(this.movementService.create(this.movement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovement>>) {
        result.subscribe((res: HttpResponse<IMovement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMovementCategoryById(index: number, item: IMovementCategory) {
        return item.id;
    }
}
