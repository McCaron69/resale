import { Feature } from "./feature";

export interface Category {

  categoryId: bigint;
  name:string;
  
  features: Feature[];
  
  parent: Category;

}
