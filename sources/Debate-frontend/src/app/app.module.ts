import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatChipsModule } from '@angular/material/chips';
import { TextFieldModule } from '@angular/cdk/text-field'; 

import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { DebateCardComponent } from './homepage/debate-card/debate-card.component';
import { TagSearchComponent } from './tag-search/tag-search.component';
import { AppRoutingModule } from './app-routing.module';
import { AddUpdateDebateComponent } from './debate/add-update-debate/add-update-debate.component';
import { DebateComponent } from './debate/debate.component';
import { DebateArgumentComponent } from './debate/debate-argument/debate-argument.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    TagSearchComponent,
    DebateCardComponent,
    AddUpdateDebateComponent,
    DebateComponent,
    DebateArgumentComponent
    ],
  imports: [
    BrowserModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatAutocompleteModule,
    MatInputModule,
    MatFormFieldModule,
    MatChipsModule,
    MatTabsModule,
    MatExpansionModule,
    MatCardModule,
    MatGridListModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    TextFieldModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
