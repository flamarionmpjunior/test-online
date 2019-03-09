/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestOnlineTestModule } from '../../../test.module';
import { TrainingFaseDeleteDialogComponent } from 'app/entities/training-fase/training-fase-delete-dialog.component';
import { TrainingFaseService } from 'app/entities/training-fase/training-fase.service';

describe('Component Tests', () => {
    describe('TrainingFase Management Delete Component', () => {
        let comp: TrainingFaseDeleteDialogComponent;
        let fixture: ComponentFixture<TrainingFaseDeleteDialogComponent>;
        let service: TrainingFaseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [TrainingFaseDeleteDialogComponent]
            })
                .overrideTemplate(TrainingFaseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrainingFaseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingFaseService);
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
