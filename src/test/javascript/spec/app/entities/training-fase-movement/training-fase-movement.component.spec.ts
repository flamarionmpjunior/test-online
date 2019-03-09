/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestOnlineTestModule } from '../../../test.module';
import { TrainingFaseMovementComponent } from 'app/entities/training-fase-movement/training-fase-movement.component';
import { TrainingFaseMovementService } from 'app/entities/training-fase-movement/training-fase-movement.service';
import { TrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';

describe('Component Tests', () => {
    describe('TrainingFaseMovement Management Component', () => {
        let comp: TrainingFaseMovementComponent;
        let fixture: ComponentFixture<TrainingFaseMovementComponent>;
        let service: TrainingFaseMovementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [TrainingFaseMovementComponent],
                providers: []
            })
                .overrideTemplate(TrainingFaseMovementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrainingFaseMovementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingFaseMovementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TrainingFaseMovement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.trainingFaseMovements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
