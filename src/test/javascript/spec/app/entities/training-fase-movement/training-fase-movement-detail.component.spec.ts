/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestOnlineTestModule } from '../../../test.module';
import { TrainingFaseMovementDetailComponent } from 'app/entities/training-fase-movement/training-fase-movement-detail.component';
import { TrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';

describe('Component Tests', () => {
    describe('TrainingFaseMovement Management Detail Component', () => {
        let comp: TrainingFaseMovementDetailComponent;
        let fixture: ComponentFixture<TrainingFaseMovementDetailComponent>;
        const route = ({ data: of({ trainingFaseMovement: new TrainingFaseMovement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [TrainingFaseMovementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TrainingFaseMovementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrainingFaseMovementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.trainingFaseMovement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
