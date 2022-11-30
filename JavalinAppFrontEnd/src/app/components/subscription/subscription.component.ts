import { Component, OnInit } from '@angular/core';
import {SubscriptionClientService, SubscriptionsPayload} from "../../services/subscription/subscription-client.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent implements OnInit {
  subscriptions: Observable<SubscriptionsPayload> = this.subscriptionClientService.getUserSubscriptions()

  constructor(private subscriptionClientService: SubscriptionClientService) { }

  ngOnInit(): void {
  }

  updateSubscriptions(serviceName: string, price: number) {
    if (serviceName.trim().length > 0 && price > 0) {
      this.subscriptionClientService.updateUserSubscriptions(serviceName, price).subscribe(() => {
        this.subscriptions = this.subscriptionClientService.getUserSubscriptions()
      })
    }
  }
}
