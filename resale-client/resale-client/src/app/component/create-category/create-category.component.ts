import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Address} from "../../domain/address";
import {Category} from "../../domain/category";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpClient, HttpParams} from "@angular/common/http";
import {faSearch, faTimes} from "@fortawesome/free-solid-svg-icons/";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-category',
  templateUrl: './create-category.component.html',
  styleUrls: ['./create-category.component.css']
})
export class CreateCategoryComponent implements OnInit {

  baseUrl = environment.baseUrl;

  faSearch = faSearch;
  parent: Category|null;
  private closeResult = '';
  words = '';
  searchCategoryResult: Category[] = [];
  totalCategoryPages: number;
  categoryPage = 0;
  faTimes = faTimes;
  categoryName =  new FormControl('',
    [Validators.required,
    Validators.min(2),
    Validators.max(50)]);

  constructor(private http: HttpClient, private modalService:NgbModal, private router:Router) { }

  ngOnInit(): void {
  }


  openCategoryModal(content: any) {
    this.words = ''
    this.modalService.open(content, { size: 'lg' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  searchCategories(page: number) {
    this.categoryPage = page;
    this.http.get("http://localhost:8080/category/search", {
      params: new HttpParams()
        .append('page',page)
        .append('words', this.words)
    }).subscribe((res:any)=>{
      this.searchCategoryResult = res.content;
      this.totalCategoryPages = res.totalPages;
    });
  }

  changeParent(category: Category,modal: any) {
    this.parent = category;
    modal.close('close');
  }

  createCategory() {
    this.http.post(this.baseUrl + 'category', {
      name: this.categoryName.value,
      parent: this.parent
    }).subscribe((res:any)=>{
      this.router.navigate(['/administrate/category']);
    })
  }

}
