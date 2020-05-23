import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export interface AddArgumentData {
  type: string;
  title: string;
  content: string;
}

@Component({
  selector: 'app-add-argument-dialog',
  templateUrl: './add-argument-dialog.component.html',
  styleUrls: ['./add-argument-dialog.component.scss'],
})
export class AddArgumentDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<AddArgumentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddArgumentData
  ) {}

  onCancelClicked(): void {
    this.dialogRef.close();
  }
}
