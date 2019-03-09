/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestOnlineTestModule } from '../../../test.module';
import { TrainingFaseDetailComponent } from 'app/entities/training-fase/training-fase-detail.component';
import { TrainingFase } from 'app/shared/model/training-fase.model';

describe('Component Tests', () => {
    describe('TrainingFase Management Detail Component', () => {
        let comp: TrainingFaseDetailComponent;
        let fixture: ComponentFixture<TrainingFaseDetailComponent>;
        const route = ({ data: of({ trainingFase: new TrainingFase(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [TrainingFaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TrainingFaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrainingFaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.trainingFase).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
