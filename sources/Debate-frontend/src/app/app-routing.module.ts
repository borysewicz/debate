import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AddUpdateDebateComponent } from './debate/add-update-debate/add-update-debate.component';
import { DebateComponent } from './debate/debate.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { RegistrationComponent } from './loginpage/registration/registration.component';
import { AccountComponent } from './account/account.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', component: HomepageComponent },
  { path: 'addDebate', component: AddUpdateDebateComponent },
  { path: 'home', redirectTo: '' },
  { path: 'login', component: LoginpageComponent },
  { path: 'register', component: RegistrationComponent },
  { path: 'debate/:id', redirectTo: 'debate/:id/arguments' },
  { path: 'debate/:id/arguments', component: DebateComponent, data: { section: 'arguments' } },
  { path: 'debate/:id/comments', component: DebateComponent, data: { section: 'comments' } }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
