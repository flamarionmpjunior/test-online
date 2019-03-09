import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFaseCategory } from 'app/shared/model/fase-category.model';
import { FaseCategoryService } from './fase-category.service';

@Component({
    selector: 'jhi-fase-category-delete-dialog',
    templateUrl: './fase-category-delete-dialog.component.html'
})
export class FaseCategoryDeleteDialogComponent {
    faseCategory: IFaseCategory;

    constructor(
        protected faseCategoryService: FaseCategoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.faseCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'faseCategoryListModification',
                content: 'Deleted an faseCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fase-category-delete-popup',
    template: ''
})
export class FaseCategoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ faseCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FaseCategoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.faseCategory = faseCategory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fase-category', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fase-category', { outlets: { popup: null } }]);
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
