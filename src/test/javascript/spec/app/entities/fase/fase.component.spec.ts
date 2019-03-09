/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestOnlineTestModule } from '../../../test.module';
import { FaseComponent } from 'app/entities/fase/fase.component';
import { FaseService } from 'app/entities/fase/fase.service';
import { Fase } from 'app/shared/model/fase.model';

describe('Component Tests', () => {
    describe('Fase Management Component', () => {
        let comp: FaseComponent;
        let fixture: ComponentFixture<FaseComponent>;
        let service: FaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [FaseComponent],
                providers: []
            })
                .overrideTemplate(FaseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FaseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Fase(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
