import { Component, OnInit } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {ChangeObserverService, SubscriptionPayload} from "../../services/change-observer/change-observer.service";
import {SubscriptionClientService} from "../../services/subscription/subscription-client.service";

@Component({
  selector: 'app-change-observer',
  templateUrl: './change-observer.component.html',
  styleUrls: ['./change-observer.component.css']
})
export class ChangeObserverComponent implements OnInit {
  messages: Subject<SubscriptionPayload[]> | undefined;
  suma: number = 0;

  constructor(private changeObserver: ChangeObserverService,
              private subscriptions: SubscriptionClientService) {
    this.messages = changeObserver.messages
    subscriptions.getUserSubscriptions().subscribe(it => {
      this.suma = this.sumPrices(it.subscriptions.map(it => it.monthlyPrice))
    })
    this.sumSubscriptionsPrices()
  }

  ngOnInit(): void {}

  sumSubscriptionsPrices() {
    this.messages?.subscribe((res: SubscriptionPayload[]) => {
      this.suma = this.sumPrices(res.map( it => it.monthlyPrice))
    })
  }

  private sumPrices(prices: number[]): number {
    return prices.reduce((prev, it) => prev + it, 0)
  }

}
