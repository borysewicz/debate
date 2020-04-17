import { DebateService } from 'src/app/services/debate.service';
import { Debate } from './../dto/debate.dto';
import { ArgumentService } from './../services/argument.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Argument, ArgumentAttitude } from '../dto/argument.dto';
import { Subscription } from 'rxjs';

@Component({
   selector: 'app-debate',
   templateUrl: './debate.component.html',
   styleUrls: ['./debate.component.scss'],
})
export class DebateComponent implements OnInit, OnDestroy {
   private routeParamSubscription: Subscription;
   debate: Debate;
   pros: Argument[];
   cons: Argument[];

   constructor(
      private debateService: DebateService,
      private argumentService: ArgumentService,
      private route: ActivatedRoute
   ) {}

   ngOnInit(): void {
      this.routeParamSubscription = this.route.params.subscribe((params) => {
         this.updateModel(params.id);
      });
   }

   private updateModel(id: string) {
      this.debateService.getDebateById(id).subscribe(debate => {
        this.debate = debate;
        this.argumentService.getArgumentsForDebate(debate._id).subscribe(debateArgs => {
          this.pros = debateArgs.filter(x => x.attitude === ArgumentAttitude.POSITIVE).sort((a, b) => b.upVotes - a.upVotes);
          this.cons = debateArgs.filter(x => x.attitude === ArgumentAttitude.NEGATIVE).sort((a, b) => b.upVotes - a.upVotes);
        });
      });
   }

   ngOnDestroy(): void {
      this.routeParamSubscription.unsubscribe();
   }
}
