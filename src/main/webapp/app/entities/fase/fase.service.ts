import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFase } from 'app/shared/model/fase.model';

type EntityResponseType = HttpResponse<IFase>;
type EntityArrayResponseType = HttpResponse<IFase[]>;

@Injectable({ providedIn: 'root' })
export class FaseService {
    public resourceUrl = SERVER_API_URL + 'api/fases';

    constructor(protected http: HttpClient) {}

    create(fase: IFase): Observable<EntityResponseType> {
        return this.http.post<IFase>(this.resourceUrl, fase, { observe: 'response' });
    }

    update(fase: IFase): Observable<EntityResponseType> {
        return this.http.put<IFase>(this.resourceUrl, fase, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFase[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
