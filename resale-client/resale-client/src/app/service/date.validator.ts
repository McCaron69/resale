import {AbstractControl, ValidationErrors} from '@angular/forms';

export function DateValidator(){
  return (control: AbstractControl):ValidationErrors|null => {
    let date = new Date(control.value);
    if (date.getTime() < Date.now() - 100*365*24*3600*1000) {
      return {old:true};
    }
    if(date.getTime() > Date.now() - 18*365*24*3600*1000){
      return {young:true};
    }
    return null;
  }
}
