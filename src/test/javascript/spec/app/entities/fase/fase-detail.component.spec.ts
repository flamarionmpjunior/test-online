/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestOnlineTestModule } from '../../../test.module';
import { FaseDetailComponent } from 'app/entities/fase/fase-detail.component';
import { Fase } from 'app/shared/model/fase.model';

describe('Component Tests', () => {
    describe('Fase Management Detail Component', () => {
        let comp: FaseDetailComponent;
        let fixture: ComponentFixture<FaseDetailComponent>;
        const route = ({ data: of({ fase: new Fase(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [FaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fase).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
