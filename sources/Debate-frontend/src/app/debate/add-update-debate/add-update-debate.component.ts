import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { AddUpdateDebateDto } from 'src/app/dto/addUpdateDebate.dto';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';

@Component({
  selector: 'app-add-update-debate',
  templateUrl: './add-update-debate.component.html',
  styleUrls: ['./add-update-debate.component.scss']
})
export class AddUpdateDebateComponent implements OnInit {

  model: AddUpdateDebateDto;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  imageUrl: any;
  imageData: File;
  imageInvalid: boolean = false;

  @ViewChild("tagList") tagList;

  constructor() { 
    this.model = {title: "", description: "", mainTags: [], allTags: ["Polityka"]};
  }

  ngOnInit(): void {
  }

  onSubmit(){
    console.log("DUpa");
  }

  get diagnostics(){
    return JSON.stringify(this.model);
  }

  addTag(event: MatChipInputEvent){
      if ((event.value || '').trim()){
        this.model.allTags.push(event.value.trim());
        if (this.model.allTags.length >= 3 && this.model.allTags.length < 7){
          this.tagList.errorState = false;
        }else if (this.model.allTags.length > 7){
          this.tagList.errorState = true;
        }
      }
      if (event.input){
        event.input.value = "";
      }    
  }

  removeTag(tag: string): void {
    const index = this.model.allTags.indexOf(tag);

    if (index >= 0) {
      this.model.allTags.splice(index, 1);
    }

    if (this.model.allTags.length < 3 || this.model.allTags.length > 7){
        this.tagList.errorState = true;
    } else {
      this.tagList.errorState = false;
    }
  }

  onFileChanged(fileEvent: any){
    if (fileEvent.target.files[0].size > 4000000){ // 4MB = 4 000 000 B
      this.imageInvalid = true;
      return;
    }
    this.imageData = fileEvent.target.files[0];
    this.imageInvalid = false;
    const reader = new FileReader();
    reader.readAsDataURL(this.imageData);
    reader.onload = (_event) => {
      this.imageUrl = reader.result;
    }    
  }

}
