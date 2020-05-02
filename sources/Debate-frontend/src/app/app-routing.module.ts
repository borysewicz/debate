import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { AddUpdateDebateComponent } from './debate/add-update-debate/add-update-debate.component';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { RegistrationComponent } from './loginpage/registration/registration.component';
import { AccountComponent } from './account/account.component';
import { SearchResultsComponent } from './search-results/search-results.component';

const routes: Routes = [
  { path: 'addDebate', component: AddUpdateDebateComponent },
  { path: 'searchResults', component: SearchResultsComponent},
  { path: 'home', component: HomepageComponent },
  { path: 'login', component: LoginpageComponent },
  { path: 'register', component: RegistrationComponent },
  { path: 'account', component: AccountComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
