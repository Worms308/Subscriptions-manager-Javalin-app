import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeObserverComponent } from './change-observer.component';

describe('ChangeObserverComponent', () => {
  let component: ChangeObserverComponent;
  let fixture: ComponentFixture<ChangeObserverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeObserverComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangeObserverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
