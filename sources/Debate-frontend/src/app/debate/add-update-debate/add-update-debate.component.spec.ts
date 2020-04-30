import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUpdateDebateComponent } from './add-update-debate.component';

describe('AddUpdateDebateComponent', () => {
  let component: AddUpdateDebateComponent;
  let fixture: ComponentFixture<AddUpdateDebateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUpdateDebateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUpdateDebateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
