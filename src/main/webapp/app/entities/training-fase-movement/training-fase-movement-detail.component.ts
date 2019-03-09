import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';

@Component({
    selector: 'jhi-training-fase-movement-detail',
    templateUrl: './training-fase-movement-detail.component.html'
})
export class TrainingFaseMovementDetailComponent implements OnInit {
    trainingFaseMovement: ITrainingFaseMovement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trainingFaseMovement }) => {
            this.trainingFaseMovement = trainingFaseMovement;
        });
    }

    previousState() {
        window.history.back();
    }
}
