/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestOnlineTestModule } from '../../../test.module';
import { TrainingFaseComponent } from 'app/entities/training-fase/training-fase.component';
import { TrainingFaseService } from 'app/entities/training-fase/training-fase.service';
import { TrainingFase } from 'app/shared/model/training-fase.model';

describe('Component Tests', () => {
    describe('TrainingFase Management Component', () => {
        let comp: TrainingFaseComponent;
        let fixture: ComponentFixture<TrainingFaseComponent>;
        let service: TrainingFaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [TrainingFaseComponent],
                providers: []
            })
                .overrideTemplate(TrainingFaseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrainingFaseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingFaseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TrainingFase(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.trainingFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
