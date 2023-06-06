import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Category} from "../../../domain/category";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {faSearch} from "@fortawesome/free-solid-svg-icons/faSearch"
import {Feature} from "../../../domain/feature";
import {environment} from "../../../../environments/environment";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-administrate-category',
  templateUrl: './administrate-category.component.html',
  styleUrls: ['./administrate-category.component.css']
})
export class AdministrateCategoryComponent implements OnInit {

  private baseUrl = environment.baseUrl;
  faSearch =  faSearch;
  closeResult = '';
  words = '';



  //Categories variables
  page = 0;
  categories: Category[]
  totalCategoryPages: number;
  current: Category;


  //Feature search modal variables
  featurePage: number;
  searchFeatureResult: Feature[] = [];
  totalFeaturesPages: number;


  //Category search modal variables
  searchCategoryResult: Category[];
  totalModalCategoryPages: number;
  categoryModalPage: number;
  currentName: FormControl;



  constructor(private http:HttpClient, private modalService:NgbModal) { }
  ngOnInit(): void {
    this.getCategories(this.page);
  }


  //Feature Modal methods
  openFeatureModal(content:any, category:Category) {
    this.words = '';
    this.searchFeatureResult = [];
    this.current = category;
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  /**
   * This method return dismiss modal reason message.
   * @param reason
   * @private
   * @return string
   */
  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }


  searchFeatures(page:number) {
    this.featurePage = page;
    this.http.get(this.baseUrl + "feature/search", {
      params: new HttpParams()
        .append('name', this.words)
        .append('page', page)
    }).subscribe((res:any)=>{
      this.searchFeatureResult = res.content;
      this.totalFeaturesPages = res.totalPages;
    })
  }


  getCategories(page:number){
    this.page = page;
    return this.http.get(this.baseUrl+"category/page", {
      params: new HttpParams().append('page', page)
    }).subscribe((res:any) =>{
      console.log(res);
      this.categories = res.content;
      this.totalCategoryPages = res.totalPages;
    });
  }

  deleteCategory(category: Category) {
    const index = this.categories.indexOf(category);
    if (index > -1) {
      this.categories.splice(index, 1);
    }
    this.http.delete(this.baseUrl+"category/" + category.categoryId).subscribe();
  }




  addFeature(feature: Feature) {
    this.current.features.push(feature);
    this.http.patch("http://localhost:8080/category/feature/add", {
      'categoryId':this.current.categoryId.toString(),
      'featureId': feature.featureId.toString()
    }).subscribe(res=>{
      console.log(res);
    })
  }

  deleteFeature(feature: Feature) {
    const index = this.current.features.indexOf(feature);
    if (index > -1) {
      this.current.features.splice(index, 1);
    }
    this.http.patch("http://localhost:8080/category/feature/delete", {
      'categoryId':this.current.categoryId.toString(),
      'featureId': feature.featureId.toString()
    }).subscribe(res=>{
      console.log(res);
    })
  }

  saveChanges(modal: any) {
    this.current.name = this.currentName.value;
    this.http.patch("http://localhost:8080/category",this.current)
      .subscribe((res:any)=>{
        this.categories.splice(this.categories.indexOf(this.current),1);
        this.categories.push(res);
        modal.close('close');
      })
  }



  //Category modal methods
  changeParent(category: Category) {
    this.current.parent = category;
  }

  searchCategories(page: number) {
      this.http.get("http://localhost:8080/category/search", {
        params: new HttpParams()
          .append('page',page)
          .append('words', this.words)
      }).subscribe((res:any)=>{
        this.searchCategoryResult = res.content;
      });
  }

  openCategoryModal(content: any, category: Category) {
    this.words = '';
    this.searchCategoryResult = [];
    this.current = category;
    this.currentName = new FormControl(category.name,[
      Validators.required,
      Validators.minLength(2),
      Validators.maxLength(50)
    ])
    this.modalService.open(content, { size: 'lg' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

}
