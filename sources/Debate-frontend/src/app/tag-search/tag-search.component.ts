import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tag-search',
  templateUrl: './tag-search.component.html',
  styleUrls: ['./tag-search.component.scss'],
})
export class TagSearchComponent implements OnInit {
  tagControl = new FormControl('');
  @ViewChild('tagInput') tagControlElement: ElementRef<HTMLInputElement>;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  @Input() chosenTags: string[] = [];
  popularTags: string[] = ['Polityka', 'Ekonomia', 'Religia', 'Nauka'];

  constructor(private router: Router) {}

  ngOnInit(): void {}

  onTagAdded(event: MatChipInputEvent) {
    if (this._addTag(event.value)) {
      this.tagControlElement.nativeElement.value = '';
      this.tagControl.setValue(null);
    }
  }

  addTagAndClear(tag: string) {
    this._addTag(tag);
    this.tagControlElement.nativeElement.value = '';
    this.tagControl.setValue(null);
  }

  onTagRemoved(tag: string) {
    const tagIndex = this.chosenTags.indexOf(tag);
    if (tagIndex !== -1) {
      this.chosenTags.splice(tagIndex, 1);
    }
  }

  private _addTag(tag: string): boolean {
    const value = tag?.toLowerCase();
    const regex = new RegExp('^' + value + '$', 'i');
    if (!this.chosenTags.find((y) => regex.test(y)) && /\S/.test(value)) {
      this.chosenTags.push(tag.trim());
      return true;
    }

    if (!/\S/.test(value)) {
      this.tagControlElement.nativeElement.value = '';
      this.tagControl.setValue(null);
    }

    return false;
  }

  searchDebateByTags() {
    if (this.chosenTags !== []) {
      this.router.navigate(['/searchResults'], {
        queryParams: { searchTag: this.chosenTags },
      });
    }
  }
}
