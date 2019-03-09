/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestOnlineTestModule } from '../../../test.module';
import { FaseDeleteDialogComponent } from 'app/entities/fase/fase-delete-dialog.component';
import { FaseService } from 'app/entities/fase/fase.service';

describe('Component Tests', () => {
    describe('Fase Management Delete Component', () => {
        let comp: FaseDeleteDialogComponent;
        let fixture: ComponentFixture<FaseDeleteDialogComponent>;
        let service: FaseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [FaseDeleteDialogComponent]
            })
                .overrideTemplate(FaseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FaseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
