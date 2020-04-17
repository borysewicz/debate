import { Argument, ArgumentAttitude } from './../../dto/argument.dto';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-debate-argument',
  templateUrl: './debate-argument.component.html',
  styleUrls: ['./debate-argument.component.scss']
})
export class DebateArgumentComponent implements OnInit {

  @Input() argument: Argument;
  readonly argumentAttitude = ArgumentAttitude;
  iconName: string;

  constructor() { }

  ngOnInit(): void {
    this.iconName = this.argument.attitude === ArgumentAttitude.POSITIVE ? 'add' : 'remove';
  }

}
