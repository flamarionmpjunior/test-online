/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TestOnlineTestModule } from '../../../test.module';
import { TrainingFaseMovementUpdateComponent } from 'app/entities/training-fase-movement/training-fase-movement-update.component';
import { TrainingFaseMovementService } from 'app/entities/training-fase-movement/training-fase-movement.service';
import { TrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';

describe('Component Tests', () => {
    describe('TrainingFaseMovement Management Update Component', () => {
        let comp: TrainingFaseMovementUpdateComponent;
        let fixture: ComponentFixture<TrainingFaseMovementUpdateComponent>;
        let service: TrainingFaseMovementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [TrainingFaseMovementUpdateComponent]
            })
                .overrideTemplate(TrainingFaseMovementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrainingFaseMovementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingFaseMovementService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TrainingFaseMovement(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.trainingFaseMovement = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TrainingFaseMovement();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.trainingFaseMovement = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
