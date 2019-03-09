/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestOnlineTestModule } from '../../../test.module';
import { TrainingFaseMovementDeleteDialogComponent } from 'app/entities/training-fase-movement/training-fase-movement-delete-dialog.component';
import { TrainingFaseMovementService } from 'app/entities/training-fase-movement/training-fase-movement.service';

describe('Component Tests', () => {
    describe('TrainingFaseMovement Management Delete Component', () => {
        let comp: TrainingFaseMovementDeleteDialogComponent;
        let fixture: ComponentFixture<TrainingFaseMovementDeleteDialogComponent>;
        let service: TrainingFaseMovementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [TrainingFaseMovementDeleteDialogComponent]
            })
                .overrideTemplate(TrainingFaseMovementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrainingFaseMovementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingFaseMovementService);
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
