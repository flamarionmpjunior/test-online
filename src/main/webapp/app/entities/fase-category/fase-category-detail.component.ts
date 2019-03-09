import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFaseCategory } from 'app/shared/model/fase-category.model';

@Component({
    selector: 'jhi-fase-category-detail',
    templateUrl: './fase-category-detail.component.html'
})
export class FaseCategoryDetailComponent implements OnInit {
    faseCategory: IFaseCategory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ faseCategory }) => {
            this.faseCategory = faseCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
