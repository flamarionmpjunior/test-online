import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFaseCategory } from 'app/shared/model/fase-category.model';

type EntityResponseType = HttpResponse<IFaseCategory>;
type EntityArrayResponseType = HttpResponse<IFaseCategory[]>;

@Injectable({ providedIn: 'root' })
export class FaseCategoryService {
    public resourceUrl = SERVER_API_URL + 'api/fase-categories';

    constructor(protected http: HttpClient) {}

    create(faseCategory: IFaseCategory): Observable<EntityResponseType> {
        return this.http.post<IFaseCategory>(this.resourceUrl, faseCategory, { observe: 'response' });
    }

    update(faseCategory: IFaseCategory): Observable<EntityResponseType> {
        return this.http.put<IFaseCategory>(this.resourceUrl, faseCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFaseCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFaseCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
