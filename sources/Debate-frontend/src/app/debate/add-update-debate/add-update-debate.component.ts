import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatChipInputEvent, MatChipList } from '@angular/material/chips';
import { Router } from '@angular/router';
import { AddUpdateDebate } from 'src/app/dto/addUpdateDebate.dto';
import { DebateService } from 'src/app/services/debate.service';

@Component({
  selector: 'app-add-update-debate',
  templateUrl: './add-update-debate.component.html',
  styleUrls: ['./add-update-debate.component.scss']
})
export class AddUpdateDebateComponent implements OnInit {

  model: AddUpdateDebate;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  imageUrl: string | ArrayBuffer;
  imageData: File;
  imageInvalid = false;

  @ViewChild('tagList') tagList: MatChipList;
  @ViewChild('debateForm') debateForm: HTMLFormElement;

  constructor(private debateService: DebateService, private router: Router) {
    this.model = {title: '', description: '', mainTags: [], allTags: ['polityka']};
  }

  ngOnInit(): void {
  }

  onSubmit() {
    if (!this.debateForm.form.valid || !this.isTagListValid()) {
      return;
    }
    this.model.mainTags = this.model.allTags.slice(0, 3);
    this.debateService.addDebate(this.model, this.imageData).subscribe(
      res => this.router.navigate(['/home']),
      err => console.log(err)
    );
  }

  get diagnostics() {
    return JSON.stringify(this.model);
  }

  addTag(event: MatChipInputEvent) {
      if ((event.value || '').trim()) {
        const value = event.value.trim().toLowerCase();
        if (this.model.allTags.indexOf(value) >= 0) {
          return;
        }
        this.model.allTags.push(value);
        if (this.model.allTags.length >= 3 && this.model.allTags.length < 7) {
          this.tagList.errorState = false;
        } else if (this.model.allTags.length > 7) {
          this.tagList.errorState = true;
        }
      }
      if (event.input) {
        event.input.value = '';
      }
  }

  removeTag(tag: string): void {
    const index = this.model.allTags.indexOf(tag);
    if (index >= 0) {
      this.model.allTags.splice(index, 1);
    }

    if (this.model.allTags.length < 3 || this.model.allTags.length > 7) {
        this.tagList.errorState = true;
    } else {
      this.tagList.errorState = false;
    }
  }

  onFileChanged(fileEvent: Event) {
    const fileEventTarget = fileEvent.target as HTMLInputElement;
    if (fileEventTarget.files[0].size > 4000000) { // 4MB = 4 000 000 B
      this.imageInvalid = true;
      return;
    }
    this.imageData = fileEventTarget.files[0];
    this.imageInvalid = false;
    const reader = new FileReader();
    reader.readAsDataURL(this.imageData);
    reader.onload = () => {
      this.imageUrl = reader.result;
    };
  }

  isTagListValid(): boolean {
    if (this.model.allTags.length >= 3 && this.model.allTags.length <= 7) { return true; } else {
      this.tagList.errorState = true;
      return false;
    }
  }
}
