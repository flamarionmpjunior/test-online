import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovement } from 'app/shared/model/movement.model';
import { MovementService } from './movement.service';

@Component({
    selector: 'jhi-movement-delete-dialog',
    templateUrl: './movement-delete-dialog.component.html'
})
export class MovementDeleteDialogComponent {
    movement: IMovement;

    constructor(protected movementService: MovementService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'movementListModification',
                content: 'Deleted an movement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movement-delete-popup',
    template: ''
})
export class MovementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MovementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.movement = movement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/movement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/movement', { outlets: { popup: null } }]);
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
