import { Component, OnInit } from '@angular/core';
import {Feature} from "../../../domain/feature";
import {HttpClient, HttpParams} from "@angular/common/http";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-administrate-feature',
  templateUrl: './administrate-feature.component.html',
  styleUrls: ['./administrate-feature.component.css']
})
export class AdministrateFeatureComponent implements OnInit {

  private baseUrl = environment.baseUrl;
  page = 0;
  features: Feature[] = [];
  totalPages: number;
  current: FormGroup;
  private closeResult = '';
  constructor(private http:HttpClient, private modalService:NgbModal, private fb:FormBuilder) { }
  ngOnInit(): void {
    this.getFeatures(this.page);
  }

  deleteFeature(feature: Feature) {
    const index = this.features.indexOf(feature);
    if (index > -1) {
      this.features.splice(index, 1);
    }
    this.http.delete(this.baseUrl+"feature/" + feature.featureId).subscribe();
  }


  getFeatures(page:number){
    this.page = page;
    return this.http.get(this.baseUrl+"feature",{
      params: new HttpParams().append('page', page)
    }).subscribe((res:any)=>{
      this.features = res.content;
      this.features.sort((a,b)=> 0 - (a > b ? 1 : -1));
      this.totalPages = res.totalPages;
    })
  }


  openFeatureModal(content:any, feature:Feature) {
    this.current = this.fb.group({
      name: new FormControl(feature.name,
        [Validators.required,
          Validators.pattern('^[\w]+'),
        Validators.minLength(2),
        Validators.maxLength(50)]),
      description: new FormControl(feature.description,
        [Validators.required,
          Validators.pattern('^[\w]+'),
        Validators.maxLength(1000)]),
      featureId: new FormControl(feature.featureId)
    });
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

  saveChanges(modal: any) {
    this.http.patch(this.baseUrl+"feature", this.current.value)
      .subscribe((res:any)=>{
        this.features.splice(this.features.findIndex(x=>x.featureId == res.featureId),1);
        this.features.push(res);
        this.features.sort((a,b)=> 0 - (a > b ? 1 : -1));
        modal.close('close');
      })
  }
}
