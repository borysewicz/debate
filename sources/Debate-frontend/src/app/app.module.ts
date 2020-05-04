import { TextFieldModule } from '@angular/cdk/text-field';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Injectable, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CookieService } from 'ngx-cookie-service';
import { TimeagoCustomFormatter, TimeagoFormatter, TimeagoIntl, TimeagoModule } from 'ngx-timeago';

import { AccountComponent } from './account/account.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoadMoreComponent } from './common/load-more/load-more.component';
import { AddUpdateDebateComponent } from './debate/add-update-debate/add-update-debate.component';
import { CommentComponent } from './debate/comment/comment.component';
import { DebateArgumentComponent } from './debate/debate-argument/debate-argument.component';
import { DebateComponent } from './debate/debate.component';
import { DebateCardComponent } from './homepage/debate-card/debate-card.component';
import { HomepageComponent } from './homepage/homepage.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { RegistrationComponent } from './loginpage/registration/registration.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { TagSearchComponent } from './tag-search/tag-search.component';
import { AddCommentComponent } from './debate/comment/add-comment/add-comment.component';

@Injectable()
export class CustomIntl extends TimeagoIntl {}

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    TagSearchComponent,
    DebateCardComponent,
    AddUpdateDebateComponent,
    LoginpageComponent,
    RegistrationComponent,
    AccountComponent,
    LoadMoreComponent,
    DebateComponent,
    DebateArgumentComponent,
    CommentComponent,
    SearchResultsComponent,
    AddCommentComponent,
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
    MatDividerModule,
    MatProgressBarModule,
    MatMenuModule,
    MatGridListModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    TextFieldModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    TimeagoModule.forRoot({
      intl: { provide: TimeagoIntl, useClass: CustomIntl },
      formatter: {
        provide: TimeagoFormatter,
        useClass: TimeagoCustomFormatter,
      },
    }),
  ],

  providers: [
    CookieService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
