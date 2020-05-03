import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AccountComponent } from './account/account.component';
import { AddUpdateDebateComponent } from './debate/add-update-debate/add-update-debate.component';
import { DebateComponent } from './debate/debate.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { RegistrationComponent } from './loginpage/registration/registration.component';
import { SearchResultsComponent } from './search-results/search-results.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', component: HomepageComponent },
  { path: 'addDebate', component: AddUpdateDebateComponent },
  { path: 'home', redirectTo: '' },
  { path: 'account', component: AccountComponent },
  { path: 'searchResults', component: SearchResultsComponent},
  { path: 'login', component: LoginpageComponent },
  { path: 'register', component: RegistrationComponent },
  { path: 'debate/:id', component: DebateComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
