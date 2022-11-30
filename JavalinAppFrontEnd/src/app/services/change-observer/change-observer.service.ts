import {Injectable} from '@angular/core';
import {map, Subject} from "rxjs";
import {WebsocketService} from "../websocket/websocket.service";

export interface SubscriptionPayload {
  serviceName: string,
  monthlyPrice: number
}

@Injectable({
  providedIn: 'root'
})
export class ChangeObserverService {
  messages: Subject<SubscriptionPayload[]> | undefined;

  constructor(private wsService: WebsocketService) {
    this.connect()
  }

  public connect() {
    const userId = '1'
    this.messages = <Subject<SubscriptionPayload[]>>this.wsService.connect(`ws://localhost:8080/changed-subscription/${userId}`).pipe(
      map((response: MessageEvent): SubscriptionPayload[] => {
          return JSON.parse(response.data);
        }
      ));
  }
}
