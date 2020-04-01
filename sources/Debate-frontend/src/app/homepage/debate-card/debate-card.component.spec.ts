import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DebateCardComponent } from './debate-card.component';

describe('DebateCardComponent', () => {
  let component: DebateCardComponent;
  let fixture: ComponentFixture<DebateCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebateCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DebateCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
