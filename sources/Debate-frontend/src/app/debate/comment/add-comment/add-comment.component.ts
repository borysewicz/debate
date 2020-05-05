import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.scss']
})
export class AddCommentComponent implements OnInit {

  @Output() commentAdded = new EventEmitter<string>();
  @ViewChild('commentForm') commentForm: NgForm;
  comment: string;

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.commentForm.valid) {
      this.commentAdded.emit(this.comment);
      this.commentForm.resetForm();
    }
  }

}
