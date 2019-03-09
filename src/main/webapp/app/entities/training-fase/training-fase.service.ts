import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrainingFase } from 'app/shared/model/training-fase.model';

type EntityResponseType = HttpResponse<ITrainingFase>;
type EntityArrayResponseType = HttpResponse<ITrainingFase[]>;

@Injectable({ providedIn: 'root' })
export class TrainingFaseService {
    public resourceUrl = SERVER_API_URL + 'api/training-fases';

    constructor(protected http: HttpClient) {}

    create(trainingFase: ITrainingFase): Observable<EntityResponseType> {
        return this.http.post<ITrainingFase>(this.resourceUrl, trainingFase, { observe: 'response' });
    }

    update(trainingFase: ITrainingFase): Observable<EntityResponseType> {
        return this.http.put<ITrainingFase>(this.resourceUrl, trainingFase, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITrainingFase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITrainingFase[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
