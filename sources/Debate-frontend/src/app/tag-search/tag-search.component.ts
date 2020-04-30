import { Component, OnInit, Input, ElementRef, ViewChild } from '@angular/core';
import { MatChipInputEvent } from '@angular/material/chips';
import { FormControl } from '@angular/forms';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';

@Component({
  selector: 'app-tag-search',
  templateUrl: './tag-search.component.html',
  styleUrls: ['./tag-search.component.scss']
})
export class TagSearchComponent implements OnInit {
  tagControl = new FormControl('');
  @ViewChild('tagInput') tagControlElement: ElementRef<HTMLInputElement>;
  tags: string[] = [
    'Polityka',
    'Ekonomia',
    'Religia',
    'Nauka',
    'Chemia',
    'Informatyka',
    'Sztuczna inteligencja'
  ];
  separatorKeysCodes: number[] = [ENTER, COMMA];
  @Input() chosenTags: string[] = [];
  filteredTags: Observable<string[]>;
  popularTags: string[] = ['Polityka', 'Ekonomia', 'Religia', 'Nauka'];

  constructor() {}

  ngOnInit(): void {
    this.filteredTags = this.tagControl.valueChanges.pipe(
      // tslint:disable-next-line: deprecation
      startWith(null),
      map((tag: string | null) =>
        tag ? this._filter(tag) : this.tags.slice()
      )
    );
  }

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

  private _filter(newTag: string) {
    const filterValue = newTag.toLowerCase();
    return this.tags.filter(
      tag =>
        tag.toLowerCase().includes(filterValue) &&
        !this.chosenTags.includes(tag)
    );
  }

  private _addTag(tag: string): boolean {
    const value = tag?.toLowerCase();
    const regex = new RegExp('^' + value + '$', 'i');
    if (
      this.tags.find(x => regex.test(x)) &&
      !this.chosenTags.find(y => regex.test(y))
    ) {
      this.chosenTags.push(tag.trim());
      return true;
    }
    return false;
  }
}
