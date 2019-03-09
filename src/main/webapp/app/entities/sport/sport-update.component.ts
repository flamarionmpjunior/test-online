import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';

@Component({
    selector: 'jhi-sport-update',
    templateUrl: './sport-update.component.html'
})
export class SportUpdateComponent implements OnInit {
    sport: ISport;
    isSaving: boolean;

    constructor(protected sportService: SportService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sport }) => {
            this.sport = sport;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sport.id !== undefined) {
            this.subscribeToSaveResponse(this.sportService.update(this.sport));
        } else {
            this.subscribeToSaveResponse(this.sportService.create(this.sport));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISport>>) {
        result.subscribe((res: HttpResponse<ISport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
