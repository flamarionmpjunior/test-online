/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TestOnlineTestModule } from '../../../test.module';
import { TrainingFaseUpdateComponent } from 'app/entities/training-fase/training-fase-update.component';
import { TrainingFaseService } from 'app/entities/training-fase/training-fase.service';
import { TrainingFase } from 'app/shared/model/training-fase.model';

describe('Component Tests', () => {
    describe('TrainingFase Management Update Component', () => {
        let comp: TrainingFaseUpdateComponent;
        let fixture: ComponentFixture<TrainingFaseUpdateComponent>;
        let service: TrainingFaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [TrainingFaseUpdateComponent]
            })
                .overrideTemplate(TrainingFaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrainingFaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingFaseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TrainingFase(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.trainingFase = entity;
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
                    const entity = new TrainingFase();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.trainingFase = entity;
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
