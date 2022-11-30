import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export interface SubscriptionsPayload {
  userId: string,
  subscriptions: SubscriptionPayload[]
}

export interface SubscriptionPayload {
  serviceName: string,
  monthlyPrice: number
}

@Injectable({
  providedIn: 'root'
})
export class SubscriptionClientService {
  userId = '1'

  constructor(private httpClient: HttpClient) {
  }

  getUserSubscriptions(): Observable<SubscriptionsPayload> {
    return this.httpClient.get<SubscriptionsPayload>(`http://localhost:8080/subscriptions/${this.userId}`)
  }

  updateUserSubscriptions(serviceName: string, price: number): Observable<void> {
    const payload: SubscriptionsPayload = {
      userId: this.userId,
      subscriptions: [{
        serviceName: serviceName,
        monthlyPrice: price
      }]
    }
    return this.httpClient.post<void>(`http://localhost:8080/subscriptions/`, payload)
  }
}
