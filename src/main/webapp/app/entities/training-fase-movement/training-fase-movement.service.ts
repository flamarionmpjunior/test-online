import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrainingFaseMovement } from 'app/shared/model/training-fase-movement.model';

type EntityResponseType = HttpResponse<ITrainingFaseMovement>;
type EntityArrayResponseType = HttpResponse<ITrainingFaseMovement[]>;

@Injectable({ providedIn: 'root' })
export class TrainingFaseMovementService {
    public resourceUrl = SERVER_API_URL + 'api/training-fase-movements';

    constructor(protected http: HttpClient) {}

    create(trainingFaseMovement: ITrainingFaseMovement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(trainingFaseMovement);
        return this.http
            .post<ITrainingFaseMovement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(trainingFaseMovement: ITrainingFaseMovement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(trainingFaseMovement);
        return this.http
            .put<ITrainingFaseMovement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITrainingFaseMovement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITrainingFaseMovement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(trainingFaseMovement: ITrainingFaseMovement): ITrainingFaseMovement {
        const copy: ITrainingFaseMovement = Object.assign({}, trainingFaseMovement, {
            name: trainingFaseMovement.name != null && trainingFaseMovement.name.isValid() ? trainingFaseMovement.name.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.name = res.body.name != null ? moment(res.body.name) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((trainingFaseMovement: ITrainingFaseMovement) => {
                trainingFaseMovement.name = trainingFaseMovement.name != null ? moment(trainingFaseMovement.name) : null;
            });
        }
        return res;
    }
}
