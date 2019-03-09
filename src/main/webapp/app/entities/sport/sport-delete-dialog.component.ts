import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';

@Component({
    selector: 'jhi-sport-delete-dialog',
    templateUrl: './sport-delete-dialog.component.html'
})
export class SportDeleteDialogComponent {
    sport: ISport;

    constructor(protected sportService: SportService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sportListModification',
                content: 'Deleted an sport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sport-delete-popup',
    template: ''
})
export class SportDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SportDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sport = sport;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sport', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sport', { outlets: { popup: null } }]);
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
