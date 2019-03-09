/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestOnlineTestModule } from '../../../test.module';
import { FaseCategoryComponent } from 'app/entities/fase-category/fase-category.component';
import { FaseCategoryService } from 'app/entities/fase-category/fase-category.service';
import { FaseCategory } from 'app/shared/model/fase-category.model';

describe('Component Tests', () => {
    describe('FaseCategory Management Component', () => {
        let comp: FaseCategoryComponent;
        let fixture: ComponentFixture<FaseCategoryComponent>;
        let service: FaseCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [FaseCategoryComponent],
                providers: []
            })
                .overrideTemplate(FaseCategoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FaseCategoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaseCategoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FaseCategory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.faseCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
