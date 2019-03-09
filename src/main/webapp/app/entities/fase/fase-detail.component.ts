import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFase } from 'app/shared/model/fase.model';

@Component({
    selector: 'jhi-fase-detail',
    templateUrl: './fase-detail.component.html'
})
export class FaseDetailComponent implements OnInit {
    fase: IFase;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fase }) => {
            this.fase = fase;
        });
    }

    previousState() {
        window.history.back();
    }
}
