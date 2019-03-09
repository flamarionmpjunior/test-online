import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingFase } from 'app/shared/model/training-fase.model';

@Component({
    selector: 'jhi-training-fase-detail',
    templateUrl: './training-fase-detail.component.html'
})
export class TrainingFaseDetailComponent implements OnInit {
    trainingFase: ITrainingFase;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trainingFase }) => {
            this.trainingFase = trainingFase;
        });
    }

    previousState() {
        window.history.back();
    }
}
