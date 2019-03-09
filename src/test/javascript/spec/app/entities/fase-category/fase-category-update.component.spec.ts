/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TestOnlineTestModule } from '../../../test.module';
import { FaseCategoryUpdateComponent } from 'app/entities/fase-category/fase-category-update.component';
import { FaseCategoryService } from 'app/entities/fase-category/fase-category.service';
import { FaseCategory } from 'app/shared/model/fase-category.model';

describe('Component Tests', () => {
    describe('FaseCategory Management Update Component', () => {
        let comp: FaseCategoryUpdateComponent;
        let fixture: ComponentFixture<FaseCategoryUpdateComponent>;
        let service: FaseCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [FaseCategoryUpdateComponent]
            })
                .overrideTemplate(FaseCategoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FaseCategoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaseCategoryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FaseCategory(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.faseCategory = entity;
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
                    const entity = new FaseCategory();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.faseCategory = entity;
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
