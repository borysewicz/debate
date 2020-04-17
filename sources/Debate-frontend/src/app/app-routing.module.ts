import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { AddUpdateDebateComponent } from './debate/add-update-debate/add-update-debate.component';
import { DebateComponent } from './debate/debate.component';

const routes: Routes = [
  { path: 'addDebate', component: AddUpdateDebateComponent },
  { path: 'home', component: HomepageComponent },
  { path: 'debate/:id', component: DebateComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
