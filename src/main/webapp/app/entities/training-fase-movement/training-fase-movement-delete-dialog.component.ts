import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';
import { TrainingFaseMovementService } from './training-fase-movement.service';

@Component({
    selector: 'jhi-training-fase-movement-delete-dialog',
    templateUrl: './training-fase-movement-delete-dialog.component.html'
})
export class TrainingFaseMovementDeleteDialogComponent {
    trainingFaseMovement: ITrainingFaseMovement;

    constructor(
        protected trainingFaseMovementService: TrainingFaseMovementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.trainingFaseMovementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'trainingFaseMovementListModification',
                content: 'Deleted an trainingFaseMovement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-training-fase-movement-delete-popup',
    template: ''
})
export class TrainingFaseMovementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trainingFaseMovement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TrainingFaseMovementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.trainingFaseMovement = trainingFaseMovement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/training-fase-movement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/training-fase-movement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
