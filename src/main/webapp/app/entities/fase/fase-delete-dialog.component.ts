import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFase } from 'app/shared/model/fase.model';
import { FaseService } from './fase.service';

@Component({
    selector: 'jhi-fase-delete-dialog',
    templateUrl: './fase-delete-dialog.component.html'
})
export class FaseDeleteDialogComponent {
    fase: IFase;

    constructor(protected faseService: FaseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.faseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'faseListModification',
                content: 'Deleted an fase'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fase-delete-popup',
    template: ''
})
export class FaseDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fase }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FaseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.fase = fase;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fase', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fase', { outlets: { popup: null } }]);
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
