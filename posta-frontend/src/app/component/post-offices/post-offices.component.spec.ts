import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostOfficesComponent } from './post-offices.component';

describe('PostOfficesComponent', () => {
  let component: PostOfficesComponent;
  let fixture: ComponentFixture<PostOfficesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostOfficesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostOfficesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
