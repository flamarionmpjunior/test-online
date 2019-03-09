/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestOnlineTestModule } from '../../../test.module';
import { FaseCategoryDetailComponent } from 'app/entities/fase-category/fase-category-detail.component';
import { FaseCategory } from 'app/shared/model/fase-category.model';

describe('Component Tests', () => {
    describe('FaseCategory Management Detail Component', () => {
        let comp: FaseCategoryDetailComponent;
        let fixture: ComponentFixture<FaseCategoryDetailComponent>;
        const route = ({ data: of({ faseCategory: new FaseCategory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestOnlineTestModule],
                declarations: [FaseCategoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FaseCategoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FaseCategoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.faseCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
