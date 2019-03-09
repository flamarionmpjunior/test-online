import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingFase } from 'app/shared/model/training-fase.model';
import { TrainingFaseService } from './training-fase.service';

@Component({
    selector: 'jhi-training-fase-delete-dialog',
    templateUrl: './training-fase-delete-dialog.component.html'
})
export class TrainingFaseDeleteDialogComponent {
    trainingFase: ITrainingFase;

    constructor(
        protected trainingFaseService: TrainingFaseService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.trainingFaseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'trainingFaseListModification',
                content: 'Deleted an trainingFase'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-training-fase-delete-popup',
    template: ''
})
export class TrainingFaseDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trainingFase }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TrainingFaseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.trainingFase = trainingFase;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/training-fase', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/training-fase', { outlets: { popup: null } }]);
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
