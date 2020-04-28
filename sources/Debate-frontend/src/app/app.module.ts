import { TextFieldModule } from '@angular/cdk/text-field';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddUpdateDebateComponent } from './debate/add-update-debate/add-update-debate.component';
import { CommentComponent } from './debate/comment/comment.component';
import { DebateArgumentComponent } from './debate/debate-argument/debate-argument.component';
import { DebateComponent } from './debate/debate.component';
import { DebateCardComponent } from './homepage/debate-card/debate-card.component';
import { HomepageComponent } from './homepage/homepage.component';
import { TagSearchComponent } from './tag-search/tag-search.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    TagSearchComponent,
    DebateCardComponent,
    AddUpdateDebateComponent,
    DebateComponent,
    DebateArgumentComponent,
    CommentComponent,
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
    MatListModule,
    MatExpansionModule,
    MatButtonToggleModule,
    MatCardModule,
    MatProgressBarModule,
    MatMenuModule,
    MatGridListModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    TextFieldModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
