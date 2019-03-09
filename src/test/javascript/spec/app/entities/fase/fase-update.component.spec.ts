/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TestOnlineTestModule } from '../../../test.module';
import { FaseUpdateComponent } from 'app/entities/fase/fase-update.component';
import { FaseService } from 'app/entities/fase/fase.service';
import { Fase } from 'app/shared/model/fase.model';

describe('Component Tests', () => {
    describe('Fase Management Update Component', () => {
        let comp: FaseUpdateComponent;
        let fixture: ComponentFixture<FaseUpdateComponent>;
        let service: FaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [FaseUpdateComponent]
            })
                .overrideTemplate(FaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Fase(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.fase = entity;
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
                    const entity = new Fase();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.fase = entity;
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
